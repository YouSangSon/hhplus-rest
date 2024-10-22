package yousang.rest_server.domains.queue.infrastructure

import com.querydsl.core.QueryException
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import yousang.rest_server.domains.queue.domain.models.QQueueEntity
import yousang.rest_server.domains.queue.domain.models.QueueEntity
import yousang.rest_server.domains.user.domain.models.UserEntity

@Repository
class QueueCustomRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun findByUserId(userId: Long): QueueEntity {
        val queue = QQueueEntity.queueEntity

        return try {
            jpaQueryFactory.selectFrom(queue).where(queue.user.id.eq(userId).and(queue.deletion.deletedAt.isNull))
                .fetchOne() ?: throw NoSuchElementException("queue not found")
        } catch (e: QueryException) {
            throw QueryException("queue query failed")
        } catch (e: Exception) {
            throw Exception("queue not found")
        }
    }

    fun findByUser(user: UserEntity): QueueEntity {
        val queueEntity = QQueueEntity.queueEntity

        return try {
            jpaQueryFactory.selectFrom(queueEntity)
                .where(queueEntity.user.eq(user).and(queueEntity.deletion.deletedAt.isNull)).fetchOne()
                ?: throw NoSuchElementException("queue not found")
        } catch (e: QueryException) {
            throw QueryException("queue query failed")
        } catch (e: Exception) {
            throw Exception("queue not found")
        }
    }
}