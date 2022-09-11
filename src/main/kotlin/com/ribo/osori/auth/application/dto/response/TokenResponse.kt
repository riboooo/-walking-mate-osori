package com.ribo.osori.auth.application.dto.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val memberResponse : MemberResponse,
) {
}

data class MemberResponse(
    val email: String,
    val nickName: String,
){

}