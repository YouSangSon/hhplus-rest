package yousang.rest_server.domains.reservations.domain

import com.querydsl.core.QueryException
import org.springframework.stereotype.Service
import yousang.rest_server.domains.reservations.domain.models.ReservationEntity

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
) {
    fun saveReservation(reservation: ReservationEntity): ReservationEntity {
        try {
            return reservationRepository.save(reservation)
        } catch (e: Exception) {
            throw Exception("unable to save the appointment")
        }
    }

    fun getReservationById(reservationId: Long): ReservationEntity {
        try {
            return reservationRepository.findById(reservationId)
                ?: throw IllegalArgumentException("no appointment found")
        } catch (e: Exception) {
            throw Exception("no appointment found")
        } catch (e: QueryException) {
            throw QueryException("no appointment found")
        }
    }

    fun getReservationsByUserId(userId: Long): List<ReservationEntity> {
        try {
            return reservationRepository.findByUserId(userId)
        } catch (e: Exception) {
            throw Exception("no appointment found")
        } catch (e: QueryException) {
            throw QueryException("no appointment found")
        }
    }
}