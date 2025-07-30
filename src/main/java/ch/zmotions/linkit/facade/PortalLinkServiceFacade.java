package ch.zmotions.linkit.facade;

import ch.zmotions.linkit.commons.dto.PortalLinkDto;
import ch.zmotions.linkit.service.PortalLinkService;
import ch.zmotions.linkit.service.domain.PortalLinkEO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PortalLinkServiceFacade implements CrudFacade<PortalLinkDto> {

    private final PortalLinkService portalLinkService;
    private final ModelMapper mapper;

    public PortalLinkServiceFacade(PortalLinkService portalLinkService, ModelMapper mapper) {
        this.portalLinkService = portalLinkService;
        this.mapper = mapper;
    }

    @Override
    public List<PortalLinkDto> findAll() {
        return portalLinkService.findAll().stream()
                .map(portalLink -> mapper.map(portalLink, PortalLinkDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PortalLinkDto> findById(UUID id) {
        return portalLinkService.findById(id).map(portalLink -> mapper.map(portalLink, PortalLinkDto.class));
    }

    @Override
    public Optional<PortalLinkDto> create(PortalLinkDto entity) {
        return portalLinkService.create(mapper.map(entity, PortalLinkEO.class))
                .map(portalLink -> mapper.map(portalLink, PortalLinkDto.class));
    }

    @Override
    public Optional<PortalLinkDto> update(PortalLinkDto entity) {
        return portalLinkService.update(mapper.map(entity, PortalLinkEO.class))
                .map(portalLink -> mapper.map(portalLink, PortalLinkDto.class));
    }

    @Override
    public void removeById(UUID id) {
        portalLinkService.removeById(id);
    }

    @Override
    public void removeAll() {
        portalLinkService.removeAll();
    }

    public List<PortalLinkDto> getSharedLinks() {
        return portalLinkService.getSharedLinks().stream()
                .map(portalLink -> mapper.map(portalLink, PortalLinkDto.class))
                .collect(Collectors.toList());
    }

    public List<PortalLinkDto> getGlobalLinks() {
        return portalLinkService.getGlobalLinks().stream()
                .map(portalLink -> mapper.map(portalLink, PortalLinkDto.class))
                .collect(Collectors.toList());
    }

    public List<PortalLinkDto> getPersonalLinks() {
        return portalLinkService.getPersonalLinks().stream()
                .map(portalLink -> mapper.map(portalLink, PortalLinkDto.class))
                .collect(Collectors.toList());
    }

    public List<String> getCategories() {
        return portalLinkService.getCategories();
    }
}
