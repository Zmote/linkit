package ch.zmotions.linkit.service.impl;

import ch.zmotions.linkit.commons.exceptions.StorageException;
import ch.zmotions.linkit.config.properties.StorageProperties;
import ch.zmotions.linkit.service.StorageService;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
@ConditionalOnProperty(name = "storage.type", havingValue = "aws")
public class AWSStorageServiceImpl extends AbstractStorageServiceImpl implements StorageService {

    private AmazonS3 s3Client;
    private String BUCKET_NAME = "linkit-bilder-bucket";
    private final StorageProperties storageProperties;

    public AWSStorageServiceImpl(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    @PostConstruct
    public void init() {
        AWSCredentials credentials = new BasicAWSCredentials(
                storageProperties.getKey(),
                storageProperties.getSecret()
        );
        s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public String store(MultipartFile file) {
        String filename = extractFilenameForUpload(file);
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            String IMAGES_FOLDER = "images";
            filename = IMAGES_FOLDER + "/" + storageProperties.getLocation().replaceAll("[^a-zA-Z0-9-]", "")
                    + "/" + filename;
            s3Client.putObject(BUCKET_NAME, filename, file.getInputStream(), null);
        } catch (IOException | AmazonServiceException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
        return filename;
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public String[] loadAllPaths() {
        return loadAll().map((path) -> resolveLocation(FilenameUtils.separatorsToUnix(path.toString())))
                .toArray(String[]::new);
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public void deleteAll() {
        ObjectListing objectListing = s3Client.listObjects(BUCKET_NAME);
        objectListing.getObjectSummaries().forEach(summary -> s3Client.deleteObject(BUCKET_NAME, summary.getKey()));
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public void delete(String filename) {
        try {
            filename = URLDecoder.decode(filename, "UTF-8");
            s3Client.deleteObject(BUCKET_NAME, URI.create(filename).getPath().substring(1));
        } catch (UnsupportedEncodingException e) {
            throw new StorageException("Failed to delete File");
        }
    }

    private String resolveLocation(String fileName) {
        try {
            return URLDecoder.decode(s3Client.getUrl(BUCKET_NAME, fileName).toExternalForm(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Failed decoding URL", e);
        }
    }

    private Stream<Path> loadAll() {
        ObjectListing objectListing = s3Client.listObjects(BUCKET_NAME);
        return objectListing.getObjectSummaries().stream().map(summary -> Paths.get(summary.getKey()));
    }

}
