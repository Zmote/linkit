package ch.zmotions.linkit.service.domain;

import ch.zmotions.linkit.config.EOFactory;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class PortalEOTest {

    @Test
    public void getId() {
        PortalEO portal = EOFactory.createDummyPortal();
        assertEquals(EOFactory.knownPortalId, portal.getId());
    }

    @Test
    public void setId() {
        PortalEO portal = EOFactory.createDummyPortal();
        assertEquals(EOFactory.knownPortalId, portal.getId());
        UUID newUuid = UUID.randomUUID();
        portal.setId(newUuid);
        assertEquals(newUuid, portal.getId());
    }

    @Test
    public void getName() {
        PortalEO portal = EOFactory.createDummyPortal();
        assertEquals("Portal", portal.getName());
    }

    @Test
    public void setName() {
        PortalEO portal = EOFactory.createDummyPortal();
        assertEquals("Portal", portal.getName());
        portal.setName("Other");
        assertEquals("Other", portal.getName());
    }

    @Test
    public void getCopyright() {
        PortalEO portal = EOFactory.createDummyPortal();
        assertEquals("Copyright", portal.getCopyright());
    }

    @Test
    public void setCopyright() {
        PortalEO portal = EOFactory.createDummyPortal();
        assertEquals("Copyright", portal.getCopyright());
        portal.setCopyright("Other");
        assertEquals("Other", portal.getCopyright());
    }

    @Test
    public void getCustomCss() {
        PortalEO portal = EOFactory.createDummyPortal();
        assertEquals("CustomCss", portal.getCustomCss());
    }

    @Test
    public void setCustomCss() {
        PortalEO portal = EOFactory.createDummyPortal();
        assertEquals("CustomCss", portal.getCustomCss());
        portal.setCustomCss("Other");
        assertEquals("Other", portal.getCustomCss());
    }

    @Test
    public void equals() {
        PortalEO portal = EOFactory.createDummyPortal();
        Object object = new Object();
        assertNotEquals(portal, object);
        assertEquals(portal, portal);
    }

    @Test
    public void equals2() {
        PortalEO portal = EOFactory.createDummyPortal();
        Object object = new Object();
        PortalEO otherPortal = EOFactory.createDummyPortal();
        otherPortal.setId(UUID.randomUUID());
        assertNotEquals(portal, object);
        assertEquals(portal, portal);
        assertNotEquals(portal, otherPortal);
    }

    @Test
    public void getMaxLoginAttempts() {
        PortalEO portal = EOFactory.createDummyPortal();
        assertEquals(3, portal.getMaxLoginAttempts());
    }

    @Test
    public void setMaxLoginAttempts() {
        PortalEO portal = EOFactory.createDummyPortal();
        assertEquals(3, portal.getMaxLoginAttempts());
        portal.setMaxLoginAttempts(10);
        assertEquals(10, portal.getMaxLoginAttempts());
    }
}