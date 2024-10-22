package yousang.rest_server.domains.reservation.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import yousang.rest_server.domains.reservation.domain.models.ReservationEntity

interface ReservationJpaRepository : JpaRepository<ReservationEntity, Long> {
    fun save(reservation: ReservationEntity): ReservationEntity
}