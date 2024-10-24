package yousang.rest_server.domains.queue.infrastructure

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import yousang.rest_server.domains.queue.domain.models.QQueueEntity
import yousang.rest_server.domains.queue.domain.models.QueueEntity
import yousang.rest_server.domains.queue.domain.models.QueueStatus

@Repository
class QueueCustomRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun findByUserIdAndStatus(userId: Long, status: QueueStatus): QueueEntity? {
        val queue = QQueueEntity.queueEntity

        return jpaQueryFactory.selectFrom(queue)
            .where(queue.userId.eq(userId).and(queue.status.eq(status)).and(queue.deletion.deletedAt.isNull)).fetchOne()
    }

    fun findMaxPosition(): Int {
        val qQueue = QQueueEntity.queueEntity

        val maxPosition =
            jpaQueryFactory.select(qQueue.position.max()).from(qQueue).where(qQueue.deletion.deletedAt.isNull)
                .fetchOne()

        return maxPosition ?: 0
    }

    fun findFirstByStatusOrderByPositionAsc(status: QueueStatus): QueueEntity? {
        val queue = QQueueEntity.queueEntity

        return jpaQueryFactory.selectFrom(queue).where(queue.status.eq(status).and(queue.deletion.deletedAt.isNull))
            .orderBy(queue.position.asc()).fetchFirst()
    }

    fun findByUserId(userId: Long): QueueEntity? {
        val queue = QQueueEntity.queueEntity

        return jpaQueryFactory.selectFrom(queue).where(queue.userId.eq(userId).and(queue.deletion.deletedAt.isNull))
            .fetchOne()
    }
}