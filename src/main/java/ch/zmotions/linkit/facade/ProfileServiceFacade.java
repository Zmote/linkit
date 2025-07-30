package ch.zmotions.linkit.facade;

import ch.zmotions.linkit.commons.dto.UserDto;
import ch.zmotions.linkit.service.ProfileService;
import ch.zmotions.linkit.service.domain.UserEO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceFacade {

    private final ProfileService profileService;
    private final ModelMapper mapper;

    public ProfileServiceFacade(ProfileService profile, ModelMapper mapper) {
        this.profileService = profile;
        this.mapper = mapper;
    }

    public Optional<UserDto> findByUsername(String username) {
        return profileService.findByUsername(username).map(user -> mapper.map(user, UserDto.class));
    }

    public Optional<UserDto> updateProfile(UserDto entity) {
        Optional<UserEO> optionalUser = profileService.updateProfile(mapper.map(entity, UserEO.class));
        return optionalUser.map(user -> mapper.map(user, UserDto.class));
    }

    public Boolean changePassword(String newPassword){
        return profileService.changePassword(newPassword);
    }
}
