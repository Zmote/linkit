package ch.zmotions.linkit.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("LinkIt API")
						.version("0.9.9-SNAPSHOT")
						.description("Documentation of the API using Swaager OpenAPI"));
	}
}