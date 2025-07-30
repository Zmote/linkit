package ch.zmotions.linkit.service.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Objects;

@Component
public class JpegCompressor {
    public InputStream compress(MultipartFile file, float quality) throws IOException {
        String fileExtension = Objects.requireNonNull(StringUtils.getFilenameExtension(file.getOriginalFilename()));
        if (fileExtension.equals("jpg")) {
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(fileExtension);
            if (writers.hasNext()) {
                try {
                    ImageWriter writer = writers.next();

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageOutputStream ios = ImageIO.createImageOutputStream(byteArrayOutputStream);
                    writer.setOutput(ios);

                    ImageWriteParam imageWriteParam = writer.getDefaultWriteParam();
                    imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    imageWriteParam.setCompressionQuality(quality);

                    BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

                    writer.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);
                    byteArrayOutputStream.flush();
                    return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return file.getInputStream();
    }
}
