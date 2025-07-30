package ch.zmotions.linkit.controller.advice;

import ch.zmotions.linkit.commons.dto.PortalDto;
import ch.zmotions.linkit.commons.dto.UserDto;
import ch.zmotions.linkit.config.auth.LoginUser;
import ch.zmotions.linkit.controller.AppController;
import ch.zmotions.linkit.controller.ConfigurationController;
import ch.zmotions.linkit.controller.error.MyCustomErrorController;
import ch.zmotions.linkit.facade.AuthHelperFacade;
import ch.zmotions.linkit.facade.PortalServiceFacade;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@ControllerAdvice(assignableTypes = {AppController.class, ConfigurationController.class, MyCustomErrorController.class})
public class GlobalAttributesAdvice {

    private final AuthHelperFacade authHelperFacade;
    private final PortalServiceFacade portalServiceFacade;

    public GlobalAttributesAdvice(AuthHelperFacade authHelperFacade, PortalServiceFacade portalServiceFacade) {
        this.authHelperFacade = authHelperFacade;
        this.portalServiceFacade = portalServiceFacade;
    }

    @ModelAttribute("loginUser")
    public LoginUser populateUser() {
        try {
            Optional<UserDto> loginUserOptional = authHelperFacade.getLoginUser();
            if (loginUserOptional.isPresent()) {
                UserDto loginUser = loginUserOptional.get();
                return new LoginUser(loginUser.getUsername(), loginUser.getFullname());
            } else {
                return new LoginUser("", "");
            }
        } catch (AccessDeniedException ex) {
            return new LoginUser("", "");
        }
    }

    @ModelAttribute("system")
    public PortalDto systemSettings() {
        try {
            Optional<PortalDto> systemSetting = portalServiceFacade.findOne();
            return systemSetting.orElseGet(PortalDto::new);
        } catch (AccessDeniedException ex) {
            return new PortalDto();
        }
    }
}
