package yousang.rest_server.domains.reservation.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import yousang.rest_server.domains.reservation.domain.models.SeatEntity

interface SeatJpaRepository : JpaRepository<SeatEntity, Long> {
    fun save(seat: SeatEntity): SeatEntity
}