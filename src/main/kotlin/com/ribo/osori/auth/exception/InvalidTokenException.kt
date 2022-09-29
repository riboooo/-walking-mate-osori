package com.ribo.osori.auth.exception

import com.ribo.osori.common.exception.BusinessException
import com.ribo.osori.common.exception.ErrorCode
import java.text.MessageFormat

class InvalidTokenException(token: String?, errorCode: ErrorCode?) :
    BusinessException(MessageFormat.format(ERROR_MESSAGE_FORMAT, token), errorCode) {
    companion object {
        private const val ERROR_MESSAGE_FORMAT = "유효하지 않은 토큰입니다. 토큰 : {0}"
    }
}
