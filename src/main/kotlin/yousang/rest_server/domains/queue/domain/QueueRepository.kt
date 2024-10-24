package yousang.rest_server.domains.queue.domain

import yousang.rest_server.domains.queue.domain.models.QueueEntity
import yousang.rest_server.domains.queue.domain.models.QueueStatus

interface QueueRepository {
    fun save(queueEntity: QueueEntity): QueueEntity
    fun findByUserIdAndStatus(userId: Long, status: QueueStatus): QueueEntity?
    fun findMaxPosition(): Int
    fun findFirstByStatusOrderByPositionAsc(status: QueueStatus): QueueEntity?
    fun findByUserId(userId: Long): QueueEntity?
    fun delete(queueEntity: QueueEntity)
}