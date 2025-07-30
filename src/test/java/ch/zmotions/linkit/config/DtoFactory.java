package ch.zmotions.linkit.config;

import ch.zmotions.linkit.commons.dto.*;
import ch.zmotions.linkit.commons.types.PortalLinkType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DtoFactory {
    public static final UUID knownPortalLinkId = UUID.randomUUID();
    public static final UUID knownUserId = UUID.randomUUID();
    public static final UUID knownPortalId = UUID.randomUUID();
    private static final UUID knownRoleUserId = UUID.randomUUID();
    public static final UUID knownRoleAdminId = UUID.randomUUID();

    public static RoleDto createDummyRoleDtoUser() {
        RoleDto role = new RoleDto();
        role.setId(knownRoleUserId);
        role.setName("ROLE_USER");
        return role;
    }

    public static RoleDto createDummyRoleDtoAdmin() {
        RoleDto role = new RoleDto();
        role.setId(knownRoleAdminId);
        role.setName("ROLE_ADMIN");
        return role;
    }

    private static List<RoleDto> createDummyRoleDtos() {
        return new ArrayList<RoleDto>() {{
            add(createDummyRoleDtoUser());
            add(createDummyRoleDtoAdmin());
        }};
    }

    public static UserDto createDummyUserDto() {
        UserDto userDto = createRandomDummyUserDto();
        userDto.setId(knownUserId);
        userDto.setUsername("Test" + knownUserId);
        return userDto;
    }

    public static RestrictedUserDto createDummyRestrictedUserDto() {
        UserDto userDto = createRandomDummyUserDto();
        userDto.setId(knownUserId);
        userDto.setUsername("Test" + knownUserId);
        RestrictedUserDto restrictedUserDto = new RestrictedUserDto();
        restrictedUserDto.setUsername(userDto.getUsername());
        restrictedUserDto.setFirstname(userDto.getFirstname());
        restrictedUserDto.setLastname(userDto.getLastname());
        restrictedUserDto.setPassword(userDto.getPassword());
        return restrictedUserDto;
    }

    public static UserDto createRandomDummyUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(UUID.randomUUID());
        userDto.setEnabled(true);
        userDto.setNew(true);
        userDto.setAccountNonLocked(true);
        userDto.setUsername("Test" + UUID.randomUUID());
        userDto.setFirstname("Test");
        userDto.setLastname("Test");
        userDto.setPassword("testpassword");
        userDto.setRoles(createDummyRoleDtos());
        return userDto;
    }

    public static PortalLinkDto createDummyPortalLinkDto() {
        PortalLinkDto portalLinkDto = new PortalLinkDto();
        portalLinkDto.setId(knownPortalLinkId);
        portalLinkDto.setName("Some Link");
        portalLinkDto.setDescription("Some Description");
        portalLinkDto.setHref("www.20min.ch");
        portalLinkDto.setMedia("./uploads/file.jpg");
        portalLinkDto.setCategory("Medien");
        portalLinkDto.setType(PortalLinkType.WEB);
        portalLinkDto.setOwner(null);
        portalLinkDto.setLogin("admin");
        portalLinkDto.setPassword("root@SOMEPASS2019");
        portalLinkDto.setSharedUsers(new ArrayList<UserDto>() {{
            add(createRandomDummyUserDto());
            add(createRandomDummyUserDto());
        }});
        return portalLinkDto;
    }

    public static PortalDto createDummyPortalDto() {
        PortalDto portalDto = new PortalDto();
        portalDto.setName("Portal");
        portalDto.setCopyright("Copyright");
        portalDto.setCustomCss("CustomCss");
        portalDto.setId(knownPortalId);
        portalDto.setBlockedList("0.0.0.0");
        portalDto.setMaxLoginAttempts(3);
        return portalDto;
    }

    public static FileResponseDto createDummyFileResponseDto() {
        return new FileResponseDto("testfile.jpg", "./uploads/testfile.jpg", "TEST", 10);
    }
}
