package ch.zmotions.linkit.service.impl;

import ch.zmotions.linkit.service.UserService;
import ch.zmotions.linkit.service.domain.RoleEO;
import ch.zmotions.linkit.service.domain.UserEO;
import ch.zmotions.linkit.service.domain.repository.PortalLinkRepository;
import ch.zmotions.linkit.service.domain.repository.RoleRepository;
import ch.zmotions.linkit.service.domain.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl extends AbstractUserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PortalLinkRepository portalLinkRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder, PortalLinkRepository portalLinkRepository) {
        super(bCryptPasswordEncoder);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.portalLinkRepository = portalLinkRepository;
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public List<UserEO> findAll() {
        return userRepository.findAll();
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @Override
    public Optional<UserEO> findById(UUID id) {
        return userRepository.findById(id);
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @Override
    public Optional<UserEO> create(UserEO entity) {
        if (entity.getPassword() != null && !entity.getPassword().isEmpty()) {
            entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
        }
        roleRepository.findByNameIs("ROLE_USER").ifPresent(roleUser -> {
            if (entity.getRoles() != null) {
                if (!entity.getRoles().contains(roleUser)) {
                    entity.addRole(roleUser);
                }
            } else {
                entity.addRole(roleUser);
            }
        });
        entity.setNew(true);
        return save(entity);
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @Override
    public Optional<UserEO> update(UserEO entity) {
        Optional<UserEO> existingUser = findById(entity.getId());
        if (existingUser.isPresent()) {
            UserEO modifiedUser = updateBaseFields(entity, existingUser.get());
            if (entity.getRoles() != null) {
                if (entity.getRoles().isEmpty()) {
                    modifiedUser.clearRoles();
                } else {
                    List<RoleEO> roles = new ArrayList<>();
                    entity.getRoles().forEach(role -> roleRepository.findById(role.getId()).ifPresent(roles::add));
                    modifiedUser.setRoles(roles);
                }
            } else {
                modifiedUser.clearRoles();
            }
            addRoleUser(modifiedUser);
            return save(modifiedUser);
        }
        return Optional.empty();
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @Override
    public void removeById(UUID id) {
        findById(id).ifPresent(this::remove);
    }

    private void remove(UserEO user) {
        user.getSharedLinks().forEach(sharedLink -> sharedLink.removeSharedUser(user));
        user.getMyLinks().forEach(myLink -> {
            myLink.getSharedUsers().forEach(sharedUser -> sharedUser.removeSharedLink(myLink));
            myLink.setOwner(null);
            portalLinkRepository.delete(myLink);
        });
        user.getRoles().forEach(role -> role.removeUser(user));
        userRepository.delete(user);
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @Override
    public void removeAll() {
        findAll().forEach(this::remove);
    }

    private Optional<UserEO> save(UserEO entity) {
        return Optional.of(userRepository.save(entity));
    }

    private void addRoleUser(UserEO user) {
        roleRepository.findByNameIs("ROLE_USER").ifPresent(role -> {
            if (user.getRoles() != null) {
                if (!user.getRoles().contains(role)) {
                    user.addRole(role);
                }
            } else {
                user.setRoles(new ArrayList<>());
                user.addRole(role);
            }
        });
    }
}
