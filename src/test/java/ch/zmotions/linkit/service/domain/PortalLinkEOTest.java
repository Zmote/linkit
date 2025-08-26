package ch.zmotions.linkit.service.domain;

import ch.zmotions.linkit.commons.types.PortalLinkType;
import ch.zmotions.linkit.config.EOFactory;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PortalLinkEOTest {

    @Test
    public void getId() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertEquals(EOFactory.knownPortalLinkId, portalLink.getId());
    }

    @Test
    public void setId() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertEquals(EOFactory.knownPortalLinkId, portalLink.getId());
        UUID newUuid = UUID.randomUUID();
        portalLink.setId(newUuid);
        assertEquals(newUuid, portalLink.getId());
    }

    @Test
    public void getName() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertEquals("Name", portalLink.getName());
    }

    @Test
    public void setName() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertEquals("Name", portalLink.getName());
        portalLink.setName("Otherlink");
        assertEquals("Otherlink", portalLink.getName());
    }

    @Test
    public void getDescription() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertEquals("Description", portalLink.getDescription());
    }

    @Test
    public void setDescription() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertEquals("Description", portalLink.getDescription());
        portalLink.setDescription("Other");
        assertEquals("Other", portalLink.getDescription());
    }

    @Test
    public void getHref() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertEquals("Href", portalLink.getHref());
    }

    @Test
    public void setHref() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertEquals("Href", portalLink.getHref());
        portalLink.setHref("Other");
        assertEquals("Other", portalLink.getHref());
    }

    @Test
    public void getMedia() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertEquals("Media", portalLink.getMedia());
    }

    @Test
    public void setMedia() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertEquals("Media", portalLink.getMedia());
        portalLink.setMedia("Other");
        assertEquals("Other", portalLink.getMedia());
    }

    @Test
    public void getType() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertEquals(PortalLinkType.WEB, portalLink.getType());
    }

    @Test
    public void setType() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertEquals(PortalLinkType.WEB, portalLink.getType());
        portalLink.setType(PortalLinkType.RDP);
        assertEquals(PortalLinkType.RDP, portalLink.getType());
    }

    @Test
    public void getOwner() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertNull(portalLink.getOwner());
    }

    @Test
    public void setOwner() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertNull(portalLink.getOwner());
        UserEO owner = EOFactory.createDummyUser();
        portalLink.setOwner(owner);
        assertEquals(owner, portalLink.getOwner());
    }

    @Test
    public void isPublic() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertTrue(portalLink.isPublic());
    }

    @Test
    public void setPublic() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertTrue(portalLink.isPublic());
        portalLink.setPublic(false);
        assertFalse(portalLink.isPublic());
    }

    @Test
    public void testPortalLinkIsPrivateWhenOwnerIsSet() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertTrue(portalLink.isPublic());
        UserEO owner = EOFactory.createDummyUser();
        portalLink.setOwner(owner);
        assertFalse(portalLink.isPublic());
    }

    @Test
    public void testPortalLinkIsPublicWhenOwnerIsSetNull() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertTrue(portalLink.isPublic());
        portalLink.setOwner(null);
        assertTrue(portalLink.isPublic());
    }

    @Test
    public void getSharedUsers() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertTrue(portalLink.getSharedUsers().isEmpty());
    }

    @Test
    public void setSharedUsers() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertTrue(portalLink.getSharedUsers().isEmpty());
        portalLink.getSharedUsers().add(EOFactory.createDummyRandomUser());
        portalLink.getSharedUsers().add(EOFactory.createDummyRandomUser());
        assertEquals(2, portalLink.getSharedUsers().size());
    }

    @Test
    public void addSharedUser() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertTrue(portalLink.getSharedUsers().isEmpty());
        portalLink.addSharedUser(EOFactory.createDummyRandomUser());
        assertEquals(1, portalLink.getSharedUsers().size());
    }

    @Test
    public void removeSharedUser() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertTrue(portalLink.getSharedUsers().isEmpty());
        UserEO sharedUser = EOFactory.createDummyRandomUser();
        portalLink.addSharedUser(sharedUser);
        assertEquals(1, portalLink.getSharedUsers().size());
        portalLink.removeSharedUser(sharedUser);
        assertEquals(0, portalLink.getSharedUsers().size());
    }

    @Test
    public void clearSharedUsers() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertTrue(portalLink.getSharedUsers().isEmpty());
        portalLink.getSharedUsers().add(EOFactory.createDummyRandomUser());
        portalLink.getSharedUsers().add(EOFactory.createDummyRandomUser());
        assertEquals(2, portalLink.getSharedUsers().size());
        portalLink.clearSharedUsers();
        assertEquals(0, portalLink.getSharedUsers().size());
    }

    @Test
    public void equals() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        Object object = new Object();
        assertNotEquals(portalLink, object);
        assertEquals(portalLink, portalLink);
    }

    @Test
    public void equals2() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        Object object = new Object();
        PortalLinkEO otherPortalLink = EOFactory.createDummyRandomPortalLink();
        assertNotEquals(portalLink, object);
        assertEquals(portalLink, portalLink);
        assertNotEquals(portalLink, otherPortalLink);
    }

    @Test
    public void getLogin() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertEquals("admin", portalLink.getLogin());
    }

    @Test
    public void setLogin() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertEquals("admin", portalLink.getLogin());
        portalLink.setLogin("root");
        assertEquals("root", portalLink.getLogin());
    }

    @Test
    public void getPassword() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertEquals("testpassword", portalLink.getPassword());
    }

    @Test
    public void setPassword() {
        PortalLinkEO portalLink = EOFactory.createDummyPortalLink();
        assertEquals("testpassword", portalLink.getPassword());
        portalLink.setPassword("other");
        assertEquals("other", portalLink.getPassword());
    }
}