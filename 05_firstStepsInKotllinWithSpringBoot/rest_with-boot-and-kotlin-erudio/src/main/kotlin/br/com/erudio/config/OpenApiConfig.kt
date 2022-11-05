package br.com.erudio.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenConfig(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("API with Kotlin and Spring Boot")
                    .version("v1")
                    .description("Descriptions")
                    .termsOfService("Terms")
                    .license(
                        License().name("Apache 2.0")
                            .url("http://exemplo.com.br/exemplo-exemplo")
            )
        )
    }
}