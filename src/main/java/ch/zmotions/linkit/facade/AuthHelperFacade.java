package ch.zmotions.linkit.facade;

import ch.zmotions.linkit.commons.dto.UserDto;
import ch.zmotions.linkit.service.util.auth.AuthHelper;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthHelperFacade {

    private final AuthHelper authHelper;
    private final ModelMapper mapper;

    public AuthHelperFacade(AuthHelper authHelper, ModelMapper mapper) {
        this.authHelper = authHelper;
        this.mapper = mapper;
    }

    public String getUsername() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return user.getUsername();
        } catch (NullPointerException ex) {
            throw new AccessDeniedException("Principal not found", ex);
        }
    }

    public Optional<UserDto> getLoginUser() {
        return authHelper.getLoginUser().map(user -> mapper.map(user, UserDto.class));
    }

    public String encrypt(String toEncrypt) {
        return authHelper.encrypt(toEncrypt);
    }

    public String decrypt(String toDecrypt) {
        return authHelper.decrypt(toDecrypt);
    }
}
