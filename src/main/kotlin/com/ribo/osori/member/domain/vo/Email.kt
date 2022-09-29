package com.ribo.osori.member.domain.vo

import com.google.common.base.Preconditions
import org.apache.commons.lang3.StringUtils
import java.text.MessageFormat
import java.util.regex.Pattern
import javax.persistence.Column

class Email(
    @Column(name = "email", nullable = false, unique = true)
    private var value: String? = null
) {
    private val EMAIL_FORMAT_REGEX = "^[_a-z\\d-]+(.[_a-z\\d-]+)*@(?:\\w+\\.)+\\w+"
    private val EMAIL_FORMAT_PATTERN = Pattern.compile(EMAIL_FORMAT_REGEX)
    private val MAX_EMAIL_LENGTH = 50

    fun Email(value: String) {
        validateEmail(value)
        this.value = value
    }

    private fun validateEmail(value: String) {
        Preconditions.checkArgument(
            !StringUtils.isBlank(value),
            MessageFormat.format("이메일은 비어있을 수 없습니다. 현재 이메일 : {0}", value)
        )
        Preconditions.checkArgument(
            value.length <= MAX_EMAIL_LENGTH,
            MessageFormat.format(
                "이메일은 {0}글자를 초과할 수 없습니다. 현재 이메일 길이: {1}",
                MAX_EMAIL_LENGTH,
                value.length
            )
        )
        Preconditions.checkArgument(
            EMAIL_FORMAT_PATTERN.matcher(value).matches(),
            MessageFormat.format("올바른 이메일 형식이 아닙니다. 현재 이메일 : {0}", value)
        )
    }
}