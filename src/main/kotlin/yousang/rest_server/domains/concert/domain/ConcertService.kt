package yousang.rest_server.domains.concert.domain

import org.springframework.stereotype.Service
import yousang.rest_server.domains.reservation.interfaces.dto.SeatDto

@Service
class ConcertService(
    private val concertRepository: ConcertRepository,
) {
    fun getAvailableDatesByConcert(concertId: Long): List<Long> {
        return concertRepository.findAvailableDatesByConcert(concertId)
    }

    fun getAvailableSeatsByConcertAndDate(concertId: Long, date: Long): List<SeatDto> {
        val seats = concertRepository.findAvailableSeatsByConcertAndDate(concertId, date)
        return seats.map { SeatDto(it.seatNumber, it.status) }
    }
}