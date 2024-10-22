package yousang.rest_server.domains.reservation.infrastructure

import com.querydsl.core.QueryException
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import yousang.rest_server.domains.reservation.domain.models.QSeatEntity
import yousang.rest_server.domains.reservation.domain.models.SeatEntity

@Repository
class SeatCustomRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun findById(id: Long): SeatEntity {
        val seat = QSeatEntity.seatEntity

        return try {
            jpaQueryFactory.selectFrom(seat).where(seat.id.eq(id).and(seat.deletion.deletedAt.isNull)).fetchOne()
                ?: throw NoSuchElementException("seat not found")
        } catch (e: QueryException) {
            throw QueryException("seat query failed")
        } catch (e: Exception) {
            throw Exception("seat not found")
        }
    }
}