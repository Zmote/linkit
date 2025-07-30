package ch.zmotions.linkit.service;

import ch.zmotions.linkit.service.domain.UserEO;

import java.util.Optional;

public interface ProfileService{
    Optional<UserEO> updateProfile(UserEO entity);
    Boolean changePassword(String newPassword);
    Optional<UserEO> findByUsername(String username);
}
