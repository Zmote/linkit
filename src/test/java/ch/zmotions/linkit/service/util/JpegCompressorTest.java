package ch.zmotions.linkit.service.util;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

public class JpegCompressorTest {

    @Test
    public void compress() throws IOException {
        String data = "someimage";
        MockMultipartFile multipartFile =
                new MockMultipartFile("data", "file.jpeg", "image/jpeg", "data".getBytes());
        JpegCompressor jpegCompressor = new JpegCompressor();
        InputStream convertedInputStream = jpegCompressor.compress(multipartFile, 0.5f);
        byte[] convertedData = new byte[convertedInputStream.available()];
        int readBytes = convertedInputStream.read(convertedData);
        assertNotEquals(data.getBytes().length, readBytes);
    }
}