package com.gcc.miti.auth.security

import com.gcc.miti.auth.dto.TokenDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(private val userDetailsService: UserDetailsService) {
    companion object {
        private const val accessTokenExpireTime = 30 * 60 * 1000L
        private const val refreshTokenExpireTime = 60 * 60 * 1000L * 3
        private const val tokenExpireTimeDev = 60 * 60 * 1000L * 1000000
    }

    @Value("\${JWT-SECRET}")
    private lateinit var secretKey: String
    lateinit var key: Key

    @Value("\${spring.profiles.active}")
    private lateinit var activeProfile: String

    @PostConstruct
    protected fun init() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }

    fun createToken(authentication: Authentication): TokenDto {
        val accessTokenRefreshTime = when (activeProfile) {
            "develop" -> tokenExpireTimeDev
            else -> accessTokenExpireTime
        }
//        val refreshTokenRefreshTime = when (activeProfile) {
//            "develop" -> tokenExpireTimeDev
//            else -> refreshTokenExpireTime
//        }
        val now = Date()
        val accessToken = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setSubject(authentication.name)
            .claim("Authorities", authentication.authorities.toString())
            .setIssuedAt(now)
            .setExpiration(Date(now.time + accessTokenRefreshTime))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
//        val refreshToken = Jwts.builder()
//            .setHeaderParam("typ", "JWT")
//            .setIssuedAt(now)
//            .setExpiration(Date(now.time + refreshTokenRefreshTime))
//            .signWith(key, SignatureAlgorithm.HS256)
//            .compact()
        return TokenDto(accessToken)
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(getUserPk(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUserPk(token: String): String {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body.subject
    }

    fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")?.substring(6)
    }

    fun validateToken(jwtToken: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }
}
