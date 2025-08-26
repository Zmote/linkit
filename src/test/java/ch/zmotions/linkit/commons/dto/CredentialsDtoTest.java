package ch.zmotions.linkit.commons.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CredentialsDtoTest {

    @Test
    public void getLogin() {
        PortalLinkDto portalLinkDto = new PortalLinkDto();
        portalLinkDto.setLogin("Zaferium");
        CredentialsDto credentialsDto = new CredentialsDto(portalLinkDto);
        assertEquals("Zaferium", credentialsDto.getLogin());
    }

    @Test
    public void setLogin() {
        PortalLinkDto portalLinkDto = new PortalLinkDto();
        portalLinkDto.setLogin("Zaferium");
        CredentialsDto credentialsDto = new CredentialsDto(portalLinkDto);
        credentialsDto.setLogin("Admin");
        assertNotEquals("Admin", portalLinkDto.getLogin());
        assertEquals("Zaferium", portalLinkDto.getLogin());
        assertEquals("Admin", credentialsDto.getLogin());
    }

    @Test
    public void getPassword() {
        PortalLinkDto portalLinkDto = new PortalLinkDto();
        portalLinkDto.setPassword("Zaferium");
        CredentialsDto credentialsDto = new CredentialsDto(portalLinkDto);
        assertEquals("Zaferium", credentialsDto.getPassword());
    }

    @Test
    public void setPassword() {
        PortalLinkDto portalLinkDto = new PortalLinkDto();
        portalLinkDto.setPassword("Zaferium");
        CredentialsDto credentialsDto = new CredentialsDto(portalLinkDto);
        credentialsDto.setPassword("Admin");
        assertNotEquals("Admin", portalLinkDto.getPassword());
        assertEquals("Zaferium", portalLinkDto.getPassword());
        assertEquals("Admin", credentialsDto.getPassword());
    }

    @Test
    public void getPortalLinkName() {
        PortalLinkDto portalLinkDto = new PortalLinkDto();
        portalLinkDto.setName("Portal");
        CredentialsDto credentialsDto = new CredentialsDto(portalLinkDto);
        assertEquals("Portal", credentialsDto.getPortalLinkName());
    }

    @Test
    public void setPortalLinkName() {
        PortalLinkDto portalLinkDto = new PortalLinkDto();
        portalLinkDto.setName("Portal");
        CredentialsDto credentialsDto = new CredentialsDto(portalLinkDto);
        credentialsDto.setPortalLinkName("Other Portal");
        assertNotEquals("Other Portal", portalLinkDto.getName());
        assertEquals("Portal", portalLinkDto.getName());
        assertEquals("Other Portal", credentialsDto.getPortalLinkName());
    }
}