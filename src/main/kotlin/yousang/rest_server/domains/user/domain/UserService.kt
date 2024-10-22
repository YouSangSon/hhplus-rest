package yousang.rest_server.domains.user.domain

import org.springframework.stereotype.Service
import yousang.rest_server.domains.user.domain.models.User
import yousang.rest_server.domains.user.domain.models.UserEntity
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun findById(userId: Long): User {
        return userRepository.findById(userId).toModel()
    }

    fun findByUuid(userUuid: UUID): User {
        return userRepository.findByUuid(userUuid).toModel()
    }
}