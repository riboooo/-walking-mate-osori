package com.ribo.osori.auth.presentation.resolver

import io.swagger.v3.oas.annotations.Parameter
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Parameter(hidden = true)
annotation class RequestTokens
