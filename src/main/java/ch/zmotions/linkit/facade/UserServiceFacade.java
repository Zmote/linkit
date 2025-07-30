package ch.zmotions.linkit.facade;

import ch.zmotions.linkit.commons.dto.UserDto;
import ch.zmotions.linkit.service.UserService;
import ch.zmotions.linkit.service.domain.UserEO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceFacade implements CrudFacade<UserDto> {

    private final UserService userService;
    private final ModelMapper mapper;

    public UserServiceFacade(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public List<UserDto> findAll() {
        return userService.findAll().stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findById(UUID id) {
        Optional<UserEO> optionalUser = userService.findById(id);
        return optionalUser.map(user -> mapper.map(user, UserDto.class));
    }

    @Override
    public Optional<UserDto> create(UserDto entity) {
        Optional<UserEO> optionalUser = userService.create(mapper.map(entity, UserEO.class));
        return optionalUser.map(user -> mapper.map(user, UserDto.class));
    }

    @Override
    public Optional<UserDto> update(UserDto entity) {
        Optional<UserEO> optionalUser = userService.update(mapper.map(entity, UserEO.class));
        return optionalUser.map(user -> mapper.map(user, UserDto.class));
    }

    @Override
    public void removeById(UUID id) {
        userService.removeById(id);
    }

    @Override
    public void removeAll() {
        userService.removeAll();
    }
}
