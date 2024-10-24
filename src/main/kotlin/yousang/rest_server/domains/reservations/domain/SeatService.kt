package yousang.rest_server.domains.reservations.domain

import com.querydsl.core.QueryException
import org.springframework.stereotype.Service
import yousang.rest_server.domains.reservations.domain.models.SeatEntity

@Service
class SeatService(
    private val seatRepository: SeatRepository
) {
    fun getSeatById(seatId: Long): SeatEntity {
        try {
            return seatRepository.findById(seatId) ?: throw Exception("no seats with that seatId")
        } catch (e: Exception) {
            throw Exception("no seats with that seatId")
        } catch (e: QueryException) {
            throw QueryException("no seats with that seatId")
        }
    }

    fun getAvailableSeatsByResourceId(resourceId: Long): List<SeatEntity> {
        try {
            return seatRepository.findAllByResourceId(resourceId)
        } catch (e: Exception) {
            throw Exception("no seats with that resourceId")
        } catch (e: QueryException) {
            throw QueryException("no seats with that resourceId")
        }
    }

    fun saveSeat(seat: SeatEntity) {
        try {
            seatRepository.save(seat)
        } catch (e: Exception) {
            throw Exception("failed to save seat")
        }
    }
}