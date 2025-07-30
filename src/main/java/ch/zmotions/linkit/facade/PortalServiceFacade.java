package ch.zmotions.linkit.facade;

import ch.zmotions.linkit.commons.dto.PortalDto;
import ch.zmotions.linkit.service.PortalService;
import ch.zmotions.linkit.service.domain.PortalEO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PortalServiceFacade implements CrudFacade<PortalDto> {

    private final PortalService portalService;
    private final ModelMapper mapper;

    public PortalServiceFacade(PortalService portalService, ModelMapper mapper) {
        this.portalService = portalService;
        this.mapper = mapper;
    }

    @Override
    public List<PortalDto> findAll() {
        return portalService.findAll().stream()
                .map(portal -> mapper.map(portal, PortalDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PortalDto> findById(UUID id) {
        return portalService.findById(id).map(portal -> mapper.map(portal, PortalDto.class));
    }

    @Override
    public Optional<PortalDto> create(PortalDto entity) {
        return portalService.create(mapper.map(entity, PortalEO.class))
                .map(portal -> mapper.map(portal, PortalDto.class));
    }

    @Override
    public Optional<PortalDto> update(PortalDto entity) {
        return portalService.update(mapper.map(entity, PortalEO.class))
                .map(portal -> mapper.map(portal, PortalDto.class));
    }

    @Override
    public void removeById(UUID id) {
        portalService.removeById(id);
    }

    @Override
    public void removeAll() {
        portalService.removeAll();
    }

    public Optional<PortalDto> findOne() {
        return portalService.findOne().map(portal -> mapper.map(portal, PortalDto.class));
    }
}
