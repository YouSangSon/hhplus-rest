package yousang.rest_server.domains.queue.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yousang.rest_server.domains.queue.domain.QueueService
import yousang.rest_server.domains.queue.interfaces.dto.QueueDomainsResponse

@Service
class QueueFacade(
    private val queueService: QueueService,
) {
    @Transactional
    fun joinQueue(userId: Long): QueueDomainsResponse {
        val queueEntity = queueService.joinQueue(userId)
        return QueueDomainsResponse(
            userId = queueEntity.userId,
            position = queueEntity.position,
            status = queueEntity.status.name
        )
    }

    fun getQueuePosition(userId: Long): QueueDomainsResponse {
        val position = queueService.getQueuePosition(userId)
        return QueueDomainsResponse(
            userId = userId,
            position = position
        )
    }

    fun isActiveUser(userId: Long): Boolean {
        return queueService.isActiveUser(userId)
    }

    @Transactional
    fun expireUser(userId: Long) {
        queueService.expireUser(userId)
    }

    @Transactional
    fun removeUserFromQueue(userId: Long) {
        queueService.removeUserFromQueue(userId)
    }
}