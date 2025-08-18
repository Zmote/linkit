package ch.zmotions.linkit.config.properties.app.database.links;

public class LinkProperties {
	private Integer count;

	public LinkProperties() {
		setCount(10);
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
