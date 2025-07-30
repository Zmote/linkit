package ch.zmotions.linkit.controller.portallinks;

import ch.zmotions.linkit.commons.dto.PortalLinkDto;
import ch.zmotions.linkit.commons.dto.UserDto;
import ch.zmotions.linkit.controller.base.AbstractPortalLinksController;
import ch.zmotions.linkit.facade.AuthHelperFacade;
import ch.zmotions.linkit.facade.PortalLinkServiceFacade;
import ch.zmotions.linkit.facade.UserServiceFacade;
import ch.zmotions.linkit.service.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/personal-links")
public class PersonalLinkController extends AbstractPortalLinksController {

    private final AuthHelperFacade authHelperFacade;

    protected PersonalLinkController(PortalLinkServiceFacade portalLinkServiceFacade,
                                     UserServiceFacade userServiceFacade,
                                     StorageService storageService,
                                     AuthHelperFacade authHelperFacade) {
        super(portalLinkServiceFacade, userServiceFacade, storageService);
        this.authHelperFacade = authHelperFacade;
    }

    @GetMapping("{id}/edit")
    public String editForm(Model model, HttpServletResponse response, @PathVariable("id") UUID id) {
        return super.editForm(model, response, id, PERSONAL_LINKS_IDENTIFIER);
    }

    @GetMapping("new")
    public String newForm(Model model) {
        return super.newForm(model);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletePortalLink(@PathVariable("id") UUID id) {
        Optional<UserDto> loginUserOptional = authHelperFacade.getLoginUser();
        if (loginUserOptional.isPresent()) {
            UserDto userDto = loginUserOptional.get();
            Optional<PortalLinkDto> portalLinkOptional = portalLinkServiceFacade.findById(id);
            if (portalLinkOptional.isPresent()) {
                PortalLinkDto portalLink = portalLinkOptional.get();
                if (portalLink.getOwner() != null && portalLink.getOwner().equals(userDto)) {
                    portalLinkServiceFacade.removeById(id);
                    return ResponseEntity.ok().build();
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("{id}")
    public ResponseEntity updatePortalLink(@PathVariable("id") UUID id, @RequestBody PortalLinkDto portalLink) {
        Optional<UserDto> optionalLoginUser = authHelperFacade.getLoginUser();
        if (optionalLoginUser.isPresent()) {
            Optional<PortalLinkDto> portalLinkOptional = portalLinkServiceFacade.findById(id);
            if (portalLinkOptional.isPresent()) {
                PortalLinkDto foundPortalLink = portalLinkOptional.get();
                if (foundPortalLink.getOwner() != null && foundPortalLink.getOwner().equals(optionalLoginUser.get())) {
                    portalLink.setId(id);
                    portalLink.setOwner(optionalLoginUser.get());
                    Optional<PortalLinkDto> updatedPortalLink = portalLinkServiceFacade.update(portalLink);
                    if (updatedPortalLink.isPresent()) {
                        return ResponseEntity.ok().build();
                    }
                    return ResponseEntity.unprocessableEntity().build();
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping
    public ResponseEntity newPortalLink(@RequestBody PortalLinkDto portalLink) {
        Optional<UserDto> optionalLoginUser = authHelperFacade.getLoginUser();
        if (optionalLoginUser.isPresent()) {
            portalLink.setOwner(optionalLoginUser.get());
            Optional<PortalLinkDto> newPortalLink = portalLinkServiceFacade.create(portalLink);
            if (newPortalLink.isPresent()) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
