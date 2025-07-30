package ch.zmotions.linkit.service.domain.repository;

import ch.zmotions.linkit.service.domain.PortalLinkEO;
import ch.zmotions.linkit.service.domain.UserEO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PortalLinkRepository extends JpaRepository<PortalLinkEO, UUID> {
    List<PortalLinkEO> findAllByOwnerIsNull();

    Page<PortalLinkEO> findAllByOwnerIsNull(Pageable page);

    List<PortalLinkEO> findAllByOwner(UserEO owner);

    Page<PortalLinkEO> findAllByOwner(UserEO owner, Pageable pageable);

    List<PortalLinkEO> findDistinctByCategoryIsNotNull();
}
