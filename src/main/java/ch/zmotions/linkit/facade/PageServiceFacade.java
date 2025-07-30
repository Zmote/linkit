package ch.zmotions.linkit.facade;

import ch.zmotions.linkit.commons.dto.PortalLinkDto;
import ch.zmotions.linkit.commons.dto.UserDto;
import ch.zmotions.linkit.service.PageService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


@Component
public class PageServiceFacade {

    private final PageService pageService;
    private final ModelMapper mapper;

    public PageServiceFacade(PageService pageService, ModelMapper mapper) {
        this.pageService = pageService;
        this.mapper = mapper;
    }

    public Page<UserDto> getPagedUsers() {
        return pageService.getPagedUsers().map(user -> mapper.map(user, UserDto.class));
    }

    public Page<UserDto> getPagedUsers(int pageNo, int size, String sort) {
        return pageService.getPagedUsers(pageNo, size, sort).map(user -> mapper.map(user, UserDto.class));
    }

    public Page<PortalLinkDto> getPagedGlobalLinks() {
        return pageService.getPagedGlobalLinks().map(portalLink -> mapper.map(portalLink, PortalLinkDto.class));
    }

    public Page<PortalLinkDto> getPagedGlobalLinks(int page, int size, String sort) {
        return pageService.getPagedGlobalLinks(page, size, sort).map(portalLink -> mapper.map(portalLink, PortalLinkDto.class));
    }

    public Page<PortalLinkDto> getPagedPersonalLinks() {
        return pageService.getPagedPersonalLinks().map(portalLink -> mapper.map(portalLink, PortalLinkDto.class));
    }

    public Page<PortalLinkDto> getPagedPersonalLinks(int page, int size, String sort) {
        return pageService.getPagedPersonalLinks(page, size, sort).map(portalLink -> mapper.map(portalLink, PortalLinkDto.class));
    }
}
