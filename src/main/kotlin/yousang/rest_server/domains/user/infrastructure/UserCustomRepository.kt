package yousang.rest_server.domains.user.infrastructure

import com.querydsl.core.QueryException
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import yousang.rest_server.domains.user.domain.models.QUserEntity
import yousang.rest_server.domains.user.domain.models.UserEntity
import java.util.*

@Repository
class UserCustomRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun findById(id: Long): UserEntity {
        val user = QUserEntity.userEntity

        return try {
            jpaQueryFactory.selectFrom(user).where(user.id.eq(id).and(user.deletion.deletedAt.isNull)).fetchOne()
                ?: throw NoSuchElementException("user not found")
        } catch (e: QueryException) {
            throw QueryException("user query failed")
        } catch (e: Exception) {
            throw Exception("user not found")
        }
    }

    fun findByUuid(uuid: UUID): UserEntity {
        val user = QUserEntity.userEntity

        return try {
            jpaQueryFactory.selectFrom(user).where(user.uuid.eq(uuid).and(user.deletion.deletedAt.isNull)).fetchOne()
                ?: throw NoSuchElementException("user not found")
        } catch (e: QueryException) {
            throw QueryException("user query failed")
        } catch (e: Exception) {
            throw Exception("user not found")
        }
    }
}