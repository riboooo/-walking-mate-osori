package com.ribo.osori.common.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders


@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI{
        val securitySchemeName = "bearer"
        return OpenAPI()
            .components(
                Components()
                    .addSecuritySchemes(
                        securitySchemeName, SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme(securitySchemeName)
                            .bearerFormat("JWT")
                            .`in`(SecurityScheme.In.HEADER)
                            .name(HttpHeaders.AUTHORIZATION)
                    )
            )
            .info(Info().title("Osori"))
    }
}