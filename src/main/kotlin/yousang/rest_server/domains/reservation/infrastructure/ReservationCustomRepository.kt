package yousang.rest_server.domains.reservation.infrastructure

import com.querydsl.core.QueryException
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import yousang.rest_server.domains.reservation.domain.models.QReservationEntity
import yousang.rest_server.domains.reservation.domain.models.ReservationEntity

@Repository
class ReservationCustomRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun findById(id: Long): ReservationEntity {
        val reservation = QReservationEntity.reservationEntity

        return try {
            jpaQueryFactory.selectFrom(reservation)
                .where(reservation.id.eq(id).and(reservation.deletion.deletedAt.isNull)).fetchOne()
                ?: throw NoSuchElementException("reservation not found")
        } catch (e: QueryException) {
            throw QueryException("reservation query failed")
        } catch (e: Exception) {
            throw Exception("reservation not found")
        }
    }
}