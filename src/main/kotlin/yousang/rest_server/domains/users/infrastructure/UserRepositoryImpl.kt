package yousang.rest_server.domains.users.infrastructure

import org.springframework.stereotype.Repository
import yousang.rest_server.domains.users.domain.UserRepository
import yousang.rest_server.domains.users.domain.models.UserEntity

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository, private val userCustomRepository: UserCustomRepository
) : UserRepository {
    override fun findById(userId: Long): UserEntity? {
        return userCustomRepository.findById(userId)
    }

    override fun findByEmail(email: String): UserEntity? {
        return userCustomRepository.findByEmail(email)
    }

    override fun findByUuid(uuid: String): UserEntity? {
        return userCustomRepository.findByUuid(uuid)
    }

    override fun findByUsername(username: String): UserEntity? {
        return userCustomRepository.findByUsername(username)
    }

    override fun save(user: UserEntity): UserEntity {
        return userJpaRepository.save(user)
    }

    override fun deleteById(userId: Long) {
        userCustomRepository.deleteById(userId)
    }
}

