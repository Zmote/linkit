package ch.zmotions.linkit.auth;

import ch.zmotions.linkit.service.LoginAttemptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessEventListener
        implements ApplicationListener<AuthenticationSuccessEvent> {

    private final LoginAttemptService loginAttemptService;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationSuccessEventListener.class);

    public AuthenticationSuccessEventListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    public void onApplicationEvent(AuthenticationSuccessEvent e) {
        try {
            WebAuthenticationDetails auth = (WebAuthenticationDetails)
                    e.getAuthentication().getDetails();
            User user = (User) e.getAuthentication().getPrincipal();
            loginAttemptService.loginSucceeded(auth.getRemoteAddress());
            loginAttemptService.clearLoginAttempts(user.getUsername());
        } catch (NullPointerException ex) {
            LOGGER.warn("Could not retrieve Authentication Details");
        }
    }
}