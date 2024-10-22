package yousang.rest_server.domains.queue.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import yousang.rest_server.domains.queue.domain.models.QueueEntity

interface QueueJpaRepository : JpaRepository<QueueEntity, Long> {
    fun save(queue: QueueEntity): QueueEntity
}