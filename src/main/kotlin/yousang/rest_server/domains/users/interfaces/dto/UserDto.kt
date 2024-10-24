package yousang.rest_server.domains.users.interfaces.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserDomainsRequest(
    val userId: Long? = null,
    val username: String? = null,
    val password: String? = null,
    val email: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null
) {
    fun validateUserId() {
        if (userId == null) {
            throw IllegalArgumentException("userId is required.")
        }
    }

    fun validateUsername() {
        if (username == null) {
            throw IllegalArgumentException("username is required.")
        }
    }

    fun validatePassword() {
        if (password == null) {
            throw IllegalArgumentException("password is required.")
        }
    }

    fun validateEmail() {
        if (email == null) {
            throw IllegalArgumentException("email is required.")
        }
    }

    fun validateAccessToken() {
        if (accessToken == null) {
            throw IllegalArgumentException("accessToken is required.")
        }
    }

    fun validateRefreshToken() {
        if (refreshToken == null) {
            throw IllegalArgumentException("refreshToken is required.")
        }
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserDomainsResponse(
    var userId: Long? = null,
    var username: String? = null,
    var email: String? = null,
    var accessToken: String? = null,
    var refreshToken: String? = null
)