package ch.zmotions.linkit.commons.dto;

import ch.zmotions.linkit.config.DtoFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileResponseDtoTest {

    @Test
    public void getName() {
        FileResponseDto fileResponseDto = DtoFactory.createDummyFileResponseDto();
        assertEquals("testfile.jpg", fileResponseDto.getName());
    }

    @Test
    public void setName() {
        FileResponseDto fileResponseDto = DtoFactory.createDummyFileResponseDto();
        assertEquals("testfile.jpg", fileResponseDto.getName());
        fileResponseDto.setName("otherfile.jpg");
        assertEquals("otherfile.jpg", fileResponseDto.getName());
    }

    @Test
    public void getUri() {
        FileResponseDto fileResponseDto = DtoFactory.createDummyFileResponseDto();
        assertEquals("./uploads/testfile.jpg", fileResponseDto.getUri());
    }

    @Test
    public void setUri() {
        FileResponseDto fileResponseDto = DtoFactory.createDummyFileResponseDto();
        assertEquals("./uploads/testfile.jpg", fileResponseDto.getUri());
        fileResponseDto.setUri("https://someurl.ch/uploads/testfile.jpg");
        assertEquals("https://someurl.ch/uploads/testfile.jpg", fileResponseDto.getUri());
    }

    @Test
    public void getType() {
        FileResponseDto fileResponseDto = DtoFactory.createDummyFileResponseDto();
        assertEquals("TEST", fileResponseDto.getType());
    }

    @Test
    public void setType() {
        FileResponseDto fileResponseDto = DtoFactory.createDummyFileResponseDto();
        assertEquals("TEST", fileResponseDto.getType());
        fileResponseDto.setType("OTHER");
        assertEquals("OTHER", fileResponseDto.getType());
    }

    @Test
    public void getSize() {
        FileResponseDto fileResponseDto = DtoFactory.createDummyFileResponseDto();
        assertEquals(10, fileResponseDto.getSize());
    }

    @Test
    public void setSize() {
        FileResponseDto fileResponseDto = DtoFactory.createDummyFileResponseDto();
        assertEquals(10, fileResponseDto.getSize());
        fileResponseDto.setSize(20);
        assertEquals(20, fileResponseDto.getSize());
    }
}