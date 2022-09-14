package com.ribo.osori.member.application.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank


data class SignupRequest(
    @field:Schema(description = "이메일")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 비어있을 수 없습니다.")
    val email: String,

    @field:Schema(description = "비밀번호")
    @NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
    val password: String,

    @field:Schema(description = "닉네임")
    @NotBlank(message = "닉네임은 비어있을 수 없습니다.")
    val nickName: String
){

}
