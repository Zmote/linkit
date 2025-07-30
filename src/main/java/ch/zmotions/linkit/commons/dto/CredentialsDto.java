package ch.zmotions.linkit.commons.dto;

public class CredentialsDto {
    private String login;
    private String password;
    private String portalLinkName;

    public CredentialsDto(PortalLinkDto portalLinkDto) {
        setLogin(portalLinkDto.getLogin());
        setPassword(portalLinkDto.getPassword());
        setPortalLinkName(portalLinkDto.getName());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPortalLinkName() {
        return portalLinkName;
    }

    public void setPortalLinkName(String portalLinkName) {
        this.portalLinkName = portalLinkName;
    }
}
