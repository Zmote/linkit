package ch.zmotions.linkit.service.impl;

import ch.zmotions.linkit.service.PageService;
import ch.zmotions.linkit.service.domain.PortalLinkEO;
import ch.zmotions.linkit.service.domain.UserEO;
import ch.zmotions.linkit.service.domain.repository.PortalLinkRepository;
import ch.zmotions.linkit.service.domain.repository.UserRepository;
import ch.zmotions.linkit.service.util.auth.AuthHelper;
import ch.zmotions.linkit.service.util.sort.SortHelper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PageServiceImpl implements PageService {

    private final UserRepository userRepository;
    private final PortalLinkRepository portalLinkRepository;
    private final SortHelper sortHelper;
    private final AuthHelper authHelper;

    private int DEFAULT_PAGE_SIZE = 10;
    private int DEFAULT_PAGE = 0;

    public PageServiceImpl(UserRepository userRepository, PortalLinkRepository portalLinkRepository,
                           SortHelper sortHelper, AuthHelper authHelper) {
        this.userRepository = userRepository;
        this.portalLinkRepository = portalLinkRepository;
        this.sortHelper = sortHelper;
        this.authHelper = authHelper;
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @Override
    public Page<UserEO> getPagedUsers() {
        return getPagedUsers(DEFAULT_PAGE, DEFAULT_PAGE_SIZE, sortHelper.defaultPropertyAndDirection());
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @Override
    public Page<UserEO> getPagedUsers(int pageNo, int size, String sort) {
        Page<UserEO> page = userRepository.findAll(PageRequest.of(pageNo, size, sortHelper.getSort(sort)));
        if (page.hasContent() || pageNo == 0) {
            return page;
        }
        return getPagedUsers(pageNo - 1, size, sort);
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public Page<PortalLinkEO> getPagedGlobalLinks() {
        return getPagedGlobalLinks(DEFAULT_PAGE, DEFAULT_PAGE_SIZE, sortHelper.defaultPropertyAndDirection());
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public Page<PortalLinkEO> getPagedGlobalLinks(int pageNo, int size, String sort) {
        Page<PortalLinkEO> page = portalLinkRepository.findAllByOwnerIsNull(
                PageRequest.of(pageNo, size, sortHelper.getSort(sort)));
        if (page.hasContent() || pageNo == 0) {
            return page;
        }
        return getPagedGlobalLinks(pageNo - 1, size, sort);
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public Page<PortalLinkEO> getPagedPersonalLinks() {
        return getPagedPersonalLinks(DEFAULT_PAGE, DEFAULT_PAGE_SIZE, sortHelper.defaultPropertyAndDirection());
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    @Override
    public Page<PortalLinkEO> getPagedPersonalLinks(int pageNo, int size, String sort) {
        Optional<UserEO> currentUser = authHelper.getLoginUser();
        if (currentUser.isPresent()) {
            return getPagedPersonalLinks(currentUser.get(), pageNo, size, sort);
        }
        return Page.empty();
    }

    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_USER') || hasRole('ROLE_ADMIN'))")
    private Page<PortalLinkEO> getPagedPersonalLinks(UserEO owner, int pageNo, int size, String sort) {
        Page<PortalLinkEO> page = portalLinkRepository.findAllByOwner(owner,
                PageRequest.of(pageNo, size, sortHelper.getSort(sort)));
        if (page.hasContent() || pageNo == 0) {
            return page;
        }
        return getPagedPersonalLinks(owner, pageNo - 1, size, sort);
    }
}
