package com.ribo.osori.common.util

import com.ribo.osori.auth.application.dto.TokenDto
import com.ribo.osori.auth.exception.UnauthorizedException
import com.ribo.osori.common.exception.ErrorCode
import org.springframework.http.ResponseCookie
import java.time.Duration
import java.util.*
import java.util.function.Supplier
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest

object CookieUtil {
    private const val REFRESH_TOKEN_COOKIE_NAME = "refreshToken"
    fun createTokenCookie(tokenDto: TokenDto): ResponseCookie {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, tokenDto.value)
            .path("/")
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .maxAge(Duration.ofMillis(tokenDto.expiredTime))
            .build()
    }

    fun getTokenCookieValue(request: HttpServletRequest): String {
        val findCookie = Arrays.stream(request.cookies)
            .filter { cookie: Cookie -> cookie.name == REFRESH_TOKEN_COOKIE_NAME }
            .findAny()
            .orElseThrow(Supplier<RuntimeException> { UnauthorizedException(ErrorCode.NOT_FOUND_TOKEN) })
        return findCookie.value
    }
}
