package yousang.rest_server.domains.reservation.domain

import org.springframework.stereotype.Service
import yousang.rest_server.domains.reservation.domain.models.Reservation
import yousang.rest_server.domains.reservation.domain.models.ReservationEntity
import yousang.rest_server.domains.reservation.domain.models.ReservationStatus
import yousang.rest_server.domains.reservation.domain.models.Seat
import yousang.rest_server.domains.user.domain.models.User

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
) {
    fun save(reservation: Reservation): Reservation {
        val reservableResourceEntity = reservation.seat.reservableResource?.toEntity()
            ?: throw IllegalArgumentException("reservable resource cannot be null")

        return reservationRepository.save(
            ReservationEntity(
                reservationAt = reservation.reservationAt,
                status = reservation.status,
                expiredAt = reservation.expiredAt,
                user = reservation.user.toEntity(),
                seat = reservation.seat.toEntity(reservableResourceEntity)
            )
        ).toModel()
    }

    fun createTemporaryReservation(user: User, seat: Seat): Reservation {
        val reservableResourceEntity =
            seat.reservableResource?.toEntity() ?: throw IllegalArgumentException("reservable resource cannot be null")

        return reservationRepository.save(
            ReservationEntity(
                reservationAt = System.currentTimeMillis(),
                status = ReservationStatus.PENDING,
                expiredAt = System.currentTimeMillis() + 5 * 60 * 1000,
                user = user.toEntity(),
                seat = seat.toEntity(reservableResourceEntity)
            )
        ).toModel()
    }

    fun findById(id: Long): Reservation {
        return reservationRepository.findById(id).toModel()
    }
}