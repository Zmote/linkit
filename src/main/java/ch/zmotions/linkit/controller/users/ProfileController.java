package ch.zmotions.linkit.controller.users;

import ch.zmotions.linkit.commons.dto.RestrictedUserDto;
import ch.zmotions.linkit.commons.dto.UserDto;
import ch.zmotions.linkit.controller.base.AbstractUserController;
import ch.zmotions.linkit.facade.AuthHelperFacade;
import ch.zmotions.linkit.facade.ProfileServiceFacade;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractUserController {

    private final ProfileServiceFacade profileServiceFacade;
    private final AuthHelperFacade authHelperFacade;

    public ProfileController(SessionRegistry sessionRegistry,
                             AuthHelperFacade authHelperFacade,
                             ProfileServiceFacade profileServiceFacade) {
        super(sessionRegistry);
        this.authHelperFacade = authHelperFacade;
        this.profileServiceFacade = profileServiceFacade;
    }


    @GetMapping("edit")
    public String editForm(Model model) {
        Optional<UserDto> entity = profileServiceFacade.findByUsername(authHelperFacade.getUsername());
        if (entity.isPresent()) {
            model.addAttribute("user", entity.get());
            return "profile/edit :: form";
        }
        return null;
    }

    @PutMapping
    public String updateUser(Model model, HttpServletResponse response,
                             @Valid @RequestBody RestrictedUserDto user, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            String beforeUsername = authHelperFacade.getUsername();
            Optional<UserDto> updatedUser = profileServiceFacade.updateProfile(new UserDto(user));
            if (updatedUser.isPresent()) {
                if (!beforeUsername.equals(updatedUser.get().getUsername())) {
                    invalidateSession(beforeUsername);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            return initValidationError(model, response, user, bindingResult);
        }
        return null;
    }


    private String initValidationError(Model model, HttpServletResponse response, RestrictedUserDto user, BindingResult bindingResult) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        model.addAttribute("user", user);
        model.addAttribute("errors", processBindingResult(bindingResult));
        return "profile/edit :: modal-body";
    }
}
