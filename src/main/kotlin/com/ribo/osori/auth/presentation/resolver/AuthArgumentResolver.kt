package com.ribo.osori.auth.presentation.resolver

import com.google.common.base.Strings
import com.ribo.osori.auth.application.JwtProvider
import com.ribo.osori.auth.domain.login.Login
import com.ribo.osori.auth.domain.login.LoginMember
import com.ribo.osori.auth.exception.InvalidTokenException
import com.ribo.osori.common.exception.ErrorCode
import com.ribo.osori.common.util.AuthorizationExtractor
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

class AuthArgumentResolver(
    private val jwtProvider: JwtProvider,
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(Login::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter, mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?
    ): Any? {
        val httpServletRequest = webRequest.getNativeRequest(
            HttpServletRequest::class.java
        )
        val accessToken: String? = httpServletRequest?.let { AuthorizationExtractor().extract(it) }
        if (Strings.isNullOrEmpty(accessToken)) {
            return LoginMember(id = null, accessToken = null)
        }
        return accessToken?.let { getLoginMember(it) }
    }

    private fun getLoginMember(accessToken: String): LoginMember {
        val id = convertMemberId(accessToken)
        return LoginMember(id, accessToken)
    }

    private fun convertMemberId(token: String): Long {
        return try {
            jwtProvider.getSubject(token)!!.toLong()
        } catch (e: NumberFormatException) {
            throw InvalidTokenException(token, ErrorCode.INVALID_TOKEN)
        }
    }

}
