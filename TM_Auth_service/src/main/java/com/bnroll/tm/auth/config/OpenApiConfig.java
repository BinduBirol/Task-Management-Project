package com.bnroll.tm.auth.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI taskManagementOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Task Management Authentication API")
						.description("Authentication and User Management APIs").version("v1.0")
						.contact(new Contact().name("Bindu Birol").email("bindu@example.com")))
				.externalDocs(new ExternalDocumentation().description("Project Repository")
						.url("https://github.com/BinduBirol/Task-Management-Project.git"));
	}
}