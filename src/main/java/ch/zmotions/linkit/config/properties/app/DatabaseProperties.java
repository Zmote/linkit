package ch.zmotions.linkit.config.properties.app;

import ch.zmotions.linkit.config.properties.app.database.PopulateProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

public class DatabaseProperties {
	private Boolean initialize;
	@NestedConfigurationProperty
	private PopulateProperties populate;

	public DatabaseProperties() {
		setInitialize(false);
		this.populate = new PopulateProperties();
	}

	public Boolean getInitialize() {
		return initialize;
	}

	public void setInitialize(Boolean initialize) {
		this.initialize = initialize;
	}

	public PopulateProperties getPopulate() {
		return populate;
	}

	public void setPopulate(PopulateProperties populate) {
		this.populate = populate;
	}
}
