package ch.zmotions.linkit.config.auth;

public class LoginUser {
    private String username;
    private String fullname;

    public LoginUser(String username, String fullname) {
        this.username = username;
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }
}
