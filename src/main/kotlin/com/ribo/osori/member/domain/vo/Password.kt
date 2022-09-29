package com.ribo.osori.member.domain.vo

import com.google.common.base.Preconditions
import org.springframework.security.crypto.password.PasswordEncoder
import java.text.MessageFormat
import java.util.regex.Pattern
import javax.persistence.Column

class Password(
    @Column(name = "password", nullable = false)
    private var value: String? = null
) {

    private val PASSWORD_FORMAT_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W])).*"
    private val PASSWORD_FORMAT_PATTERN = Pattern.compile(PASSWORD_FORMAT_REGEX)
    private val MIN_LENGTH = 8
    private val MAX_LENGTH = 20


    fun encryptPassword(
        passwordEncoder: PasswordEncoder,
        value: String
    ): Password {
        validatePassword(value)
        return Password(passwordEncoder.encode(value))
    }

    private fun validatePassword(value: String) {
        Preconditions.checkArgument(
            value.length in MIN_LENGTH..MAX_LENGTH,
            MessageFormat.format(
                "비밀번호는 {0}자 이상 {1}자 이하입니다.",
                MIN_LENGTH,
                MAX_LENGTH
            )
        )
        Preconditions.checkArgument(
            PASSWORD_FORMAT_PATTERN.matcher(
                value
            ).matches(), "비밀번호는 대소문자, 숫자, 특수 문자를 포함해야 생성 가능합니다."
        )
    }

    fun isNotSamePassword(passwordEncoder: PasswordEncoder, inputPassword: String?): Boolean {
        return !passwordEncoder.matches(inputPassword, this.value)
    }
}