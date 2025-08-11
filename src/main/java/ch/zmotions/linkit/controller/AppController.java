package ch.zmotions.linkit.controller;

import ch.zmotions.linkit.commons.dto.CredentialsDto;
import ch.zmotions.linkit.commons.dto.PortalLinkDto;
import ch.zmotions.linkit.controller.base.AbstractPortalController;
import ch.zmotions.linkit.facade.AuthHelperFacade;
import ch.zmotions.linkit.facade.PortalLinkServiceFacade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class AppController extends AbstractPortalController {

    private final PortalLinkServiceFacade portalLinkServiceFacade;
    private final AuthHelperFacade authHelperFacade;

    public AppController(PortalLinkServiceFacade portalLinkServiceFacade, AuthHelperFacade authHelperFacade) {
        this.portalLinkServiceFacade = portalLinkServiceFacade;
        this.authHelperFacade = authHelperFacade;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request, @RequestParam(required = false) String displayMode) {
        initLinks(model, portalLinkServiceFacade.getGlobalLinks());
        return indexTemplate(model, request, displayMode);
    }

    @GetMapping("/publiclinks")
    public String publicLinks(Model model, HttpServletRequest request, @RequestParam(required = false) String displayMode) {
        return index(model, request, displayMode);
    }

    @GetMapping("/mylinks")
    public String myLinks(Model model, HttpServletRequest request, @RequestParam(required = false) String displayMode) {
        initLinks(model, portalLinkServiceFacade.getPersonalLinks());
        return indexTemplate(model, request, displayMode);
    }

    @GetMapping("/sharedwithme")
    public String sharedWithMe(Model model, HttpServletRequest request, @RequestParam(required = false) String displayMode) {
        initLinks(model, portalLinkServiceFacade.getSharedLinks());
        return indexTemplate(model, request, displayMode);
    }

    @GetMapping("/credentials")
    public String credentials(Model model, HttpServletRequest request) {
        model.addAttribute("globalLinks", portalLinkServiceFacade.getGlobalLinks().stream()
                .filter(this::hasLoginPasswordDefined).collect(Collectors.toList()));
        model.addAttribute("loginUserLinks", portalLinkServiceFacade.getPersonalLinks().stream()
                .filter(this::hasLoginPasswordDefined).collect(Collectors.toList()));
        return isAjax(request) ? "credentials :: body" : "credentials";
    }

    @GetMapping("credentials/{id}")
    public ResponseEntity showPassword(@PathVariable("id") UUID portalLinkId) {
        Optional<PortalLinkDto> dto = portalLinkServiceFacade.findById(portalLinkId);
        return dto.<ResponseEntity>map(portalLinkDto -> ResponseEntity.ok(authHelperFacade.decrypt(portalLinkDto.getPassword())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("credentials/extended/{id}")
    public String showPasswordAndLogin(Model model, HttpServletResponse response, @PathVariable("id") UUID portalLinkId) {
        Optional<PortalLinkDto> dto = portalLinkServiceFacade.findById(portalLinkId);
        if (dto.isPresent()) {
            dto.get().setPassword(authHelperFacade.decrypt(dto.get().getPassword()));
            model.addAttribute("credential", new CredentialsDto(dto.get()));
            return "login_credentials :: body";
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return null;
    }

    @GetMapping("/warn")
    public String warn() {
        return "warn :: body";
    }

    @GetMapping("/404")
    public String notFound(HttpServletRequest request) {
        return isAjax(request) ? "error :: body" : "error";
    }

    private Boolean hasLoginPasswordDefined(PortalLinkDto portalLink) {
        return (portalLink.getLogin() != null && !portalLink.getLogin().isEmpty()) &&
                (portalLink.getPassword() != null && !portalLink.getPassword().isEmpty());
    }

    private String indexTemplate(Model model, HttpServletRequest request, String displayMode) {
        model.addAttribute("displayMode", displayMode);
        String index = "index";
        if (displayMode != null) {
            if (displayMode.equals("leftRight")) {
                index = "index_ltr";
            }
            if (displayMode.equals("topDown")) {
                index = "index_td";
            }
        }
        return isAjax(request) ? index + " :: body" : index;
    }

}
