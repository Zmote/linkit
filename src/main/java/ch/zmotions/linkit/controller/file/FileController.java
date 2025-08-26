package ch.zmotions.linkit.controller.file;

import ch.zmotions.linkit.commons.dto.FileResponseDto;
import ch.zmotions.linkit.commons.exceptions.StorageException;
import ch.zmotions.linkit.service.domain.PortalLinkEO;
import ch.zmotions.linkit.service.PortalLinkService;
import ch.zmotions.linkit.service.StorageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FileController {

    private final StorageService storageService;
    private final PortalLinkService portalLinkService;

    public FileController(StorageService storageService, PortalLinkService portalLinkService) {
        this.storageService = storageService;
        this.portalLinkService = portalLinkService;
    }

    @GetMapping("/download/{id}.rdp")
    public ResponseEntity<Resource> downloadRdpProfile(@PathVariable("id") UUID portalLinkId) {
        Optional<PortalLinkEO> portalLink = portalLinkService.findById(portalLinkId);
        if (portalLink.isPresent()) {
            String profileContent = "full address:s:" + portalLink.get().getHref();
            byte[] profileBytes = profileContent.getBytes();
            Resource resource = new ByteArrayResource(profileBytes);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + portalLink.get().getName() + ".rdp\"")
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(value = "/upload-file")
    public FileResponseDto uploadFile(@RequestParam("file") MultipartFile file) {
        String name = storageService.store(file);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(name)
                .toUriString();

        return new FileResponseDto(name, uri, file.getContentType(), file.getSize());
    }

    @PostMapping("/upload-multiple-files")
    public List<FileResponseDto> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete-file")
    public ResponseEntity<String> deleteFile(@RequestParam("file") String fileName) {
        try {
            storageService.delete(fileName);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Gelöscht: " + fileName);
        } catch (StorageException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Datei nicht gefunden");
        }
    }
}