package yousang.rest_server.domains.reservation.domain

import yousang.rest_server.domains.reservation.domain.models.SeatEntity

interface SeatRepository {
    fun findById(id: Long): SeatEntity
    fun save(seat: SeatEntity): SeatEntity?
}