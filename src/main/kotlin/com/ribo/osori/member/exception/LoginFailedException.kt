package com.ribo.osori.member.exception

import com.ribo.osori.common.exception.ErrorCode

class LoginFailedException(errorCode: ErrorCode?) : MemberException(errorCode)
