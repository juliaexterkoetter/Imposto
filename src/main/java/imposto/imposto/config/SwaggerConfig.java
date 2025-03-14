package imposto.imposto.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Impostos")
                        .version("v1")
                        .description("API para gerenciamento e cálculo de impostos  dinâmicos, com segurança via Spring Security e JWT"));
    }
}
