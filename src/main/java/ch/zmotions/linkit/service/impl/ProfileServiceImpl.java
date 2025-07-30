package ch.zmotions.linkit.service.impl;

import ch.zmotions.linkit.service.ProfileService;
import ch.zmotions.linkit.service.domain.UserEO;
import ch.zmotions.linkit.service.domain.repository.UserRepository;
import ch.zmotions.linkit.service.util.auth.AuthHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
public class ProfileServiceImpl extends AbstractUserServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final AuthHelper authHelper;

    public ProfileServiceImpl(UserRepository userRepository,
                              AuthHelper authHelper,
                              BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(bCryptPasswordEncoder);
        this.authHelper = authHelper;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserEO> updateProfile(UserEO entity) {
        Optional<UserEO> existingUser = authHelper.getLoginUser();
        return existingUser.flatMap(userEO -> {
            userEO.setNew(false);
            return save(updateBaseFields(entity, userEO));
        });
    }

    @Override
    public Boolean changePassword(String newPassword) {
        Optional<UserEO> user = authHelper.getLoginUser();
        if (user.isPresent()) {
            user.get().setPassword(bCryptPasswordEncoder.encode(newPassword));
            user.get().setNew(false);
            return save(user.get()).isPresent();
        }
        return false;
    }

    private Optional<UserEO> save(UserEO entity) {
        return Optional.of(userRepository.save(entity));
    }

    @Override
    public Optional<UserEO> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
