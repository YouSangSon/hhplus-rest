package yousang.rest_server.domains.concert.infrastructure

import com.querydsl.core.QueryException
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import yousang.rest_server.domains.concert.domain.models.QConcertEntity
import yousang.rest_server.domains.reservation.domain.models.QReservableResourceEntity
import yousang.rest_server.domains.reservation.domain.models.QSeatEntity
import yousang.rest_server.domains.reservation.domain.models.SeatEntity
import yousang.rest_server.domains.reservation.domain.models.SeatStatus

@Repository
class ConcertCustomRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun findAvailableDatesByConcert(concertId: Long): List<Long> {
        val concert = QConcertEntity.concertEntity
        val seat = QSeatEntity.seatEntity

        return try {
            jpaQueryFactory.select(concert.dateTime).from(concert).leftJoin(concert.seats, seat).where(
                concert.id.eq(concertId)
                    .and(seat.status.eq(SeatStatus.AVAILABLE).and(concert.deletion.deletedAt.isNull))
            ).fetch()
        } catch (e: QueryException) {
            throw QueryException("concert query failed")
        } catch (e: Exception) {
            throw Exception("concert not found")
        }
    }

    fun findAvailableSeatsByConcertAndDate(concertId: Long, date: Long): List<SeatEntity> {
        val seat = QSeatEntity.seatEntity
        val resource = QReservableResourceEntity.reservableResourceEntity

        return try {
            jpaQueryFactory.selectFrom(seat).leftJoin(seat.reservableResource, resource)  // seat와 resource 연결
                .where(
                    resource.id.eq(concertId).and(resource.dateTime.eq(date)).and(seat.status.eq(SeatStatus.AVAILABLE))
                        .and(resource.deletion.deletedAt.isNull)
                ).fetch()
        } catch (e: QueryException) {
            throw QueryException("concert query failed")
        } catch (e: Exception) {
            throw Exception("concert not found")
        }
    }
}