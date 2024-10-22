package yousang.rest_server.domains.reservation.infrastructure

import org.springframework.stereotype.Repository
import yousang.rest_server.domains.reservation.domain.ReservationRepository
import yousang.rest_server.domains.reservation.domain.models.Reservation
import yousang.rest_server.domains.reservation.domain.models.ReservationEntity
import yousang.rest_server.domains.reservation.domain.models.ReservationStatus
import yousang.rest_server.domains.user.infrastructure.UserCustomRepository

@Repository
class ReservationRepositoryImpl(
    private val reservationJpaRepository: ReservationJpaRepository,
    private val reservationCustomRepository: ReservationCustomRepository
) : ReservationRepository {
    override fun save(reservation: ReservationEntity): ReservationEntity {
        return reservationJpaRepository.save(reservation)
    }

    override fun findById(id: Long): ReservationEntity {
        return reservationCustomRepository.findById(id)
    }
}