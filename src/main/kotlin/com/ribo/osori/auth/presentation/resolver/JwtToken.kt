package com.ribo.osori.auth.presentation.resolver

data class JwtToken(
    private val accessToken: String,
    private val refreshToken: String
    )
