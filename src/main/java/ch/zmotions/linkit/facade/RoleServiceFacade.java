package ch.zmotions.linkit.facade;

import ch.zmotions.linkit.commons.dto.RoleDto;
import ch.zmotions.linkit.service.RoleService;
import ch.zmotions.linkit.service.domain.RoleEO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RoleServiceFacade implements CrudFacade<RoleDto> {

    private final RoleService roleService;
    private final ModelMapper mapper;

    public RoleServiceFacade(RoleService roleService, ModelMapper mapper) {
        this.roleService = roleService;
        this.mapper = mapper;
    }

    @Override
    public List<RoleDto> findAll() {
        return roleService.findAll().stream()
                .map(role -> mapper.map(role, RoleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoleDto> findById(UUID id) {
        return roleService.findById(id).map(role -> mapper.map(role, RoleDto.class));
    }

    @Override
    public Optional<RoleDto> create(RoleDto entity) {
        return roleService.create(mapper.map(entity, RoleEO.class))
                .map(role -> mapper.map(role, RoleDto.class));
    }

    @Override
    public Optional<RoleDto> update(RoleDto entity) {
        return roleService.update(mapper.map(entity, RoleEO.class))
                .map(role -> mapper.map(role, RoleDto.class));
    }

    @Override
    public void removeById(UUID id) {
        roleService.removeById(id);
    }

    @Override
    public void removeAll() {
        roleService.removeAll();
    }

    public Optional<RoleDto> getRoleUser() {
        return roleService.getRoleUser().map(role -> mapper.map(role, RoleDto.class));
    }

    public Optional<RoleDto> getRoleAdmin() {
        return roleService.getRoleAdmin().map(role -> mapper.map(role, RoleDto.class));
    }
}
