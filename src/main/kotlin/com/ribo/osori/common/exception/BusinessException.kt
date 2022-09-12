package com.ribo.osori.common.exception


open class BusinessException(
    message: String?,
    private val errorCode: ErrorCode?
) : RuntimeException(message)
