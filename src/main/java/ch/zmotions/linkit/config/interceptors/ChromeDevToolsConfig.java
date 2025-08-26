package ch.zmotions.linkit.config.interceptors;

import ch.zmotions.linkit.config.interceptors.devtools.ChromeDevToolsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ChromeDevToolsConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ChromeDevToolsInterceptor());
	}
}
