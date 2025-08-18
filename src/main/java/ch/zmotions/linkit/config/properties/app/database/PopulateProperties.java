package ch.zmotions.linkit.config.properties.app.database;

import ch.zmotions.linkit.config.properties.app.database.links.LinkProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

public class PopulateProperties {
	private Boolean enabled;
	@NestedConfigurationProperty
	private LinkProperties links;

	public PopulateProperties() {
		this.links = new LinkProperties();
		setEnabled(false);
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public LinkProperties getLinks() {
		return links;
	}

	public void setLinks(LinkProperties links) {
		this.links = links;
	}
}
