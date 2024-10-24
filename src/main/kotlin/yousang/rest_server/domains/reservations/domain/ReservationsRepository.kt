package yousang.rest_server.domains.reservations.domain

import yousang.rest_server.domains.reservations.domain.models.ReservableResourceEntity
import yousang.rest_server.domains.reservations.domain.models.ReservationEntity
import yousang.rest_server.domains.reservations.domain.models.ReservationHistoryEntity
import yousang.rest_server.domains.reservations.domain.models.SeatEntity

interface ReservationRepository {
    fun save(reservationEntity: ReservationEntity): ReservationEntity
    fun findByUserId(userId: Long): List<ReservationEntity>
    fun findById(reservationId: Long): ReservationEntity?
}

interface SeatRepository {
    fun findById(seatId: Long): SeatEntity?
    fun findAllByResourceId(reservableResourceId: Long): List<SeatEntity>
    fun save(seatEntity: SeatEntity): SeatEntity
    fun findByIdWithLock(seatId: Long): SeatEntity?
}

interface ReservationHistoryRepository {
    fun save(reservationHistoryEntity: ReservationHistoryEntity): ReservationHistoryEntity
    fun findByReservationId(reservationId: Long): ReservationHistoryEntity?
    fun findByTimestampAfter(timestamp: Long): List<ReservationHistoryEntity>
}

interface ReservableResourceRepository {
    fun findById(reservableResourceId: Long): ReservableResourceEntity?
    fun save(reservableResourceEntity: ReservableResourceEntity): ReservableResourceEntity
    fun findByName(name: String): List<ReservableResourceEntity>
    fun findByDateRange(startDate: Long, endDate: Long): List<ReservableResourceEntity>
}