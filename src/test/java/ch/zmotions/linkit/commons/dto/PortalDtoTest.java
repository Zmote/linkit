package ch.zmotions.linkit.commons.dto;

import ch.zmotions.linkit.config.DtoFactory;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PortalDtoTest {

    @Test
    public void getId() {
        PortalDto portalDto = DtoFactory.createDummyPortalDto();
        assertNotNull(portalDto.getId());
        assertEquals(DtoFactory.knownPortalId, portalDto.getId());
    }

    @Test
    public void setId() {
        PortalDto portalDto = DtoFactory.createDummyPortalDto();
        assertNotNull(portalDto.getId());
        assertEquals(DtoFactory.knownPortalId, portalDto.getId());
        UUID newId = UUID.randomUUID();
        portalDto.setId(newId);
        assertEquals(newId, portalDto.getId());
    }

    @Test
    public void getName() {
        PortalDto portalDto = DtoFactory.createDummyPortalDto();
        assertEquals("Portal", portalDto.getName());
    }

    @Test
    public void setName() {
        PortalDto portalDto = DtoFactory.createDummyPortalDto();
        assertEquals("Portal", portalDto.getName());
        portalDto.setName("Other");
        assertEquals("Other", portalDto.getName());
    }

    @Test
    public void getCopyright() {
        PortalDto portalDto = DtoFactory.createDummyPortalDto();
        assertEquals("Copyright", portalDto.getCopyright());
    }

    @Test
    public void setCopyright() {
        PortalDto portalDto = DtoFactory.createDummyPortalDto();
        assertEquals("Copyright", portalDto.getCopyright());
        portalDto.setCopyright("Other");
        assertEquals("Other", portalDto.getCopyright());
    }

    @Test
    public void getCustomCss() {
        PortalDto portalDto = DtoFactory.createDummyPortalDto();
        assertEquals("CustomCss", portalDto.getCustomCss());
    }

    @Test
    public void setCustomCss() {
        PortalDto portalDto = DtoFactory.createDummyPortalDto();
        assertEquals("CustomCss", portalDto.getCustomCss());
        portalDto.setCustomCss("OtherCss");
        assertEquals("OtherCss", portalDto.getCustomCss());
    }

    @Test
    public void equals() {
        PortalDto portalDto = DtoFactory.createDummyPortalDto();
        Object object = new Object();
        assertNotEquals(portalDto, object);
        assertEquals(portalDto, portalDto);
    }

    @Test
    public void equals2() {
        PortalDto portalDto = DtoFactory.createDummyPortalDto();
        PortalDto portalDto2 = DtoFactory.createDummyPortalDto();
        portalDto.setId(UUID.randomUUID());
        assertEquals(portalDto, portalDto);
        assertNotEquals(portalDto, portalDto2);
    }

    @Test
    public void getBlockedList() {
        PortalDto portalDto = DtoFactory.createDummyPortalDto();
        assertEquals("0.0.0.0", portalDto.getBlockedList());
    }

    @Test
    public void setBlockedList() {
        PortalDto portalDto = DtoFactory.createDummyPortalDto();
        assertEquals("0.0.0.0", portalDto.getBlockedList());
        portalDto.setBlockedList("1.1.1.1");
        assertEquals("1.1.1.1", portalDto.getBlockedList());
    }

    @Test
    public void getMaxLoginAttempts() {
        PortalDto portalDto = DtoFactory.createDummyPortalDto();
        assertEquals(3, portalDto.getMaxLoginAttempts());
    }

    @Test
    public void setMaxLoginAttempts() {
        PortalDto portalDto = DtoFactory.createDummyPortalDto();
        assertEquals(3, portalDto.getMaxLoginAttempts());
        portalDto.setMaxLoginAttempts(10);
        assertEquals(10, portalDto.getMaxLoginAttempts());
    }
}