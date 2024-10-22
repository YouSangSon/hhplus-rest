package yousang.rest_server.domains.concert.application

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yousang.rest_server.domains.concert.domain.ConcertService
import yousang.rest_server.domains.concert.domain.models.Concert
import yousang.rest_server.domains.reservation.interfaces.dto.SeatDto

@Component
class ConcertFacade(
    private val concertService: ConcertService,
) {
    @Transactional(readOnly = true)
    fun getAvailableDatesByConcert(concertId: Long): List<Long> {
        return concertService.getAvailableDatesByConcert(concertId)
    }

    @Transactional(readOnly = true)
    fun getAvailableSeatsByConcertAndDate(concertId: Long, date: Long): List<SeatDto> {
        return concertService.getAvailableSeatsByConcertAndDate(concertId, date)
    }
}