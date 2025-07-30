package ch.zmotions.linkit.commons.dto;

import ch.zmotions.linkit.commons.types.PortalLinkType;
import ch.zmotions.linkit.config.DtoFactory;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class PortalLinkDtoTest {

    @Test
    public void getId() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals(DtoFactory.knownPortalLinkId, portalLinkDto.getId());
    }

    @Test
    public void setId() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals(DtoFactory.knownPortalLinkId, portalLinkDto.getId());
        UUID newUuid = UUID.randomUUID();
        portalLinkDto.setId(newUuid);
        assertEquals(newUuid, portalLinkDto.getId());
    }

    @Test
    public void getName() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals("Some Link", portalLinkDto.getName());
    }

    @Test
    public void setName() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals("Some Link", portalLinkDto.getName());
        portalLinkDto.setName("Other Link");
        assertEquals("Other Link", portalLinkDto.getName());
    }

    @Test
    public void getDescription() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals("Some Description", portalLinkDto.getDescription());
    }

    @Test
    public void setDescription() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals("Some Description", portalLinkDto.getDescription());
        portalLinkDto.setDescription("Other Descrioption");
        assertEquals("Other Descrioption", portalLinkDto.getDescription());
    }

    @Test
    public void getHref() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals("www.20min.ch", portalLinkDto.getHref());
    }

    @Test
    public void setHref() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals("www.20min.ch", portalLinkDto.getHref());
        portalLinkDto.setHref("www.blick.ch");
        assertEquals("www.blick.ch", portalLinkDto.getHref());
    }

    @Test
    public void getMedia() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals("./uploads/file.jpg", portalLinkDto.getMedia());
    }

    @Test
    public void setMedia() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals("./uploads/file.jpg", portalLinkDto.getMedia());
        portalLinkDto.setMedia("./uploads/file2.jpg");
        assertEquals("./uploads/file2.jpg", portalLinkDto.getMedia());
    }

    @Test
    public void getType() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals(PortalLinkType.WEB, portalLinkDto.getType());
    }

    @Test
    public void setType() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals(PortalLinkType.WEB, portalLinkDto.getType());
        portalLinkDto.setType(PortalLinkType.RDP);
        assertEquals(PortalLinkType.RDP, portalLinkDto.getType());
    }

    @Test
    public void getLogin() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals("admin", portalLinkDto.getLogin());
    }

    @Test
    public void setLogin() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals("admin", portalLinkDto.getLogin());
        portalLinkDto.setLogin("admin1");
        assertEquals("admin1", portalLinkDto.getLogin());
    }

    @Test
    public void getPassword() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals("root@SOMEPASS2019", portalLinkDto.getPassword());
    }

    @Test
    public void setPassword() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals("root@SOMEPASS2019", portalLinkDto.getPassword());
        portalLinkDto.setPassword("root@SOMEOTHERPASS2019");
        assertEquals("root@SOMEOTHERPASS2019", portalLinkDto.getPassword());
    }

    @Test
    public void getOwner() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertNull(portalLinkDto.getOwner());
    }

    @Test
    public void setOwner() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertNull(portalLinkDto.getOwner());
        UserDto owner = DtoFactory.createDummyUserDto();
        portalLinkDto.setOwner(owner);
        assertEquals(owner, portalLinkDto.getOwner());
    }

    @Test
    public void equals1() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        Object object = new Object();
        assertNotEquals(portalLinkDto, object);
        assertEquals(portalLinkDto, portalLinkDto);
    }

    @Test
    public void equals2() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals(portalLinkDto, portalLinkDto);
        PortalLinkDto otherPortalLinkDto = DtoFactory.createDummyPortalLinkDto();
        otherPortalLinkDto.setId(UUID.randomUUID());
        assertNotEquals(portalLinkDto, otherPortalLinkDto);
    }

    @Test
    public void getSharedUsers() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertNotNull(portalLinkDto.getSharedUsers());
        assertEquals(2, portalLinkDto.getSharedUsers().size());
    }

    @Test
    public void setSharedUsers() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertNotNull(portalLinkDto.getSharedUsers());
        assertEquals(2, portalLinkDto.getSharedUsers().size());
        portalLinkDto.getSharedUsers().add(DtoFactory.createRandomDummyUserDto());
        portalLinkDto.getSharedUsers().add(DtoFactory.createRandomDummyUserDto());
        assertEquals(4, portalLinkDto.getSharedUsers().size());
    }

    @Test
    public void getCategory() {
        PortalLinkDto portalLinkDto = DtoFactory.createDummyPortalLinkDto();
        assertEquals("Medien", portalLinkDto.getCategory());
    }
}