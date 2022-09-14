package com.ribo.osori.common.util

import com.google.common.base.Strings
import org.springframework.http.HttpHeaders
import java.util.*
import javax.servlet.http.HttpServletRequest

class AuthorizationExtractor {
    private val BEARER_TYPE = "Bearer"

    fun extract(request: HttpServletRequest): String? {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        return if (Strings.isNullOrEmpty(header)) {
            null
        } else extractToken(header)
    }

    private fun extractToken(header: String): String? {
        if (header.lowercase(Locale.getDefault()).startsWith(BEARER_TYPE.lowercase(Locale.getDefault()))) {
            var authHeaderValue = header.substring(BEARER_TYPE.length).trim { it <= ' ' }
            val commaIndex = authHeaderValue.indexOf(',')
            if (commaIndex > 0) {
                authHeaderValue = authHeaderValue.substring(0, commaIndex)
            }
            return authHeaderValue
        }
        return null
    }
}