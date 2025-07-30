package ch.zmotions.linkit.service.impl;

import ch.zmotions.linkit.service.RoleService;
import ch.zmotions.linkit.service.domain.RoleEO;
import ch.zmotions.linkit.service.domain.repository.RoleRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<RoleEO> findAll() {
        return roleRepository.findAll(
                Sort.by(
                        Sort.Order.asc("id")
                )
        );
    }

    @Override
    public Optional<RoleEO> findById(UUID id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<RoleEO> create(RoleEO entity) {
        return save(entity);
    }

    @Override
    public Optional<RoleEO> update(RoleEO entity) {
        return save(entity);
    }

    @Override
    public void removeById(UUID id) {
        findById(id).ifPresent(this::remove);
    }

    private void remove(RoleEO role) {
        role.getUsers().forEach(user -> user.removeRole(role));
        roleRepository.delete(role);
    }

    @Override
    public void removeAll() {
        findAll().forEach(this::remove);
    }

    private Optional<RoleEO> save(RoleEO entity) {
        return Optional.of(roleRepository.save(entity));
    }

    @Override
    public Optional<RoleEO> getRoleUser() {
        return roleRepository.findByNameIs("ROLE_USER");
    }

    @Override
    public Optional<RoleEO> getRoleAdmin() {
        return roleRepository.findByNameIs("ROLE_ADMIN");
    }
}
