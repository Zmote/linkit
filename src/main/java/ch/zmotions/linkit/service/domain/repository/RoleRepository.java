package ch.zmotions.linkit.service.domain.repository;

import ch.zmotions.linkit.service.domain.RoleEO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleEO, UUID> {
    Optional<RoleEO> findByNameIs(String name);
}
