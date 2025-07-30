package ch.zmotions.linkit.service.impl;

import ch.zmotions.linkit.service.domain.UserEO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public abstract class AbstractUserServiceImpl {
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AbstractUserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    UserEO updateBaseFields(UserEO entity, UserEO modifiedUser) {
        modifiedUser.setUsername(entity.getUsername());
        modifiedUser.setFirstname(entity.getFirstname());
        modifiedUser.setLastname(entity.getLastname());
        if (entity.getPassword() != null && !entity.getPassword().isEmpty()) {
            if (!entity.getPassword().equals(modifiedUser.getPassword())) {
                modifiedUser.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
            }
        }
        if (entity.getEnabled() != null) {
            modifiedUser.setEnabled(entity.getEnabled());
        }
        if (entity.getAccountNonLocked() != null) {
            if (entity.getAccountNonLocked()) {
                modifiedUser.setLoginAttempts(0);
            }
            modifiedUser.setAccountNonLocked(entity.getAccountNonLocked());
        }
        return modifiedUser;
    }
}
