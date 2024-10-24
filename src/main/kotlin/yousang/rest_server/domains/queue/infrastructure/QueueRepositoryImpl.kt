package yousang.rest_server.domains.queue.infrastructure

import org.springframework.stereotype.Repository
import yousang.rest_server.domains.queue.domain.QueueRepository
import yousang.rest_server.domains.queue.domain.models.QueueEntity
import yousang.rest_server.domains.queue.domain.models.QueueStatus

@Repository
class QueueRepositoryImpl(
    private val queueJpaRepository: QueueJpaRepository, private val queueCustomRepository: QueueCustomRepository
) : QueueRepository {
    override fun save(queueEntity: QueueEntity): QueueEntity {
        return queueJpaRepository.save(queueEntity)
    }

    override fun findByUserIdAndStatus(userId: Long, status: QueueStatus): QueueEntity? {
        return queueCustomRepository.findByUserIdAndStatus(userId, status)
    }

    override fun findMaxPosition(): Int {
        return queueCustomRepository.findMaxPosition()
    }

    override fun findFirstByStatusOrderByPositionAsc(status: QueueStatus): QueueEntity? {
        return queueCustomRepository.findFirstByStatusOrderByPositionAsc(status)
    }

    override fun findByUserId(userId: Long): QueueEntity? {
        return queueCustomRepository.findByUserId(userId)
    }

    override fun delete(queueEntity: QueueEntity) {
        queueJpaRepository.delete(queueEntity)
    }
}

