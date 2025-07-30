package ch.zmotions.linkit.service;

import ch.zmotions.linkit.service.domain.RoleEO;

import java.util.Optional;

public interface RoleService extends CrudService<RoleEO> {
    Optional<RoleEO> getRoleUser();
    Optional<RoleEO> getRoleAdmin();
}
