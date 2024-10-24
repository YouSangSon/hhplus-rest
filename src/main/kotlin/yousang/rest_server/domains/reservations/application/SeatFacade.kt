package yousang.rest_server.domains.reservations.application

import org.springframework.stereotype.Component
import yousang.rest_server.domains.reservations.domain.SeatService
import yousang.rest_server.domains.reservations.domain.models.SeatEntity

@Component
class SeatFacade(
    private val seatService: SeatService
) {
    fun getSeatById(seatId: Long): SeatEntity {
        return seatService.getSeatById(seatId)
    }

    fun saveSeat(seat: SeatEntity) {
        seatService.saveSeat(seat)
    }

    fun getAvailableSeatsByResourceId(resourceId: Long): List<SeatEntity> {
        return seatService.getAvailableSeatsByResourceId(resourceId)
    }
}