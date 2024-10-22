package yousang.rest_server.domains.queue.domain

import org.springframework.stereotype.Service
import yousang.rest_server.domains.queue.domain.models.Queue
import yousang.rest_server.domains.queue.domain.models.QueueStatus
import yousang.rest_server.domains.user.domain.models.User

@Service
class QueueService(
    private val queueRepository: QueueRepository
) {
    fun findByUserId(userId: Long): Queue {
        return queueRepository.findByUserId(userId).toModel()
    }

    fun expireQueueForUser(user: User): Queue {
        val queue = queueRepository.findByUser(user.toEntity())
        queue.toModel().let {
            it.status = QueueStatus.EXPIRED
            queueRepository.save(it.toEntity(user.toEntity(), it.reservableResource!!.toEntity()))
        }

        return queue.toModel()
    }

    fun save(queue: Queue): Queue? {
        return queue.user?.let { queue.toEntity(it.toEntity(), queue.reservableResource!!.toEntity()) }?.let {
            queueRepository.save(it).toModel()
        }
    }
}