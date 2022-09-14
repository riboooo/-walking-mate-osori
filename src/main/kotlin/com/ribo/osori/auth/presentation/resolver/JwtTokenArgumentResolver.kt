package com.ribo.osori.auth.presentation.resolver

import com.google.common.base.Strings
import com.ribo.osori.auth.exception.UnauthorizedException
import com.ribo.osori.common.exception.ErrorCode
import com.ribo.osori.common.util.AuthorizationExtractor
import com.ribo.osori.common.util.CookieUtil
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

class JwtTokenArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(RequestTokens::class.java)
    }

    @Throws(Exception::class)
    override fun resolveArgument(
        parameter: MethodParameter, mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?
    ): Any? {
        val request = webRequest.getNativeRequest(HttpServletRequest::class.java)
        val accessToken = getAccessTokenByRequest(request)
        val refreshToken: String? = request?.let { CookieUtil.getTokenCookieValue(it) }
        return refreshToken?.let { accessToken?.let { it1 -> JwtToken(it1, it) } }
    }

    private fun getAccessTokenByRequest(httpServletRequest: HttpServletRequest?): String? {
        val token: String? = httpServletRequest?.let { AuthorizationExtractor().extract(it) }
        if (Strings.isNullOrEmpty(token)) {
            throw UnauthorizedException(ErrorCode.NOT_FOUND_TOKEN)
        }
        return token
    }
}
