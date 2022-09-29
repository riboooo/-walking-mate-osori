package com.ribo.osori.member.domain.vo

import com.google.common.base.Preconditions
import org.apache.commons.lang3.StringUtils
import java.text.MessageFormat
import javax.persistence.Column

class NickName (
    // 생성전에  validateNickName(value); 실행해야함
    @Column(name = "nickname", nullable = false)
    private var value: String? = null
        ){
    private val MAX_NICKNAME_LENGTH = 15

    private fun validateNickName(value: String) {
        Preconditions.checkArgument(
            !StringUtils.isBlank(value),
            MessageFormat.format("닉네임은 비어있을 수 없습니다. 현재 닉네임 : {0}", value)
        )
        Preconditions.checkArgument(
            value.length <= MAX_NICKNAME_LENGTH,
            MessageFormat.format("닉네임은 {0}글자를 초과할 수 없습니다. 현재 닉네임 길이 : {1}", MAX_NICKNAME_LENGTH, value.length)
        )
    }
}