package ch.zmotions.linkit.commons.dto;

import ch.zmotions.linkit.config.DtoFactory;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class RoleDtoTest {

    @Test
    public void getId() {
        RoleDto roleDto = DtoFactory.createDummyRoleDtoAdmin();
        assertEquals(DtoFactory.knownRoleAdminId, roleDto.getId());
    }

    @Test
    public void setId() {
        RoleDto roleDto = DtoFactory.createDummyRoleDtoAdmin();
        assertEquals(DtoFactory.knownRoleAdminId, roleDto.getId());
        UUID newUuid = UUID.randomUUID();
        roleDto.setId(newUuid);
        assertEquals(newUuid, roleDto.getId());
    }

    @Test
    public void getName() {
        RoleDto roleDto = DtoFactory.createDummyRoleDtoAdmin();
        assertEquals("ROLE_ADMIN", roleDto.getName());
    }

    @Test
    public void setName() {
        RoleDto roleDto = DtoFactory.createDummyRoleDtoAdmin();
        assertEquals("ROLE_ADMIN", roleDto.getName());
        roleDto.setName("ROLE_TEST");
        assertEquals("ROLE_TEST", roleDto.getName());
    }

    @Test
    public void equals() {
        RoleDto roleDto = DtoFactory.createDummyRoleDtoAdmin();
        Object object = new Object();
        assertNotEquals(roleDto, object);
    }

    @Test
    public void equals2() {
        RoleDto roleDto = DtoFactory.createDummyRoleDtoAdmin();
        RoleDto otherDto = DtoFactory.createDummyRoleDtoUser();
        assertEquals(roleDto, roleDto);
        assertNotEquals(roleDto, otherDto);
    }
}