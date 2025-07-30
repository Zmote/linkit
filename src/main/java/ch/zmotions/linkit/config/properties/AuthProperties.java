package ch.zmotions.linkit.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private String key;
    private String salt;
    private String initialPass;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getInitialPass() {
        return initialPass;
    }

    public void setInitialPass(String initialPass) {
        this.initialPass = initialPass;
    }
}