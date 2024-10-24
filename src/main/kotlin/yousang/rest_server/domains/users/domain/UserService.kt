package yousang.rest_server.domains.users.domain

import com.querydsl.core.QueryException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import yousang.rest_server.domains.users.domain.models.UserEntity
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder
) {
    fun registerUser(username: String, email: String, password: String): UserEntity {
        try {
            if (userRepository.findByUsername(username) != null) {
                throw IllegalArgumentException("username that already exists")
            }
            if (userRepository.findByEmail(email) != null) {
                throw IllegalArgumentException("email that already exists")
            }

            val encodedPassword = passwordEncoder.encode(password)
            val user = UserEntity(
                username = username, email = email, password = encodedPassword, uuid = UUID.randomUUID().toString()
            )
            userRepository.save(user)

            return user
        } catch (e: Exception) {
            throw Exception("failed to register user")
        } catch (e: QueryException) {
            throw QueryException("failed to register user")
        }
    }

    fun authenticate(email: String, password: String): UserEntity {
        try {
            val user = userRepository.findByEmail(email) ?: throw IllegalArgumentException("user not found")
            if (!passwordEncoder.matches(password, user.password)) {
                throw IllegalArgumentException("password is incorrect")
            }

            return user
        } catch (e: Exception) {
            throw Exception("failed to authenticate user")
        } catch (e: QueryException) {
            throw QueryException("failed to authenticate user")
        }
    }

    fun updateTokens(user: UserEntity, accessToken: String, refreshToken: String) {
        try {
            user.accessToken = accessToken
            user.refreshToken = refreshToken

            userRepository.save(user)
        } catch (e: Exception) {
            throw Exception("failed to update tokens")
        }
    }

    fun logout(userId: Long) {
        try {
            val user = getUserById(userId)
            user.accessToken = null
            user.refreshToken = null

            userRepository.save(user)
        } catch (e: Exception) {
            throw Exception("failed to logout")
        } catch (e: QueryException) {
            throw QueryException("failed to logout")
        }
    }

    fun deleteUser(userId: Long) {
        try {
            userRepository.deleteById(userId)
        } catch (e: Exception) {
            throw Exception("failed to delete user")
        } catch (e: QueryException) {
            throw QueryException("failed to delete user")
        }
    }

    fun getUserByUuid(uuid: String): UserEntity? {
        try {
            return userRepository.findByUuid(uuid)
        } catch (e: Exception) {
            throw Exception("failed to get user by uuid")
        } catch (e: QueryException) {
            throw QueryException("failed to get user by uuid")
        }
    }

    fun getUserById(userId: Long): UserEntity {
        try {
            return userRepository.findById(userId) ?: throw IllegalArgumentException("user not found")
        } catch (e: Exception) {
            throw Exception("failed to get user by id")
        } catch (e: QueryException) {
            throw QueryException("failed to get user by id")
        }
    }
}