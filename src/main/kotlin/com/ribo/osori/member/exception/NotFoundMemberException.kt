package com.ribo.osori.member.exception

import com.ribo.osori.common.exception.ErrorCode
import java.text.MessageFormat

class NotFoundMemberException(errorCode: ErrorCode?, id: Long?) :
    MemberException(MessageFormat.format(ERROR_MESSAGE_FORMAT, id), errorCode) {
    companion object {
        private const val ERROR_MESSAGE_FORMAT = "존재하지 않는 회원입니다. 회원 번호: {0}"
    }
}
