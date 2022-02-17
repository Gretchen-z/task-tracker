package com.gretchen.tasktracker.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Task tracker service",
        version = "1.0.0",
        description = "Task tracker"))
public class SwaggerConfiguration {
}
