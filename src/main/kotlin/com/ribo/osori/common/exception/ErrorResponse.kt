package com.ribo.osori.common.exception

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import java.util.stream.Collectors

class ErrorResponse (
    val message: String,
    val code : String,
    val errors: List<FieldError>?,
        ){


    fun of(code: ErrorCode): ErrorResponse {
        return ErrorResponse(code.message, code.code, null)
    }

    fun of(code: ErrorCode, message: String): ErrorResponse {
        return ErrorResponse(message, code.code, null)
    }

    fun of(code: ErrorCode, bindingResult: BindingResult): ErrorResponse {
        return ErrorResponse(code.message, code.code, FieldError.of(bindingResult))
    }

    class FieldError private constructor(
        private val field: String,
        private val value: String,
        private val reason: String?
    ) {
        companion object {
            fun of(bindingResult: BindingResult): List<FieldError> {
                return bindingResult.fieldErrors.stream()
                    .map { error: org.springframework.validation.FieldError ->
                        FieldError(
                            error.field,
                            if (error.rejectedValue == null) "" else error.rejectedValue.toString(),
                            error.defaultMessage
                        )
                    }
                    .collect(Collectors.toList())
            }
        }
    }
}