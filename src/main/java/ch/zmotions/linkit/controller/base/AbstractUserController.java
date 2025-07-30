package ch.zmotions.linkit.controller.base;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public abstract class AbstractUserController extends AbstractController {

    private final SessionRegistry sessionRegistry;

    public AbstractUserController(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    protected void invalidateSession(String username) {
        List<Object> principals = sessionRegistry.getAllPrincipals();
        for (Object principal : principals) {
            User user = (User) principal;
            if (user.getUsername().equals(username)) {
                List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(principal, false);
                for (SessionInformation sessionInformation : sessionInformations) {
                    sessionInformation.expireNow();
                }
            }
        }
    }
}
