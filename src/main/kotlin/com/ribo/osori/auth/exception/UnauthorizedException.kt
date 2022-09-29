package com.ribo.osori.auth.exception

import com.ribo.osori.common.exception.BusinessException
import com.ribo.osori.common.exception.ErrorCode


class UnauthorizedException(errorCode: ErrorCode) :
    BusinessException(errorCode.message, errorCode)
