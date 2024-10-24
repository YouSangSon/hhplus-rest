package yousang.rest_server.domains.reservations.application

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yousang.rest_server.domains.queue.application.QueueFacade
import yousang.rest_server.domains.reservations.domain.ReservationService
import yousang.rest_server.domains.reservations.domain.models.ReservationEntity
import yousang.rest_server.domains.reservations.domain.models.ReservationStatus
import yousang.rest_server.domains.reservations.interfaces.dto.ReservationDomainsRequest
import yousang.rest_server.domains.reservations.interfaces.dto.ReservationDomainsResponse
import yousang.rest_server.domains.users.application.AccountsFacade

@Component
class ReservationFacade(
    private val reservationService: ReservationService,
    private val seatFacade: SeatFacade,
    private val reservationHistoryFacade: ReservationHistoryFacade,
    private val accountsFacade: AccountsFacade,
    private val queueFacade: QueueFacade
) {

    @Transactional
    fun reserveSeat(request: ReservationDomainsRequest): ReservationDomainsResponse {
        request.validateUserId()
        request.validateSeatId()

        if (!queueFacade.isActiveUser(request.userId!!)) {
            throw IllegalStateException("user is not active in the queue")
        }

        // 좌석 예약 처리
        val seat = seatFacade.getSeatById(request.seatId!!)
        if (!seat.isAvailable()) {
            throw IllegalStateException("seat is not available")
        }
        seat.reserve()
        seatFacade.saveSeat(seat)

        // 예약 생성
        val reservation = ReservationEntity(
            userId = request.userId!!, seatId = request.seatId
        )
        reservationService.saveReservation(reservation)

        // 예약 이력 저장
        reservationHistoryFacade.addHistory(reservation.id!!, ReservationStatus.RESERVED)

        return ReservationDomainsResponse(
            reservationId = reservation.id,
            userId = reservation.userId,
            seatId = reservation.seatId,
            status = reservation.status.name
        )
    }

    @Transactional
    fun confirmReservation(request: ReservationDomainsRequest) {
        request.validateReservationId()
        request.validateUserId()
        request.validateAmount()

        val reservation = reservationService.getReservationById(request.reservationId!!)
        if (reservation.userId != request.userId) {
            throw IllegalArgumentException("user information does not match")
        }

        if (reservation.status != ReservationStatus.RESERVED) {
            throw IllegalStateException("the reservation status is incorrect")
        }

        // payment processing
        accountsFacade.processPayment(request.userId!!, request.reservationId, request.amount!!)

        // update reservation status
        reservation.status = ReservationStatus.CONFIRMED
        reservationService.saveReservation(reservation)

        // save reservation history
        reservationHistoryFacade.addHistory(reservation.id!!, ReservationStatus.CONFIRMED)

        // user expirations in the queue
        queueFacade.expireUser(request.userId!!)
    }

    @Transactional
    fun cancelReservation(request: ReservationDomainsRequest) {
        request.validateReservationId()
        request.validateUserId()

        val reservation = reservationService.getReservationById(request.reservationId!!)
        if (reservation.userId != request.userId) {
            throw IllegalArgumentException("user information does not match")
        }

        if (reservation.status != ReservationStatus.RESERVED && reservation.status != ReservationStatus.CONFIRMED) {
            throw IllegalStateException("the reservation status is incorrect")
        }

        // update reservation status
        reservation.status = ReservationStatus.CANCELLED
        reservationService.saveReservation(reservation)

        // unseat
        val seat = seatFacade.getSeatById(reservation.seatId)
        seat.release()
        seatFacade.saveSeat(seat)

        // save reservation history
        reservationHistoryFacade.addHistory(reservation.id!!, ReservationStatus.CANCELLED)
    }

    fun getAvailableSeats(resourceId: Long): List<ReservationDomainsResponse.SeatInfo> {
        val seats = seatFacade.getAvailableSeatsByResourceId(resourceId)
        return seats.map {
            ReservationDomainsResponse.SeatInfo(
                seatId = it.id, seatNumber = it.seatNumber, isReserved = it.isReserved
            )
        }
    }

    @Transactional
    fun cancelAllReservationsByUser(userId: Long) {
        val reservations = reservationService.getReservationsByUserId(userId)
        reservations.forEach { reservation ->
            if (reservation.status == ReservationStatus.RESERVED || reservation.status == ReservationStatus.CONFIRMED) {
                reservation.status = ReservationStatus.CANCELLED
                reservationService.saveReservation(reservation)
                
                val seat = seatFacade.getSeatById(reservation.seatId)
                seat.release()
                seatFacade.saveSeat(seat)

                reservationHistoryFacade.addHistory(reservation.id!!, ReservationStatus.CANCELLED)
            }
        }
    }
}