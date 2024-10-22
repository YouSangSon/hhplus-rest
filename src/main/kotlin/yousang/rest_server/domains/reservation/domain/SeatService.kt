package yousang.rest_server.domains.reservation.domain

import org.springframework.stereotype.Service
import yousang.rest_server.domains.reservation.domain.models.Seat

@Service
class SeatService(
    private val seatRepository: SeatRepository
) {
    fun findById(seatId: Long): Seat {
        return seatRepository.findById(seatId).toModel()
    }

    fun save(seat: Seat): Seat? {
        val reservableResourceEntity =
            seat.reservableResource?.toEntity() ?: throw IllegalArgumentException("reservable resource cannot be null")
        return seatRepository.save(seat.toEntity(reservableResourceEntity))?.toModel()
    }
}