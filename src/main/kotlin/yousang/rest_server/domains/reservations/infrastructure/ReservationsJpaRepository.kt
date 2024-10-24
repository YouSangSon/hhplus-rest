package yousang.rest_server.domains.reservations.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import yousang.rest_server.domains.reservations.domain.models.ReservableResourceEntity
import yousang.rest_server.domains.reservations.domain.models.ReservationEntity
import yousang.rest_server.domains.reservations.domain.models.ReservationHistoryEntity
import yousang.rest_server.domains.reservations.domain.models.SeatEntity

interface ReservationJpaRepository : JpaRepository<ReservationEntity, Long> {

}

interface SeatJpaRepository : JpaRepository<SeatEntity, Long> {

}

interface ReservationHistoryJpaRepository : JpaRepository<ReservationHistoryEntity, Long> {

}

interface ReservableResourceJpaRepository : JpaRepository<ReservableResourceEntity, Long> {

}