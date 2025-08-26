package ch.zmotions.linkit.controller.portallinks;

import ch.zmotions.linkit.commons.dto.PortalLinkDto;
import ch.zmotions.linkit.controller.base.AbstractPortalLinksController;
import ch.zmotions.linkit.facade.PortalLinkServiceFacade;
import ch.zmotions.linkit.facade.UserServiceFacade;
import ch.zmotions.linkit.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/global-links")
public class GlobalLinkController extends AbstractPortalLinksController {

    protected GlobalLinkController(PortalLinkServiceFacade portalLinkServiceFacade,
                                   UserServiceFacade userServiceFacade,
                                   StorageService storageService) {
        super(portalLinkServiceFacade, userServiceFacade, storageService);
    }

    @GetMapping("{id}/edit")
    public String editForm(Model model, HttpServletResponse response, @PathVariable("id") UUID id) {
        return super.editForm(model, response, id, GLOBAL_LINKS_IDENTIFIER);
    }

    @GetMapping("new")
    public String newForm(Model model) {
        return super.newForm(model);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePortalLink(@PathVariable("id") UUID id) {
        portalLinkServiceFacade.removeById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updatePortalLink(@PathVariable("id") UUID id, @RequestBody PortalLinkDto portalLink) {
        portalLink.setId(id);
        Optional<PortalLinkDto> updatedPortalLink = portalLinkServiceFacade.update(portalLink);
        if (updatedPortalLink.isPresent()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @PostMapping
    public ResponseEntity<String> newPortalLink(@RequestBody PortalLinkDto portalLink) {
        Optional<PortalLinkDto> newPortalLink = portalLinkServiceFacade.create(portalLink);
        if (newPortalLink.isPresent()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.unprocessableEntity().build();
    }
}
