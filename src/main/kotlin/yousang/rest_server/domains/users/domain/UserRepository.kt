package yousang.rest_server.domains.users.domain

import yousang.rest_server.domains.users.domain.models.UserEntity

interface UserRepository {
    fun findById(userId: Long): UserEntity?
    fun findByEmail(email: String): UserEntity?
    fun findByUuid(uuid: String): UserEntity?
    fun findByUsername(username: String): UserEntity?
    fun save(user: UserEntity): UserEntity
    fun deleteById(userId: Long)
}