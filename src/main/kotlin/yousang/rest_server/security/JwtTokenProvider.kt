package yousang.rest_server.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import yousang.rest_server.domains.queue.domain.models.Queue
import yousang.rest_server.domains.user.domain.UserService
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    private val userService: UserService,
    @Value("\${jwt.secret-key}") private val secretKey: String,
    @Value("\${jwt.token-validity-in-seconds}") private val tokenValidityInSeconds: Long
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(secretKey.toByteArray())

    fun createQueueToken(uuid: UUID, queue: Queue?): String {
        val claims = mutableMapOf<String, Any>()

        queue?.let {
            claims["queuePosition"] = it.queuePosition
            claims["expiredAt"] = it.expiredAt
        }

        val now = System.currentTimeMillis()
        val validity = Date(now + tokenValidityInSeconds * 1000)

        return Jwts.builder().subject(uuid.toString()).claims(claims).issuedAt(Date(now)).expiration(validity)
            .signWith(key).compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userUuid = getUuid(token)
        val user = userService.findByUuid(UUID.fromString(userUuid))
        val userPrincipal = UserPrincipal(user.id!!, user.uuid.toString(), emptyList())

        return UsernamePasswordAuthenticationToken(userPrincipal, "", userPrincipal.authorities)
    }

    fun getClaims(token: String): Claims {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload
    }

    // Extract UUID from token
    fun getUuid(token: String): String {
        return getClaims(token).subject
    }

    // Resolve token from the request header
    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")

        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

    // Validate token
    fun validateToken(token: String): Boolean {
        return try {
            val claims = getClaims(token)
            !claims.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }
}