package com.ribo.osori.auth.application.dto

data class TokenDto(
    val value: String,
    val expiredTime: Long,
) {
}