package ch.zmotions.linkit.service;

import ch.zmotions.linkit.service.domain.PortalLinkEO;
import ch.zmotions.linkit.service.domain.UserEO;
import org.springframework.data.domain.Page;

public interface PageService {
    Page<UserEO> getPagedUsers();
    Page<UserEO> getPagedUsers(int pageNo, int size, String sort);
    Page<PortalLinkEO> getPagedGlobalLinks();
    Page<PortalLinkEO> getPagedGlobalLinks(int page, int size, String sort);
    Page<PortalLinkEO> getPagedPersonalLinks();
    Page<PortalLinkEO> getPagedPersonalLinks(int page, int size, String sort);
}
