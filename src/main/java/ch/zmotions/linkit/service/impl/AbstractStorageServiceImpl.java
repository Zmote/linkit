package ch.zmotions.linkit.service.impl;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class AbstractStorageServiceImpl {
    String extractFilenameForUpload(MultipartFile file) {
        String fileExtension = "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
        return "LIP" + System.currentTimeMillis() + fileExtension;
    }
}
