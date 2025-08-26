package ch.zmotions.linkit.controller.users;

import ch.zmotions.linkit.commons.dto.RoleDto;
import ch.zmotions.linkit.commons.dto.UserDto;
import ch.zmotions.linkit.controller.base.AbstractUserController;
import ch.zmotions.linkit.facade.RoleServiceFacade;
import ch.zmotions.linkit.facade.UserServiceFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends AbstractUserController {

    private final UserServiceFacade userServiceFacade;
    private final RoleServiceFacade roleServiceFacade;

    public UserController(UserServiceFacade userServiceFacade, SessionRegistry sessionRegistry, RoleServiceFacade roleServiceFacade) {
        super(sessionRegistry);
        this.roleServiceFacade = roleServiceFacade;
        this.userServiceFacade = userServiceFacade;
    }

    @GetMapping("{id}/edit")
    public String editForm(Model model, @PathVariable("id") UUID id) {
        Optional<UserDto> entity = userServiceFacade.findById(id);
        if (entity.isPresent()) {
            model.addAttribute("user", entity.get());
            model.addAttribute("availableRoles", calculateAvailableRoles(entity.get()));
            return "configurations/users/edit :: form";
        }
        return null;
    }

    @GetMapping("new")
    public String newForm(Model model) {
        UserDto newUser = new UserDto();
        newUser.setNew(true);
        newUser.setAccountNonLocked(true);
        newUser.setEnabled(true);
        model.addAttribute("user", newUser);
        model.addAttribute("availableRoles", roleServiceFacade.findAll());
        return "configurations/users/new :: form";
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") UUID id) {
        Optional<UserDto> toRemoveUser = userServiceFacade.findById(id);
        if (toRemoveUser.isPresent()) {
            invalidateSession(toRemoveUser.get().getUsername());
            userServiceFacade.removeById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public String updateUser(@PathVariable("id") UUID id, Model model, HttpServletResponse response,
                             @Valid @RequestBody UserDto user, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            Optional<UserDto> beforeUpdate = userServiceFacade.findById(id);
            if (beforeUpdate.isPresent()) {
                user.setId(id);
                user.setEnabled(user.getEnabled() != null ? user.getEnabled() : false);
                user.setAccountNonLocked(user.getAccountNonLocked() != null ? user.getAccountNonLocked() : false);
                String beforeUsername = beforeUpdate.get().getUsername();
                Optional<UserDto> updatedUser = userServiceFacade.update(user);
                if (updatedUser.isPresent()) {
                    if (!beforeUsername.equals(updatedUser.get().getUsername())) {
                        invalidateSession(beforeUsername);
                    }
                }
                response.setStatus(HttpStatus.OK.value());
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            }
        } else {
            return initValidationError(model, false, response, user, bindingResult);
        }
        return null;
    }

    @PostMapping
    public String newUser(HttpServletResponse response, Model model, @Valid @RequestBody UserDto user,
                          BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            user.setEnabled(user.getEnabled() != null ? user.getEnabled() : false);
            user.setAccountNonLocked(user.getAccountNonLocked() != null ? user.getAccountNonLocked() : false);
            Optional<UserDto> optional = userServiceFacade.create(user);
            if (optional.isPresent()) {
                response.setStatus(HttpStatus.OK.value());
            } else {
                response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            }
        } else {
            return initValidationError(model, true, response, user, bindingResult);
        }
        return null;
    }

    private String initValidationError(Model model, Boolean isNew, HttpServletResponse response, UserDto user, BindingResult bindingResult) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        model.addAttribute("user", user);
        restoreRoleAssociations(user);
        model.addAttribute("availableRoles", calculateAvailableRoles(user));
        model.addAttribute("errors", processBindingResult(bindingResult));
        return "configurations/users/" + (isNew ? "new" : "edit") + ":: modal-body";
    }

    private void restoreRoleAssociations(UserDto user) {
        if (user.getRoles() != null) {
            user.setRoles(user.getRoles().stream()
                    .map(role -> roleServiceFacade.findById(role.getId()).orElse(null))
                    .collect(Collectors.toList()));
        }
    }

    private List<RoleDto> calculateAvailableRoles(UserDto user) {
        if (user.getRoles() != null) {
            return roleServiceFacade.findAll()
                    .stream()
                    .filter(role -> !user.getRoles().contains(role))
                    .collect(Collectors.toList());
        } else {
            return roleServiceFacade.findAll();
        }
    }
}
