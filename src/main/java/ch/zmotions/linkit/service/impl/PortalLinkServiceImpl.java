package ch.zmotions.linkit.service.impl;

import ch.zmotions.linkit.service.domain.PortalLinkEO;
import ch.zmotions.linkit.service.domain.UserEO;
import ch.zmotions.linkit.service.domain.repository.PortalLinkRepository;
import ch.zmotions.linkit.service.PortalLinkService;
import ch.zmotions.linkit.service.domain.repository.UserRepository;
import ch.zmotions.linkit.service.util.auth.AuthHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PortalLinkServiceImpl implements PortalLinkService {
    private final PortalLinkRepository portalLinkRepository;
    private final UserRepository userRepository;
    private final AuthHelper authHelper;

    public PortalLinkServiceImpl(PortalLinkRepository portalLinkRepository, UserRepository userRepository,
                                 AuthHelper authHelper) {
        this.portalLinkRepository = portalLinkRepository;
        this.userRepository = userRepository;
        this.authHelper = authHelper;
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public Optional<PortalLinkEO> create(PortalLinkEO newEo) {
        Optional<PortalLinkEO> savedPortalLink = save(new PortalLinkEO());
        if (savedPortalLink.isPresent()) {
            newEo.setId(savedPortalLink.get().getId());
            return update(newEo);
        }
        return Optional.empty();
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public Optional<PortalLinkEO> update(PortalLinkEO entity) {
        Optional<PortalLinkEO> existingPortalLink = findById(entity.getId());
        if (existingPortalLink.isPresent()) {
            PortalLinkEO modifiedPortalLink = existingPortalLink.get();
            modifiedPortalLink.setOwner(entity.getOwner());
            modifiedPortalLink.setHref(entity.getHref());
            modifiedPortalLink.setName(entity.getName());
            modifiedPortalLink.setDescription(entity.getDescription());
            modifiedPortalLink.setCategory(entity.getCategory());
            modifiedPortalLink.setMedia(entity.getMedia());
            modifiedPortalLink.setType(entity.getType());
            modifiedPortalLink.setPublic(entity.isPublic());
            if (entity.getLogin() != null) {
                if (entity.getLogin().isEmpty()) {
                    modifiedPortalLink.setLogin(null);
                } else {
                    modifiedPortalLink.setLogin(entity.getLogin());
                }
            }
            if (entity.getPassword() != null) {
                if (entity.getPassword().isEmpty()) {
                    modifiedPortalLink.setPassword(null);
                } else {
                    if (modifiedPortalLink.getPassword() == null ||
                            !modifiedPortalLink.getPassword().equals(entity.getPassword())) {
                        modifiedPortalLink.setPassword(authHelper.encrypt(entity.getPassword()));
                    }
                }
            }
            modifiedPortalLink.getSharedUsers().forEach(sharedUser -> {
                sharedUser.removeSharedLink(modifiedPortalLink);
                userRepository.save(sharedUser);
            });
            entity.getSharedUsers().forEach(sharedUser -> userRepository.findById(sharedUser.getId()).ifPresent(aSharedUser -> {
                aSharedUser.addSharedLink(modifiedPortalLink);
                userRepository.save(aSharedUser);
            }));
            return save(modifiedPortalLink);
        }
        return Optional.empty();
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @Override
    public List<PortalLinkEO> findAll() {
        return portalLinkRepository.findAll();
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public Optional<PortalLinkEO> findById(UUID id) {
        return portalLinkRepository.findById(id);
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public void removeById(UUID id) {
        findById(id).ifPresent(this::remove);
    }

    private void remove(PortalLinkEO portalLink) {
        portalLink.getSharedUsers().forEach(user -> user.removeSharedLink(portalLink));
        if (portalLink.getOwner() != null &&
                portalLink.getOwner().getMyLinks().contains(portalLink)) {
            portalLink.getOwner().removeMyLink(portalLink);
        }
        portalLink.setOwner(null);
        portalLinkRepository.delete(portalLink);
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @Override
    public void removeAll() {
        findAll().forEach(this::remove);
    }

    private Optional<PortalLinkEO> save(PortalLinkEO saveEo) {
        return Optional.of(portalLinkRepository.save(saveEo));
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public List<PortalLinkEO> getSharedLinks() {
        Optional<UserEO> currentUser = authHelper.getLoginUser();
        if (currentUser.isPresent()) {
            return currentUser.get().getSharedLinks();
        }
        return new ArrayList<>();
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public List<PortalLinkEO> getGlobalLinks() {
        return portalLinkRepository.findAllByOwnerIsNull();
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public List<PortalLinkEO> getPersonalLinks() {
        Optional<UserEO> currentUser = authHelper.getLoginUser();
        if (currentUser.isPresent()) {
            return currentUser.get().getMyLinks();
        }
        return new ArrayList<>();
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public List<String> getCategories() {
        return portalLinkRepository.findDistinctByCategoryIsNotNull().stream()
                .map(PortalLinkEO::getCategory)
                .collect(Collectors.toList());
    }
}
