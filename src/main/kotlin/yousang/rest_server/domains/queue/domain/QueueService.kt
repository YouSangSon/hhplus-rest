package yousang.rest_server.domains.queue.domain

import com.querydsl.core.QueryException
import org.springframework.stereotype.Service
import yousang.rest_server.domains.queue.domain.models.QueueEntity
import yousang.rest_server.domains.queue.domain.models.QueueStatus

@Service
class QueueService(
    private val queueRepository: QueueRepository
) {
    fun joinQueue(userId: Long): QueueEntity {
        return try {
            val existingQueue = queueRepository.findByUserIdAndStatus(userId, QueueStatus.WAITING)
            if (existingQueue != null) {
                return existingQueue
            }

            val maxPosition = queueRepository.findMaxPosition()
            val newPosition = maxPosition + 1

            val queueEntity = QueueEntity(
                userId = userId, position = newPosition
            )

            queueRepository.save(queueEntity)
        } catch (e: Exception) {
            throw Exception(e.message)
        } catch (q: QueryException) {
            throw Exception(q.message)
        }
    }

    fun getQueuePosition(userId: Long): Int {
        return try {
            val queue = queueRepository.findByUserIdAndStatus(userId, QueueStatus.WAITING)
                ?: throw IllegalArgumentException("no queue information found")

            queue.position
        } catch (e: Exception) {
            throw Exception(e.message)
        } catch (q: QueryException) {
            throw Exception(q.message)
        }
    }

    fun findFirstWaitingQueue(): QueueEntity {
        return try {
            queueRepository.findFirstByStatusOrderByPositionAsc(QueueStatus.WAITING)
                ?: throw IllegalArgumentException("no queue information found")
        } catch (e: Exception) {
            throw Exception(e.message)
        } catch (q: QueryException) {
            throw Exception(q.message)
        }
    }

    fun isActiveUser(userId: Long): Boolean {
        return try {
            queueRepository.findByUserIdAndStatus(userId, QueueStatus.ACTIVE) ?: return false

            true
        } catch (e: Exception) {
            throw Exception(e.message)
        } catch (q: QueryException) {
            throw Exception(q.message)
        }
    }

    fun activateUser(userId: Long): QueueEntity {
        return try {
            val queue = queueRepository.findByUserIdAndStatus(userId, QueueStatus.WAITING)
                ?: throw IllegalArgumentException("no queue information found")

            queue.activate()
            queueRepository.save(queue)
        } catch (e: Exception) {
            throw Exception(e.message)
        } catch (q: QueryException) {
            throw Exception(q.message)
        }
    }

    fun expireUser(userId: Long): QueueEntity {
        return try {
            val queue = queueRepository.findByUserIdAndStatus(userId, QueueStatus.ACTIVE)
                ?: throw IllegalArgumentException("no queue information found")

            queue.expire()
            queueRepository.save(queue)
        } catch (e: Exception) {
            throw Exception(e.message)
        } catch (q: QueryException) {
            throw Exception(q.message)
        }
    }

    fun removeUserFromQueue(userId: Long) {
        try {
            val queue =
                queueRepository.findByUserId(userId) ?: throw IllegalArgumentException("no queue information found")
            queueRepository.delete(queue)
        } catch (e: Exception) {
            throw Exception(e.message)
        } catch (q: QueryException) {
            throw Exception(q.message)
        }
    }
}