package ch.zmotions.linkit.service.domain.repository;

import ch.zmotions.linkit.service.domain.PortalEO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PortalRepository extends JpaRepository<PortalEO, UUID> {}
