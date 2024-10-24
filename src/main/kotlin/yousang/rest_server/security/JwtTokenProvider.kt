package yousang.rest_server.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret-key}") private val secretKey: String,
    @Value("\${jwt.access-token-validity}") private val accessTokenValidityInSeconds: Long = 3600 * 24 * 3 * 1000, // 3 days
    @Value("\${jwt.refresh-token-validity}") private val refreshTokenValidityInSeconds: Long = 3600 * 24 * 15 * 1000, // 15 days
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(secretKey.toByteArray())

    init {
        require(secretKey.toByteArray().size >= 32) {
            "secret key must be at least 256 bits long"
        }
    }

    fun generateAccessToken(userId: Long, uuid: String): String {
        val now = Date()
        val validity = Date(now.time + accessTokenValidityInSeconds)

        return Jwts.builder()
            .subject(userId.toString())
            .claim("uuid", uuid)
            .issuedAt(now)
            .expiration(validity)
            .signWith(key)
            .compact()
    }

    fun generateRefreshToken(userId: Long, uuid: String): String {
        val now = Date()
        val validity = Date(now.time + refreshTokenValidityInSeconds)

        return Jwts.builder()
            .subject(userId.toString())
            .claim("uuid", uuid)
            .issuedAt(now)
            .expiration(validity)
            .signWith(key)
            .compact()
    }

    fun getAuthentication(token: String): CustomUserDetails {
        val claims = getClaims(token)
        val userId = claims.subject.toLong()
        val uuid = claims.get("uuid", String::class.java)
        return CustomUserDetails(userId, uuid)
    }

    fun getClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims = getClaims(token)
            !claims.expiration.before(Date())
        } catch (e: JwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}