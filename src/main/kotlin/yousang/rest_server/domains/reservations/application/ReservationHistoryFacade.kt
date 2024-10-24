package yousang.rest_server.domains.reservations.application

import org.springframework.stereotype.Component
import yousang.rest_server.domains.reservations.domain.ReservationHistoryService
import yousang.rest_server.domains.reservations.domain.models.ReservationStatus

@Component
class ReservationHistoryFacade(
    private val reservationHistoryService: ReservationHistoryService
) {
    fun addHistory(reservationId: Long, status: ReservationStatus) {
        reservationHistoryService.addHistory(reservationId, status)
    }
}