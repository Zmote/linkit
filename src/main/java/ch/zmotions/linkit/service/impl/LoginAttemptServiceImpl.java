package ch.zmotions.linkit.service.impl;

import ch.zmotions.linkit.service.LoginAttemptService;
import ch.zmotions.linkit.service.domain.PortalEO;
import ch.zmotions.linkit.service.domain.UserEO;
import ch.zmotions.linkit.service.domain.repository.PortalRepository;
import ch.zmotions.linkit.service.domain.repository.UserRepository;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private final int MAX_ATTEMPT = 12;
    private final int MAX_LOGIN_ATTEMPT_DEFAULT = 3;
    private LoadingCache<String, Integer> attemptsCache;
    private final Logger LOGGER = LoggerFactory.getLogger(LoginAttemptServiceImpl.class);
    private final UserRepository userRepository;
    private final PortalRepository portalRepository;

    public LoginAttemptServiceImpl(UserRepository userRepository, PortalRepository portalRepository) {
        super();
        this.userRepository = userRepository;
        this.portalRepository = portalRepository;
        attemptsCache = CacheBuilder.newBuilder().
                expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
            @Override
            @ParametersAreNonnullByDefault
            public Integer load(String key) {
                return 0;
            }
        });
    }

    @Override
    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    @Override
    public void loginFailed(String key) {
        int attempts = 0;
        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException e) {
            LOGGER.warn(e.toString());
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    @Override
    public boolean isBlocked(String key) {
        try {
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @Override
    public List<String> getBlockedList() {
        final List<String> blockedList = new ArrayList<>();
        attemptsCache.asMap().forEach((key, value) -> {
            if (value >= MAX_ATTEMPT) {
                blockedList.add(key);
            }
        });
        return blockedList;
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @Override
    public void updateBlocketList(List<String> blockedList) {
        blockedList.forEach(blockedIp -> {
            attemptsCache.put(blockedIp, MAX_ATTEMPT);
        });
    }

    @Override
    public void increaseUserLoginAttempt(String username) {
        Optional<UserEO> loginAttemptingUser = userRepository.findByUsername(username);
        loginAttemptingUser.ifPresent(loginUser -> {
            loginUser.setLoginAttempts(loginUser.getLoginAttempts() + 1);
            Optional<PortalEO> portalOptional = portalRepository.findAll().stream().findFirst();
            if (portalOptional.isPresent()) {
                PortalEO portal = portalOptional.get();
                if (loginUser.getLoginAttempts() > portal.getMaxLoginAttempts()) {
                    loginUser.setAccountNonLocked(false);
                }
            } else {
                if (loginUser.getLoginAttempts() > MAX_LOGIN_ATTEMPT_DEFAULT) {
                    loginUser.setAccountNonLocked(false);
                }
            }
            userRepository.save(loginUser);
        });
    }

    @Override
    public void clearLoginAttempts(String username) {
        Optional<UserEO> loginSuccedingUser = userRepository.findByUsername(username);
        loginSuccedingUser.ifPresent(loginUser -> {
            loginUser.setLoginAttempts(0);
            userRepository.save(loginUser);
        });
    }

    @Override
    public void clearCache() {
        attemptsCache.invalidateAll();
    }
}