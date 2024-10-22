package yousang.rest_server.domains.queue.domain

import yousang.rest_server.domains.queue.domain.models.QueueEntity
import yousang.rest_server.domains.user.domain.models.UserEntity

interface QueueRepository {
    fun findByUserId(userId: Long): QueueEntity
    fun findByUser(user: UserEntity): QueueEntity
    fun save(queue: QueueEntity): QueueEntity
}