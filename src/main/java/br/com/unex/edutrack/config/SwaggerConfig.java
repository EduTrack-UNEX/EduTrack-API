package br.com.unex.edutrack.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public OpenAPI customOpenApi(){
        return new OpenAPI().info(new Info().title("EduTrack-API").version("1.0"));
    }
}
