package yousang.rest_server.domains.user.infrastructure

import org.springframework.stereotype.Repository
import yousang.rest_server.domains.user.domain.UserRepository
import yousang.rest_server.domains.user.domain.models.UserEntity
import java.util.*

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository, private val userCustomRepository: UserCustomRepository
) : UserRepository {
    override fun findById(userId: Long): UserEntity {
        return userCustomRepository.findById(userId)
    }

    override fun findByUuid(userUuid: UUID): UserEntity {
        return userCustomRepository.findByUuid(userUuid)
    }
}

