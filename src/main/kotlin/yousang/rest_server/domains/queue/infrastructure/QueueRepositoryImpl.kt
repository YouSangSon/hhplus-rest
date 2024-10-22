package yousang.rest_server.domains.queue.infrastructure

import org.springframework.stereotype.Repository
import yousang.rest_server.domains.queue.domain.QueueRepository
import yousang.rest_server.domains.queue.domain.models.QueueEntity
import yousang.rest_server.domains.user.domain.models.UserEntity

@Repository
class QueueRepositoryImpl(
    private val queueJpaRepository: QueueJpaRepository, private val queueCustomRepository: QueueCustomRepository
) : QueueRepository {
    override fun save(queue: QueueEntity): QueueEntity {
        return queueJpaRepository.save(queue)
    }
    override fun findByUserId(userId: Long): QueueEntity {
        return queueCustomRepository.findByUserId(userId)
    }

    override fun findByUser(user: UserEntity): QueueEntity {
        return queueCustomRepository.findByUser(user)
    }
}

