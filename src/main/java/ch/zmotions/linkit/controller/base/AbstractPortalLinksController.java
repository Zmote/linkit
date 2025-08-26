package ch.zmotions.linkit.controller.base;

import ch.zmotions.linkit.commons.dto.PortalLinkDto;
import ch.zmotions.linkit.commons.dto.UserDto;
import ch.zmotions.linkit.facade.PortalLinkServiceFacade;
import ch.zmotions.linkit.facade.UserServiceFacade;
import ch.zmotions.linkit.service.StorageService;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AbstractPortalLinksController extends AbstractController {

    protected final PortalLinkServiceFacade portalLinkServiceFacade;
    private final UserServiceFacade userServiceFacade;
    private final StorageService storageService;

    protected AbstractPortalLinksController(PortalLinkServiceFacade portalLinkServiceFacade,
                                            UserServiceFacade userServiceFacade, StorageService storageService) {
        this.portalLinkServiceFacade = portalLinkServiceFacade;
        this.userServiceFacade = userServiceFacade;
        this.storageService = storageService;
    }

    private List<UserDto> calculateAvailableUsers(PortalLinkDto portalLink) {
        List<UserDto> sharedUsers = portalLink.getSharedUsers();
        return userServiceFacade.findAll().stream().filter(user -> !sharedUsers.contains(user))
                .collect(Collectors.toList());
    }

    protected String editForm(Model model, HttpServletResponse response, UUID id, String context) {
        Optional<PortalLinkDto> entity = portalLinkServiceFacade.findById(id);
        if (entity.isPresent()) {
            model.addAttribute("portalLink", entity.get());
            model.addAttribute("availableCategories", portalLinkServiceFacade.getCategories());
            model.addAttribute("thumbnails", storageService.loadAllPaths());
            if (context.equals(PERSONAL_LINKS_IDENTIFIER)) {
                model.addAttribute("assignedUsers", entity.get().getSharedUsers());
                model.addAttribute("availableUsers", calculateAvailableUsers(entity.get()));
            }
            return "configurations/portal-links/shared/edit :: form";
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return null;
    }

    protected String newForm(Model model) {
        model.addAttribute("portalLink", new PortalLinkDto());
        model.addAttribute("availableCategories", portalLinkServiceFacade.getCategories());
        model.addAttribute("thumbnails", storageService.loadAllPaths());
        return "configurations/portal-links/shared/new :: form";
    }
}
