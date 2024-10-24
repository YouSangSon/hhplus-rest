package yousang.rest_server.domains.users.infrastructure

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import yousang.rest_server.domains.users.domain.models.QUserEntity
import yousang.rest_server.domains.users.domain.models.UserEntity

@Repository
class UserCustomRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun findById(userId: Long): UserEntity? {
        val user = QUserEntity.userEntity

        return jpaQueryFactory.selectFrom(user).where(user.id.eq(userId).and(user.deletion.deletedAt.isNull)).fetchOne()
    }

    fun findByEmail(email: String): UserEntity? {
        val user = QUserEntity.userEntity

        return jpaQueryFactory.selectFrom(user).where(user.email.eq(email).and(user.deletion.deletedAt.isNull))
            .fetchOne()
    }

    fun findByUuid(uuid: String): UserEntity? {
        val user = QUserEntity.userEntity

        return jpaQueryFactory.selectFrom(user).where(user.uuid.eq(uuid).and(user.deletion.deletedAt.isNull)).fetchOne()
    }

    fun findByUsername(username: String): UserEntity? {
        val user = QUserEntity.userEntity

        return jpaQueryFactory.selectFrom(user).where(user.username.eq(username).and(user.deletion.deletedAt.isNull))
            .fetchOne()
    }

    fun deleteById(userId: Long) {
        val user = QUserEntity.userEntity

        jpaQueryFactory.update(user)
            .set(user.deletion.deletedAt, user.deletion.deletedAt.coalesce(user.deletion.deletedAt))
            .where(user.id.eq(userId)).execute()
    }
}