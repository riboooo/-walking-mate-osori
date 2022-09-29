package com.ribo.osori.common.config

import com.ribo.osori.auth.application.JwtProvider
import com.ribo.osori.auth.presentation.AuthInterceptor
import com.ribo.osori.auth.presentation.resolver.JwtTokenArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry

@Configuration
class WebConfig(
    private val jwtProvider: JwtProvider,
) {
    private val ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH"



    fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods(*ALLOWED_METHOD_NAMES.split(",").toTypedArray())
            .allowCredentials(true)
            .exposedHeaders(HttpHeaders.LOCATION)
            .maxAge(3600)
    }

    fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(AuthInterceptor(jwtProvider))
    }

    fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver?>) {
        resolvers.add(JwtTokenArgumentResolver())
    }
}