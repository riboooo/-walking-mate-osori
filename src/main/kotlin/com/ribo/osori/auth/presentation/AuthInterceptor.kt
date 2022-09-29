package com.ribo.osori.auth.presentation

import com.google.common.base.Strings
import com.ribo.osori.auth.aop.LoginRequired
import com.ribo.osori.auth.application.JwtProvider
import com.ribo.osori.auth.exception.InvalidTokenException
import com.ribo.osori.auth.exception.UnauthorizedException
import com.ribo.osori.common.exception.ErrorCode
import com.ribo.osori.common.util.AuthorizationExtractor
import org.springframework.web.cors.CorsUtils
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthInterceptor(
    private val jwtProvider: JwtProvider
) : HandlerInterceptor{
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        if (CorsUtils.isPreFlightRequest(request!!) || isNotLoginRequired(handler)) {
            return true
        }
        val accessToken: String? = AuthorizationExtractor().extract(request)
        accessToken?.let {
            validateAccessToken(it)
        }

        return true
    }

    private fun isNotLoginRequired(handler: Any): Boolean {
        if (handler !is HandlerMethod) {
            return true
        }
        val auth: LoginRequired? = handler.getMethodAnnotation(LoginRequired::class.java)
        return Objects.isNull(auth)
    }

    private fun validateAccessToken(accessToken: String) {
        if (Strings.isNullOrEmpty(accessToken)) {
            throw UnauthorizedException(ErrorCode.NOT_FOUND_TOKEN)
        }
        if (!jwtProvider.validateToken(accessToken)) {
            throw InvalidTokenException(accessToken, ErrorCode.INVALID_TOKEN)
        }
    }
}