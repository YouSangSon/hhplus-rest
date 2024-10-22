package yousang.rest_server.domains.concert.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import yousang.rest_server.domains.concert.domain.models.ConcertEntity

interface ConcertJpaRepository : JpaRepository<ConcertEntity, Long> {

}