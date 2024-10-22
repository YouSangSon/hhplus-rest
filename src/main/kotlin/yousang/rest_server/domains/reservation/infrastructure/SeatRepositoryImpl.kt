package yousang.rest_server.domains.reservation.infrastructure

import org.springframework.stereotype.Repository
import yousang.rest_server.domains.reservation.domain.SeatRepository
import yousang.rest_server.domains.reservation.domain.models.SeatEntity

@Repository
class SeatRepositoryImpl(
    private val seatJpaRepository: SeatJpaRepository, private val seatCustomRepository: SeatCustomRepository
) : SeatRepository {
    override fun findById(id: Long): SeatEntity {
        return seatCustomRepository.findById(id)
    }

    override fun save(seat: SeatEntity): SeatEntity {
        return seatJpaRepository.save(seat)
    }
}