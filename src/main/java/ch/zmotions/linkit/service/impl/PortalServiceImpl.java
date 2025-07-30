package ch.zmotions.linkit.service.impl;

import ch.zmotions.linkit.service.PortalService;
import ch.zmotions.linkit.service.domain.PortalEO;
import ch.zmotions.linkit.service.domain.repository.PortalRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PortalServiceImpl implements PortalService {

    private final PortalRepository portalRepository;

    public PortalServiceImpl(PortalRepository portalRepository) {
        this.portalRepository = portalRepository;
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @Override
    public List<PortalEO> findAll() {
        return portalRepository.findAll();
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @Override
    public Optional<PortalEO> findById(UUID id) {
        return portalRepository.findById(id);
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @Override
    public Optional<PortalEO> create(PortalEO entity) {
        return save(entity);
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @Override
    public Optional<PortalEO> update(PortalEO entity) {
        Optional<PortalEO> portalSetting = findOne();
        if (portalSetting.isPresent()) {
            PortalEO modifiedPortalSetting = portalSetting.get();
            modifiedPortalSetting.setName(entity.getName());
            modifiedPortalSetting.setCopyright(entity.getCopyright());
            modifiedPortalSetting.setCustomCss(entity.getCustomCss());
            modifiedPortalSetting.setMaxLoginAttempts(entity.getMaxLoginAttempts());
            save(modifiedPortalSetting);
        }
        return Optional.empty();
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @Override
    public void removeById(UUID id) {
        portalRepository.deleteById(id);
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @Override
    public void removeAll() {
        portalRepository.deleteAll();
    }

    private Optional<PortalEO> save(PortalEO entity) {
        return Optional.of(portalRepository.save(entity));
    }

    /**
     * Returns exactly one setting for the intermediate period. Later on, if multi portal
     * support is added, this can be replaced by findById. Be cautious with the access though
     * as it needs to be carefully designed to not allow an attack. Currently, findOne takes
     * no paramas so it's not vulnerable to external calls.
     *
     * @return System Settings
     */
    @Override
    public Optional<PortalEO> findOne() {
        return findAll().stream().findFirst();
    }
}
