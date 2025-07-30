package ch.zmotions.linkit.service.util.auth;

import ch.zmotions.linkit.service.domain.UserEO;
import ch.zmotions.linkit.service.domain.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthHelper {
    private final UserRepository userRepository;
    private final AESEncryptorDecryptor aesEncryptorDecryptor;

    public AuthHelper(UserRepository userRepository, AESEncryptorDecryptor aesEncryptorDecryptor) {
        this.userRepository = userRepository;
        this.aesEncryptorDecryptor = aesEncryptorDecryptor;
    }

    public Optional<UserEO> getLoginUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null) {
                return userRepository.findByUsername(authentication.getName());
            }
        }
        return Optional.empty();
    }

    public String encrypt(String toEncrypt){
        return aesEncryptorDecryptor.encrypt(toEncrypt);
    }

    public String decrypt(String toDecrypt){
        return aesEncryptorDecryptor.decrypt(toDecrypt);
    }

}
