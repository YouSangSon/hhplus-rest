package yousang.rest_server.domains.reservation.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yousang.rest_server.common.vo.Constants
import yousang.rest_server.domains.reservation.domain.ReservationService
import yousang.rest_server.domains.reservation.domain.SeatService
import yousang.rest_server.domains.reservation.domain.models.Reservation
import yousang.rest_server.domains.reservation.domain.models.ReservationStatus
import yousang.rest_server.domains.reservation.domain.models.Seat
import yousang.rest_server.domains.reservation.domain.models.SeatStatus
import yousang.rest_server.domains.user.domain.UserService

@Service
class ReservationFacade(
    private val reservationService: ReservationService,
    private val seatService: SeatService,
    private val userService: UserService,
) {
    @Transactional
    fun reserveSeat(userId: Long, seatId: Long): Reservation {
        val seat = seatService.findById(seatId)

        // handling errors if the seat is already reserved
        if (seat.status == SeatStatus.RESERVED && seat.reservedUntil?.let { it > System.currentTimeMillis() } == true) {
            throw IllegalStateException("this seat is already reserved.")
        }

        // temporary seat assignments
        seat.reserveForLimitedTime(Constants.SEAT_RESERVE_TIME)
        seatService.save(seat)

        // save reservation information
        return reservationService.createTemporaryReservation(userService.findById(userId), seat)
    }

    @Transactional
    fun cancelSeatReservation(seatId: Long): Seat {
        val seat = seatService.findById(seatId)
        seat.cancelReservation()

        return seatService.save(seat) ?: throw IllegalArgumentException("no seats were found.")
    }

    @Transactional
    fun confirmReservation(reservationId: Long): Reservation {
        val reservation = reservationService.findById(reservationId)

        // confirm seats to users after payment is received
        reservation.status = ReservationStatus.CONFIRMED

        return reservationService.save(reservation)
    }

    @Transactional
    fun cancelReservation(reservationId: Long): Reservation {
        val reservation = reservationService.findById(reservationId)

        reservation.status = ReservationStatus.CANCELLED
        reservationService.save(reservation)

        cancelSeatReservation(reservation.seat.id!!)

        return reservation
    }
}