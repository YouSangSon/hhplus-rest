package yousang.rest_server.domains.reservation.domain

import yousang.rest_server.domains.reservation.domain.models.ReservationEntity

interface ReservationRepository {
    fun save(reservation: ReservationEntity): ReservationEntity
    fun findById(id: Long): ReservationEntity
//    fun findActiveReservationBySeatId(seatId: Long): ReservationEntity?
//    fun findActiveReservations(): List<ReservationEntity>
//    fun findByUserIdAndStatus(userId: Long, status: ReservationStatus): List<ReservationEntity>
}