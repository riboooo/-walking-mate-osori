package com.ribo.osori.member.exception

import com.ribo.osori.common.exception.ErrorCode
import java.text.MessageFormat

class DuplicateEmailException(value: String?, errorCode: ErrorCode?) :
    MemberException(MessageFormat.format(ERROR_MESSAGE_FORMAT, value), errorCode) {
    companion object {
        private const val ERROR_MESSAGE_FORMAT = "중복된 이메일입니다. 현재 이메일 : {0}"
    }
}
