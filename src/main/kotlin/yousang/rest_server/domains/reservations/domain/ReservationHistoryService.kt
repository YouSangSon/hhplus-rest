package yousang.rest_server.domains.reservations.domain

import org.springframework.stereotype.Service
import yousang.rest_server.domains.reservations.domain.models.ReservationHistoryEntity
import yousang.rest_server.domains.reservations.domain.models.ReservationStatus

@Service
class ReservationHistoryService(
    private val reservationHistoryRepository: ReservationHistoryRepository
) {
    fun addHistory(reservationId: Long, status: ReservationStatus) {
        try {
            val history = ReservationHistoryEntity(
                reservationId = reservationId, status = status,
            )
            reservationHistoryRepository.save(history)
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }
}