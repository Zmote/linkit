package ch.zmotions.linkit.service.domain;

import ch.zmotions.linkit.config.EOFactory;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RoleEOTest {

    @Test
    public void getId() {
        RoleEO roleUser = EOFactory.createDummyRoleUser();
        assertEquals(EOFactory.knownRoleUserId, roleUser.getId());
    }

    @Test
    public void setId() {
        RoleEO roleUser = EOFactory.createDummyRoleUser();
        assertEquals(EOFactory.knownRoleUserId, roleUser.getId());
        UUID newUuid = UUID.randomUUID();
        roleUser.setId(newUuid);
        assertEquals(newUuid, roleUser.getId());
    }

    @Test
    public void getName() {
        RoleEO roleUser = EOFactory.createDummyRoleUser();
        assertEquals("ROLE_USER", roleUser.getName());
    }

    @Test
    public void setName() {
        RoleEO roleUser = EOFactory.createDummyRoleUser();
        assertEquals("ROLE_USER", roleUser.getName());
        roleUser.setName("ROLE_TEST");
        assertEquals("ROLE_TEST", roleUser.getName());
    }

    @Test
    public void getUsers() {
        RoleEO roleUser = EOFactory.createDummyRoleUser();
        assertTrue(roleUser.getUsers().isEmpty());
    }

    @Test
    public void setUsers() {
        RoleEO roleUser = EOFactory.createDummyRoleUser();
        assertTrue(roleUser.getUsers().isEmpty());
        roleUser.getUsers().add(EOFactory.createDummyRandomUser());
        assertEquals(1, roleUser.getUsers().size());
    }

    @Test
    public void addUser() {
        RoleEO roleUser = EOFactory.createDummyRoleUser();
        assertTrue(roleUser.getUsers().isEmpty());
        roleUser.addUser(EOFactory.createDummyRandomUser());
        roleUser.addUser(EOFactory.createDummyRandomUser());
        assertEquals(2, roleUser.getUsers().size());
    }

    @Test
    public void removeUser() {
        RoleEO roleUser = EOFactory.createDummyRoleUser();
        assertTrue(roleUser.getUsers().isEmpty());
        roleUser.addUser(EOFactory.createDummyRandomUser());
        roleUser.addUser(EOFactory.createDummyRandomUser());
        assertEquals(2, roleUser.getUsers().size());
        UserEO user = EOFactory.createDummyUser();
        roleUser.addUser(user);
        assertEquals(3, roleUser.getUsers().size());
        roleUser.removeUser(user);
        assertEquals(2, roleUser.getUsers().size());
    }

    @Test
    public void clearUsers() {
        RoleEO roleUser = EOFactory.createDummyRoleUser();
        assertTrue(roleUser.getUsers().isEmpty());
        roleUser.addUser(EOFactory.createDummyRandomUser());
        roleUser.addUser(EOFactory.createDummyRandomUser());
        assertEquals(2, roleUser.getUsers().size());
        roleUser.clearUsers();
        assertTrue(roleUser.getUsers().isEmpty());
    }

    @Test
    public void equals() {
        RoleEO roleUser = EOFactory.createDummyRoleUser();
        Object object = new Object();
        assertNotEquals(roleUser, object);
        assertEquals(roleUser, roleUser);
    }

    @Test
    public void equals1() {
        RoleEO roleUser = EOFactory.createDummyRoleUser();
        assertEquals(roleUser, roleUser);
        RoleEO roleAdmin = EOFactory.createDummyRoleAdmin();
        assertNotEquals(roleAdmin, roleUser);
    }
}