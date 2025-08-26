package ch.zmotions.linkit.service.impl;

import ch.zmotions.linkit.service.util.JpegCompressor;
import ch.zmotions.linkit.commons.exceptions.StorageException;
import ch.zmotions.linkit.config.properties.StorageProperties;
import ch.zmotions.linkit.service.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
@ConditionalOnProperty(name = "storage.type", havingValue = "system")
public class FileSystemStorageServiceImpl extends AbstractStorageServiceImpl implements StorageService {

    private final Path rootLocation;
    private final JpegCompressor jpegCompressor;

    @Autowired
    public FileSystemStorageServiceImpl(StorageProperties properties, JpegCompressor jpegCompressor) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.jpegCompressor = jpegCompressor;
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }


    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public String store(MultipartFile file) {
        String filename = extractFilenameForUpload(file);
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = jpegCompressor.compress(file, 0.5f)) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }

        return filename;
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public String[] loadAllPaths() {
        return loadAll().map((path) -> "/" +
                StringUtils.cleanPath(resolveLocation(FilenameUtils.separatorsToUnix(path.toString()))))
                .toArray(String[]::new);
    }

    @PreAuthorize("isAuthenticated() &&  hasRole('ROLE_ADMIN')")
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }


    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public void delete(String filename) {
        try {
            filename = prefixWithCurrentPathDot(URLDecoder.decode(filename, "UTF-8"));
            if (isDeletable(filename)) {
                File file = Paths.get(filename).toFile();
                FileSystemUtils.deleteRecursively(file);
            } else {
                throw new StorageException("Failed to delete file");
            }
        } catch (UnsupportedEncodingException e) {
            throw new StorageException(e.getMessage());
        }
    }

    private String resolveLocation(String fileName) {
        return rootLocation.resolve(fileName).toString();
    }

    private Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    private String getRootLocation() {
        return this.rootLocation.toString();
    }

    private String prefixWithCurrentPathDot(String fileName) {
        return fileName.charAt(0) == '.' ? fileName : "." + fileName;
    }

    private Boolean isDeletable(String fileName) {
        File deleteFile = Paths.get(fileName).toFile();
        File parent = deleteFile.getParentFile();
        return deleteFile.exists()
                && deleteFile.isFile()
                && parent != null
                && parent.isDirectory()
                && parent.getPath().equals(getRootLocation());
    }

}