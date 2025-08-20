package ch.zmotions.linkit.config.views;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@EnableConfigurationProperties({ThymeleafProperties.class})
@ConditionalOnClass({TemplateMode.class, SpringTemplateEngine.class})
@AutoConfigureAfter({ThymeleafAutoConfiguration.class})
public class ThymeleafAdditionalViewFolderConfig {
	private final ApplicationContext applicationContext;
	private final ThymeleafProperties properties;

	public ThymeleafAdditionalViewFolderConfig(
			ApplicationContext applicationContext,
			ThymeleafProperties properties
	) {
		this.applicationContext = applicationContext;
		this.properties = properties;
	}

	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(this.applicationContext);
		resolver.setPrefix("classpath:/views/");
		resolver.setSuffix(this.properties.getSuffix());
		resolver.setTemplateMode(this.properties.getMode());
		if (this.properties.getEncoding() != null) {
			resolver.setCharacterEncoding(this.properties.getEncoding().name());
		}

		resolver.setCacheable(this.properties.isCache());
		Integer order = this.properties.getTemplateResolverOrder();
		if (order != null) {
			resolver.setOrder(order + 1);
		}

		resolver.setCheckExistence(this.properties.isCheckTemplate());
		return resolver;
	}
}
