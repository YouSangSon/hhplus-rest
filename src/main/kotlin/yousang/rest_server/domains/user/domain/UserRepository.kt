package yousang.rest_server.domains.user.domain

import yousang.rest_server.domains.user.domain.models.UserEntity
import java.util.*

interface UserRepository {
    fun findById(userId: Long): UserEntity
    fun findByUuid(userUuid: UUID): UserEntity
}