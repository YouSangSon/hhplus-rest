package yousang.rest_server.domains.concert.domain

import yousang.rest_server.domains.reservation.domain.models.SeatEntity

interface ConcertRepository {
    fun findAvailableDatesByConcert(concertId: Long): List<Long>
    fun findAvailableSeatsByConcertAndDate(concertId: Long, date: Long): List<SeatEntity>
}