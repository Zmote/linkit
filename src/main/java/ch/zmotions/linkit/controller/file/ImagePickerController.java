package ch.zmotions.linkit.controller.file;

import ch.zmotions.linkit.service.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagepicker")
public class ImagePickerController {

    private final StorageService storageService;

    public ImagePickerController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public String imagePickerBody(Model model) {
        model.addAttribute("thumbnails", storageService.loadAllPaths());
        return "configurations/portal-links/shared/image-picker :: content";
    }
}
