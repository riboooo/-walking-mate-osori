package com.ribo.osori.auth.infrastructure

import com.ribo.osori.auth.application.JwtProvider
import com.ribo.osori.auth.application.dto.TokenDto
import com.ribo.osori.auth.exception.InvalidTokenException
import com.ribo.osori.common.exception.ErrorCode
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider(
    @param:Value("\${jwt.issuer}") private val issuer: String,
    @Value("\${jwt.secret-key}") secretKey: String,
    @Value("\${jwt.access-token.expire-length}") accessTokenValidityInMilliseconds: Long,
    @Value("\${jwt.refresh-token.expire-length}") refreshTokenValidityInMilliseconds: Long
) :
    JwtProvider {
    private val secretKey: Key
    private val accessTokenValidityInMilliseconds: Long
    private val refreshTokenValidityInMilliseconds: Long

    init {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds
    }

    override fun createAccessToken(payload: String?): TokenDto? {
        val token = payload?.let { createToken(it, accessTokenValidityInMilliseconds) }
        return token?.let { TokenDto(it, accessTokenValidityInMilliseconds) }
    }

    override fun createRefreshToken(): TokenDto {
        val token = createToken(UUID.randomUUID().toString(),  refreshTokenValidityInMilliseconds)
        return TokenDto(token, refreshTokenValidityInMilliseconds)
    }

    private fun createToken(payload: String, validityInMilliseconds: Long): String {
        val now = Date()
        val validity = Date(now.time + validityInMilliseconds)
        return Jwts.builder()
            .setSubject(payload)
//            .claim(COUPLE_CLAIM_NAME, coupleId)
            .setIssuer(issuer)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    override fun validateToken(token: String?): Boolean {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
            !claims.expiration.before(Date())
        } catch (e: JwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    override fun getExpiredDate(token: String?): Date? {
        return token?.let { getClaims(it).expiration }
    }

    override fun getSubject(token: String?): String? {
        return token?.let { getClaims(it).subject }
    }

    private fun getClaims(token: String): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            e.claims
        } catch (e: JwtException) {
            throw InvalidTokenException(token, ErrorCode.INVALID_TOKEN)
        } catch (e: IllegalArgumentException) {
            throw InvalidTokenException(token, ErrorCode.INVALID_TOKEN)
        }
    }

}
