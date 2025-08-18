package ch.zmotions.linkit;

import ch.zmotions.linkit.config.properties.AppProperties;
import ch.zmotions.linkit.config.properties.AuthProperties;
import ch.zmotions.linkit.config.properties.StorageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		AppProperties.class,
		StorageProperties.class,
		AuthProperties.class
})
public class PortalApplication {

	public static void main(String[] args) {
		Handler globalExceptionHandler = new Handler();
		Thread.setDefaultUncaughtExceptionHandler(globalExceptionHandler);
		SpringApplication.run(PortalApplication.class, args);
	}
}


class Handler implements Thread.UncaughtExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

	public void uncaughtException(Thread t, Throwable e) {
		LOGGER.warn("Unhandled exception caught!: " + e.getMessage());
	}
}
