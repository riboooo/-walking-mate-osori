package com.ribo.osori.auth.application

import com.ribo.osori.auth.application.dto.TokenDto
import java.util.*

interface JwtProvider {
    fun createAccessToken(payload: String?): TokenDto?

    fun createRefreshToken(): TokenDto?

    fun validateToken(token: String?): Boolean

    fun getExpiredDate(token: String?): Date?

    fun getSubject(token: String?): String?
}