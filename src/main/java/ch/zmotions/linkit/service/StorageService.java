package ch.zmotions.linkit.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void init();

    String store(MultipartFile file);

    String[] loadAllPaths();

    void deleteAll();

    void delete(String filename);
}