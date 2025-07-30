package ch.zmotions.linkit.service;

import java.util.List;

public interface LoginAttemptService {
    void loginSucceeded(String key);

    void loginFailed(String key);

    boolean isBlocked(String key);

    List<String> getBlockedList();

    void updateBlocketList(List<String> blockedlist);

    void increaseUserLoginAttempt(String username);

    void clearLoginAttempts(String username);

    void clearCache();
}
