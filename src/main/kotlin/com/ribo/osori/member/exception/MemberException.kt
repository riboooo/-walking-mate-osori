package com.ribo.osori.member.exception

import com.ribo.osori.common.exception.BusinessException
import com.ribo.osori.common.exception.ErrorCode

open class MemberException : BusinessException {
    constructor(message: String?, errorCode: ErrorCode?) : super(message, errorCode) {}
    constructor(errorCode: ErrorCode?) : super(errorCode?.message, errorCode) {}
}
