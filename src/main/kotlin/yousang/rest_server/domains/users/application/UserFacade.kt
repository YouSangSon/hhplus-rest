package yousang.rest_server.domains.users.application

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yousang.rest_server.domains.queue.application.QueueFacade
import yousang.rest_server.domains.reservations.application.ReservationFacade
import yousang.rest_server.domains.users.domain.UserService
import yousang.rest_server.domains.users.interfaces.dto.UserDomainsRequest
import yousang.rest_server.domains.users.interfaces.dto.UserDomainsResponse
import yousang.rest_server.security.JwtTokenProvider

@Component
class UserFacade(
    private val userService: UserService,
    private val accountsFacade: AccountsFacade,
    private val queueFacade: QueueFacade,
    private val reservationFacade: ReservationFacade,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional
    fun register(request: UserDomainsRequest): UserDomainsResponse {
        request.validateUsername()
        request.validateEmail()
        request.validatePassword()

        val user = userService.registerUser(request.username!!, request.email!!, request.password!!)

        accountsFacade.createAccount(user.id!!)

        val accessToken = jwtTokenProvider.generateAccessToken(user.id, user.uuid!!)
        val refreshToken = jwtTokenProvider.generateRefreshToken(user.id, user.uuid!!)

        userService.updateTokens(user, accessToken, refreshToken)

        queueFacade.joinQueue(user.id)

        return UserDomainsResponse(
            userId = user.id,
            username = user.username,
            email = user.email,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    @Transactional
    fun login(request: UserDomainsRequest): UserDomainsResponse {
        request.validateUsername()
        request.validatePassword()

        val user = userService.authenticate(request.username!!, request.password!!)

        val accessToken = jwtTokenProvider.generateAccessToken(user.id!!, user.uuid!!)
        val refreshToken = jwtTokenProvider.generateRefreshToken(user.id, user.uuid!!)

        userService.updateTokens(user, accessToken, refreshToken)

        return UserDomainsResponse(
            userId = user.id,
            username = user.username,
            email = user.email,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun refreshAccessToken(request: UserDomainsRequest): UserDomainsResponse {
        request.validateUserId()
        request.validateRefreshToken()

        val refreshToken = request.refreshToken!!

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw IllegalArgumentException("invalid refresh token")
        }

        val claims = jwtTokenProvider.getClaims(refreshToken)
        val userId = claims.subject.toLong()

        val user = userService.getUserById(userId)

        if (user.refreshToken != refreshToken) {
            throw IllegalArgumentException("refresh token does not match")
        }

        val newAccessToken = jwtTokenProvider.generateAccessToken(user.id!!, user.uuid!!)

        userService.updateTokens(user, newAccessToken, refreshToken)

        return UserDomainsResponse(
            userId = user.id,
            username = user.username,
            email = user.email,
            accessToken = newAccessToken,
            refreshToken = refreshToken
        )
    }

    @Transactional
    fun logout(userId: Long) {
        userService.logout(userId)
    }

    @Transactional
    fun deleteUser(userId: Long) {
        reservationFacade.cancelAllReservationsByUser(userId)
        queueFacade.removeUserFromQueue(userId)
        accountsFacade.deleteAccountByUserId(userId)
        userService.deleteUser(userId)
    }
}