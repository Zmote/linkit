package ch.zmotions.linkit.service;

import ch.zmotions.linkit.service.domain.PortalLinkEO;

import java.util.List;

public interface PortalLinkService extends CrudService<PortalLinkEO> {
    List<PortalLinkEO> getSharedLinks();
    List<PortalLinkEO> getGlobalLinks();
    List<PortalLinkEO> getPersonalLinks();
    List<String> getCategories();
}
