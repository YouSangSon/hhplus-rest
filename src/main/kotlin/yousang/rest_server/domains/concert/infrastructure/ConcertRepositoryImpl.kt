package yousang.rest_server.domains.concert.infrastructure

import org.springframework.stereotype.Repository
import yousang.rest_server.domains.concert.domain.ConcertRepository
import yousang.rest_server.domains.concert.domain.models.ConcertEntity
import yousang.rest_server.domains.reservation.domain.models.SeatEntity
import yousang.rest_server.domains.reservation.interfaces.dto.SeatDto

@Repository
class ConcertRepositoryImpl(
    private val concertJpaRepository: ConcertJpaRepository,
    private val concertCustomRepository: ConcertCustomRepository,
) : ConcertRepository {
    override fun findAvailableDatesByConcert(concertId: Long): List<Long> {
        return concertCustomRepository.findAvailableDatesByConcert(concertId)
    }

    override fun findAvailableSeatsByConcertAndDate(concertId: Long, date: Long): List<SeatEntity> {
        return concertCustomRepository.findAvailableSeatsByConcertAndDate(concertId, date)
    }
}