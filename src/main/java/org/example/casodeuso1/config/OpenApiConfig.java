package org.example.casodeuso1.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração dos metadados da documentação OpenAPI do primeiro caso de uso.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API REST - Caso de Uso 1: Energia e Tráfego Wi-Fi",
                version = "1.0.0",
                description = "API para operações relacionadas ao monitoramento de energia elétrica e ao tráfego Wi-Fi."
        )
)
public class OpenApiConfig {
}
