package ch.zmotions.linkit.config.properties;

import ch.zmotions.linkit.config.properties.app.DatabaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
	@NestedConfigurationProperty
	private DatabaseProperties db;

	public AppProperties() {
		this.db = new DatabaseProperties();
	}

	public DatabaseProperties getDb() {
		return db;
	}

	public void setDb(DatabaseProperties db) {
		this.db = db;
	}
}
