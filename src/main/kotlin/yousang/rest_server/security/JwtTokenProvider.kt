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
    @Value("\${jwt.queue-token-validity}") private val queueTokenValidityInSeconds: Long = 300 * 1000 // 5 minutes
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

//@Component
//class JwtTokenProvider(
//    @Value("\${jwt.secret}")
//    private val secretKey: String
//) {
//
//    private val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray())
//    val accessTokenValidity: Long = 1000L * 60 * 30 // 30분
//    val refreshTokenValidity: Long = 1000L * 60 * 60 * 24 * 7 // 7일
//
//    fun generateAccessToken(userId: Long, uuid: String): String {
//        val claims = Jwts.claims().setSubject(userId.toString())
//        claims["uuid"] = uuid
//        claims["type"] = "ACCESS"
//        val now = Date()
//        val validity = Date(now.time + accessTokenValidity)
//
//        return Jwts.builder()
//            .setClaims(claims)
//            .setIssuedAt(now)
//            .setExpiration(validity)
//            .signWith(key, SignatureAlgorithm.HS256)
//            .compact()
//    }
//
//    fun generateRefreshToken(userId: Long, uuid: String): String {
//        val claims = Jwts.claims().setSubject(userId.toString())
//        claims["uuid"] = uuid
//        claims["type"] = "REFRESH"
//        val now = Date()
//        val validity = Date(now.time + refreshTokenValidity)
//
//        return Jwts.builder()
//            .setClaims(claims)
//            .setIssuedAt(now)
//            .setExpiration(validity)
//            .signWith(key, SignatureAlgorithm.HS256)
//            .compact()
//    }
//
//    fun validateToken(token: String): Boolean {
//        try {
//            val claims = getClaims(token)
//            return !claims.expiration.before(Date())
//        } catch (e: JwtException) {
//            return false
//        } catch (e: IllegalArgumentException) {
//            return false
//        }
//    }
//
//    fun getAuthentication(token: String): Authentication {
//        val claims = getClaims(token)
//        val userId = claims.subject.toLong()
//        val uuid = claims["uuid"] as String
//
//        val principal = CustomUserDetails(userId, uuid)
//        return UsernamePasswordAuthenticationToken(principal, "", principal.authorities)
//    }
//
//    fun getClaims(token: String): Claims {
//        return Jwts.parserBuilder()
//            .setSigningKey(key)
//            .build()
//            .parseClaimsJws(token)
//            .body
//    }
//}