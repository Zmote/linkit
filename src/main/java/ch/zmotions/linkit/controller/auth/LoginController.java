package ch.zmotions.linkit.controller.auth;

import ch.zmotions.linkit.commons.dto.ChangePasswordUserDto;
import ch.zmotions.linkit.commons.dto.PortalDto;
import ch.zmotions.linkit.commons.dto.UserDto;
import ch.zmotions.linkit.controller.base.AbstractController;
import ch.zmotions.linkit.facade.AuthHelperFacade;
import ch.zmotions.linkit.facade.PortalServiceFacade;
import ch.zmotions.linkit.facade.ProfileServiceFacade;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class LoginController extends AbstractController {

    private final PortalServiceFacade portalServiceFacade;
    private final ProfileServiceFacade profileServiceFacade;
    private final AuthHelperFacade authHelperFacade;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginController(PortalServiceFacade portalServiceFacade, ProfileServiceFacade profileServiceFacade,
                           AuthHelperFacade authHelperFacade,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.portalServiceFacade = portalServiceFacade;
        this.profileServiceFacade = profileServiceFacade;
        this.authHelperFacade = authHelperFacade;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @ModelAttribute("system")
    public PortalDto systemSettings() {
        Optional<PortalDto> systemSetting = portalServiceFacade.findOne();
        return systemSetting.orElseGet(PortalDto::new);
    }


    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error",
                    "Benutzer konnte nicht authentifiziert werden. " +
                            "Vergewissern Sie sich, dass alle Angaben korrekt sind. Allenfalls ist ihr Konto gesperrt " +
                            "oder deaktiviert. Nehmen Sie mit einem Admin Kontakt auf.");
        }
        if (logout != null) {
            model.addAttribute("message", "Erfolgreich ausgeloggt! Noch einen schönen Tag!");
        }
        return "login";
    }

    @GetMapping("/checkLogin")
    public String checkLogin(Model model) {
        Optional<UserDto> loginUser = authHelperFacade.getLoginUser();
        if (loginUser.isPresent()) {
            UserDto user = loginUser.get();
            if (user.isNew() == null || user.isNew()) {
                model.addAttribute("user", new ChangePasswordUserDto());
                return "changepassword";
            } else {
                return "redirect:/";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/changepassword")
    public String changePassword(Model model, @Valid @ModelAttribute ChangePasswordUserDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", new ChangePasswordUserDto());
            model.addAttribute("errors", processBindingResult(bindingResult));
            return "changepassword";
        } else {
            Optional<UserDto> loginUserOptional = authHelperFacade.getLoginUser();
            if (loginUserOptional.isPresent()) {
                UserDto loginUser = loginUserOptional.get();
                if (bCryptPasswordEncoder.matches(user.getOldpassword(), loginUser.getPassword())) {
                    if (profileServiceFacade.changePassword(user.getNewpassword())) {
                        return "redirect:/";
                    } else {
                        return "redirect:/login";
                    }
                }
                Map<String, List<String>> errors = processBindingResult(bindingResult);
                if (errors.containsKey("oldpassword")) {
                    errors.get("oldpassword").add("Falsches Passwort!");
                } else {
                    errors.put("oldpassword", new ArrayList<String>() {{
                        add("Falsches Passwort!");
                    }});
                }
                model.addAttribute("user", new ChangePasswordUserDto());
                model.addAttribute("errors", errors);
                return "changepassword";
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/expired")
    public String expired() {
        return "redirect:/";
    }
}
