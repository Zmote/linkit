package ch.zmotions.linkit.controller.portal;

import ch.zmotions.linkit.commons.dto.PortalDto;
import ch.zmotions.linkit.facade.PortalServiceFacade;
import ch.zmotions.linkit.service.LoginAttemptService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/portal")
public class PortalController {

    private final PortalServiceFacade portalServiceFacade;
    private final LoginAttemptService loginAttemptService;

    public PortalController(PortalServiceFacade portalServiceFacade, LoginAttemptService loginAttemptService) {
        this.portalServiceFacade = portalServiceFacade;
        this.loginAttemptService = loginAttemptService;
    }

    @PostMapping
    public String savePortalSettings(@ModelAttribute PortalDto portal) {
        portalServiceFacade.update(portal);
        String splitter = "";
        if (portal.getBlockedList().contains("\r") && portal.getBlockedList().contains("\n")) {
            splitter += "\r\n";
        } else if (portal.getBlockedList().contains("\n")) {
            splitter += "\n";
        } else {
            splitter += "\r";
        }
        loginAttemptService.updateBlocketList(Arrays.asList(portal.getBlockedList().split(splitter)));
        return "redirect:/configurations";
    }
}
