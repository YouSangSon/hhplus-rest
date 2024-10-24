package yousang.rest_server.domains.reservations.infrastructure

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.LockModeType
import org.springframework.stereotype.Repository
import yousang.rest_server.domains.reservations.domain.models.QReservableResourceEntity
import yousang.rest_server.domains.reservations.domain.models.QReservationEntity
import yousang.rest_server.domains.reservations.domain.models.QReservationHistoryEntity
import yousang.rest_server.domains.reservations.domain.models.QSeatEntity
import yousang.rest_server.domains.reservations.domain.models.ReservableResourceEntity
import yousang.rest_server.domains.reservations.domain.models.ReservationEntity
import yousang.rest_server.domains.reservations.domain.models.ReservationHistoryEntity
import yousang.rest_server.domains.reservations.domain.models.SeatEntity

@Repository
class ReservationCustomRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun findById(reservationId: Long): ReservationEntity? {
        val reservation = QReservationEntity.reservationEntity

        return jpaQueryFactory.selectFrom(reservation)
            .where(reservation.id.eq(reservationId).and(reservation.deletion.deletedAt.isNull)).fetchOne()
    }

    fun findByUserId(userId: Long): List<ReservationEntity> {
        val reservation = QReservationEntity.reservationEntity

        return jpaQueryFactory.selectFrom(reservation)
            .where(reservation.userId.eq(userId).and(reservation.deletion.deletedAt.isNull)).fetch()
    }
}

@Repository
class SeatCustomRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun findById(seatId: Long): SeatEntity? {
        val seat = QSeatEntity.seatEntity

        return jpaQueryFactory.selectFrom(seat).where(seat.id.eq(seatId).and(seat.deletion.deletedAt.isNull)).fetchOne()
    }

    fun findAllByResourceId(reservableResourceId: Long): List<SeatEntity> {
        val seat = QSeatEntity.seatEntity

        return jpaQueryFactory.selectFrom(seat)
            .where(seat.reservableResourceId.eq(reservableResourceId).and(seat.deletion.deletedAt.isNull)).fetch()
    }

    fun findByIdWithLock(seatId: Long): SeatEntity? {
        val seat = QSeatEntity.seatEntity

        return jpaQueryFactory.selectFrom(seat).where(seat.id.eq(seatId).and(seat.deletion.deletedAt.isNull))
            .setLockMode(LockModeType.PESSIMISTIC_WRITE).fetchOne()
    }
}

@Repository
class ReservationHistoryCustomRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun findByReservationId(reservationId: Long): ReservationHistoryEntity? {
        val reservationHistory = QReservationHistoryEntity.reservationHistoryEntity

        return jpaQueryFactory.selectFrom(reservationHistory)
            .where(reservationHistory.reservationId.eq(reservationId).and(reservationHistory.deletion.deletedAt.isNull))
            .fetchOne()
    }

    fun findByTimestampAfter(timestamp: Long): List<ReservationHistoryEntity> {
        val reservationHistory = QReservationHistoryEntity.reservationHistoryEntity

        return jpaQueryFactory.selectFrom(reservationHistory)
            .where(reservationHistory.timestamp.gt(timestamp).and(reservationHistory.deletion.deletedAt.isNull)).fetch()
    }
}

@Repository
class ReservableResourceCustomRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun findById(reservableResourceId: Long): ReservableResourceEntity? {
        val reservableResource = QReservableResourceEntity.reservableResourceEntity

        return jpaQueryFactory.selectFrom(reservableResource)
            .where(reservableResource.id.eq(reservableResourceId).and(reservableResource.deletion.deletedAt.isNull))
            .fetchOne()
    }

    fun findByName(name: String): List<ReservableResourceEntity> {
        val reservableResource = QReservableResourceEntity.reservableResourceEntity

        return jpaQueryFactory.selectFrom(reservableResource)
            .where(reservableResource.name.contains(name).and(reservableResource.deletion.deletedAt.isNull)).fetch()
    }

    fun findByDateRange(startDate: Long, endDate: Long): List<ReservableResourceEntity> {
        val reservableResource = QReservableResourceEntity.reservableResourceEntity

        return jpaQueryFactory.selectFrom(reservableResource).where(
            reservableResource.dateTime.lt(endDate).and(reservableResource.dateTime.gt(startDate))
                .and(reservableResource.deletion.deletedAt.isNull)
        ).fetch()
    }
}