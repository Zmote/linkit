package ch.zmotions.linkit.service.domain;

import ch.zmotions.linkit.config.EOFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.*;

public class UserEOTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterClass
    public static void close() {
        validatorFactory.close();
    }

    @Test
    public void getId() {
        UserEO user = EOFactory.createDummyUser();
        assertEquals(EOFactory.knownUserId, user.getId());
    }

    @Test
    public void setId() {
        UserEO user = EOFactory.createDummyUser();
        assertEquals(EOFactory.knownUserId, user.getId());
        UUID newUuid = UUID.randomUUID();
        user.setId(newUuid);
        assertEquals(newUuid, user.getId());
    }

    @Test
    public void getFirstname() {
        UserEO user = EOFactory.createDummyUser();
        assertEquals("Test", user.getFirstname());
    }

    @Test
    public void setFirstname() {
        UserEO user = EOFactory.createDummyUser();
        assertEquals("Test", user.getFirstname());
        user.setFirstname("Other");
        assertEquals("Other", user.getFirstname());
    }

    @Test
    public void getLastname() {
        UserEO user = EOFactory.createDummyUser();
        assertEquals("Test", user.getLastname());
    }

    @Test
    public void setLastname() {
        UserEO user = EOFactory.createDummyUser();
        assertEquals("Test", user.getLastname());
        user.setLastname("Other");
        assertEquals("Other", user.getLastname());
    }

    @Test
    public void getUsername() {
        UserEO user = EOFactory.createDummyUser();
        assertEquals("Test", user.getUsername());
    }

    @Test
    public void setUsername() {
        UserEO user = EOFactory.createDummyUser();
        assertEquals("Test", user.getUsername());
        user.setUsername("Other");
        assertEquals("Other", user.getUsername());
    }

    @Test
    public void getPassword() {
        UserEO user = EOFactory.createDummyUser();
        assertEquals("testpassword", user.getPassword());
    }

    @Test
    public void setPassword() {
        UserEO user = EOFactory.createDummyUser();
        assertEquals("testpassword", user.getPassword());
        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    public void getRoles() {
        UserEO user = EOFactory.createDummyUser();
        assertEquals(2, user.getRoles().size());
    }

    @Test
    public void setRoles() {
        UserEO user = EOFactory.createDummyUser();
        assertEquals(2, user.getRoles().size());
        user.setRoles(new ArrayList<RoleEO>() {{
            add(EOFactory.createDummyRoleUser());
        }});
        assertEquals(1, user.getRoles().size());
    }

    @Test
    public void addRole() {
        UserEO user = EOFactory.createDummyUser();
        assertEquals(2, user.getRoles().size());
        user.addRole(EOFactory.createDummyRoleUser());
        assertEquals(3, user.getRoles().size());
    }

    @Test
    public void removeRole() {
        UserEO user = EOFactory.createDummyUser();
        assertEquals(2, user.getRoles().size());
        RoleEO role = EOFactory.createDummyRoleUser();
        user.addRole(role);
        assertEquals(3, user.getRoles().size());
        user.removeRole(role);
        assertEquals(2, user.getRoles().size());
    }

    @Test
    public void getMyLinks() {
        UserEO user = EOFactory.createDummyUser();
        assertTrue(user.getMyLinks().isEmpty());
    }

    @Test
    public void setMyLinks() {
        UserEO user = EOFactory.createDummyUser();
        assertTrue(user.getMyLinks().isEmpty());
        PortalLinkEO myLink = EOFactory.createDummyPortalLink();
        myLink.setOwner(user);
        user.setMyLinks(new ArrayList<PortalLinkEO>() {{
            add(myLink);
        }});
        assertEquals(1, user.getMyLinks().size());
    }

    @Test
    public void addMyLink() {
        UserEO user = EOFactory.createDummyUser();
        assertTrue(user.getMyLinks().isEmpty());
        PortalLinkEO myLink = EOFactory.createDummyPortalLink();
        myLink.setOwner(user);
        user.addMyLink(myLink);
        assertEquals(1, user.getMyLinks().size());
    }

    @Test
    public void removeMyLink() {
        UserEO user = EOFactory.createDummyUser();
        assertTrue(user.getMyLinks().isEmpty());
        PortalLinkEO myLink = EOFactory.createDummyPortalLink();
        myLink.setOwner(user);
        user.addMyLink(myLink);
        assertEquals(1, user.getMyLinks().size());
        user.removeMyLink(myLink);
        assertTrue(user.getMyLinks().isEmpty());
    }

    @Test
    public void getSharedLinks() {
        UserEO user = EOFactory.createDummyUser();
        assertTrue(user.getSharedLinks().isEmpty());
    }

    @Test
    public void setSharedLinks() {
        UserEO user = EOFactory.createDummyUser();
        assertTrue(user.getSharedLinks().isEmpty());
        PortalLinkEO sharedLink = EOFactory.createDummyPortalLink();
        sharedLink.setOwner(EOFactory.createDummyRandomUser());
        user.setSharedLinks(new ArrayList<PortalLinkEO>() {{
            add(sharedLink);
        }});
        assertEquals(1, user.getSharedLinks().size());
    }

    @Test
    public void addSharedLink() {
        UserEO user = EOFactory.createDummyUser();
        assertTrue(user.getSharedLinks().isEmpty());
        PortalLinkEO sharedLink = EOFactory.createDummyPortalLink();
        sharedLink.setOwner(EOFactory.createDummyRandomUser());
        user.addSharedLink(sharedLink);
        assertEquals(1, user.getSharedLinks().size());
    }

    @Test
    public void removeSharedLink() {
        UserEO user = EOFactory.createDummyUser();
        assertTrue(user.getSharedLinks().isEmpty());
        PortalLinkEO sharedLink = EOFactory.createDummyPortalLink();
        sharedLink.setOwner(EOFactory.createDummyRandomUser());
        user.addSharedLink(sharedLink);
        assertEquals(1, user.getSharedLinks().size());
        user.removeSharedLink(sharedLink);
        assertTrue(user.getSharedLinks().isEmpty());
    }

    @Test
    public void clearRoles() {
        UserEO user = EOFactory.createDummyUser();
        assertEquals(2, user.getRoles().size());
        user.addRole(EOFactory.createDummyRoleUser());
        assertEquals(3, user.getRoles().size());
        user.clearRoles();
        assertTrue(user.getRoles().isEmpty());
    }

    @Test
    public void clearSharedLinks() {
        UserEO user = EOFactory.createDummyUser();
        assertTrue(user.getSharedLinks().isEmpty());
        PortalLinkEO sharedLink = EOFactory.createDummyPortalLink();
        sharedLink.setOwner(EOFactory.createDummyRandomUser());
        user.addSharedLink(sharedLink);
        user.addSharedLink(sharedLink);
        assertEquals(2, user.getSharedLinks().size());
        user.clearSharedLinks();
        assertTrue(user.getSharedLinks().isEmpty());
    }

    @Test
    public void clearMyLinks() {
        UserEO user = EOFactory.createDummyUser();
        assertTrue(user.getMyLinks().isEmpty());
        PortalLinkEO myLink = EOFactory.createDummyPortalLink();
        myLink.setOwner(user);
        user.addMyLink(myLink);
        user.addMyLink(myLink);
        assertEquals(2, user.getMyLinks().size());
        user.clearMyLinks();
        assertTrue(user.getMyLinks().isEmpty());
    }

    @Test
    public void isAdmin() {
        UserEO user = EOFactory.createDummyUser();
        assertTrue(user.isAdmin());
        user.clearRoles();
        assertFalse(user.isAdmin());
    }

    @Test
    public void equals() {
    }

    @Test
    public void equals2() {
    }

    @Test
    public void getNew() {
        UserEO user = EOFactory.createDummyUser();
        assertTrue(user.getNew());
    }

    @Test
    public void setNew() {
        UserEO user = EOFactory.createDummyUser();
        assertTrue(user.getNew());
        user.setNew(false);
        assertFalse(user.getNew());
    }

    @Test
    public void getEnabled() {
        UserEO user = EOFactory.createDummyUser();
        assertTrue(user.getEnabled());
    }

    @Test
    public void setEnabled() {
        UserEO user = EOFactory.createDummyUser();
        assertTrue(user.getEnabled());
        user.setEnabled(false);
        assertFalse(user.getEnabled());
    }

    @Test
    public void getAccountNonLocked() {
        UserEO user = EOFactory.createDummyUser();
        assertTrue(user.getAccountNonLocked());
    }

    @Test
    public void setAccountNonLocked() {
        UserEO user = EOFactory.createDummyUser();
        assertTrue(user.getAccountNonLocked());
        user.setAccountNonLocked(false);
        assertFalse(user.getAccountNonLocked());
    }

    @Test
    public void getLoginAttempts() {
        UserEO user = EOFactory.createDummyUser();
        assertEquals(0, user.getLoginAttempts());
    }

    @Test
    public void setLoginAttempts() {
        UserEO user = EOFactory.createDummyUser();
        assertEquals(0, user.getLoginAttempts());
        user.setLoginAttempts(3);
        assertEquals(3, user.getLoginAttempts());
    }

    @Test
    public void testBlankUsernameValidaiton() {
        UserEO user = EOFactory.createDummyUser();
        user.setUsername("     ");
        Set<ConstraintViolation<UserEO>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testEmptyUsernameValidation() {
        UserEO user = EOFactory.createDummyUser();
        user.setUsername("");
        Set<ConstraintViolation<UserEO>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testMinThreeCharacterLongUsernameValidation() {
        UserEO user = EOFactory.createDummyUser();
        user.setUsername("zd");
        Set<ConstraintViolation<UserEO>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testBlankFirstnameValidaiton() {
        UserEO user = EOFactory.createDummyUser();
        user.setFirstname("     ");
        Set<ConstraintViolation<UserEO>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testEmptyFirstnameValidation() {
        UserEO user = EOFactory.createDummyUser();
        user.setFirstname("");
        Set<ConstraintViolation<UserEO>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testMinThreeCharacterLongFirstnameValidation() {
        UserEO user = EOFactory.createDummyUser();
        user.setFirstname("zd");
        Set<ConstraintViolation<UserEO>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testBlankLastnameValidaiton() {
        UserEO user = EOFactory.createDummyUser();
        user.setLastname("     ");
        Set<ConstraintViolation<UserEO>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testEmptyLastnameValidation() {
        UserEO user = EOFactory.createDummyUser();
        user.setLastname("");
        Set<ConstraintViolation<UserEO>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testMinThreeCharacterLongLastnameValidation() {
        UserEO user = EOFactory.createDummyUser();
        user.setLastname("zd");
        Set<ConstraintViolation<UserEO>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testPasswordMatchesPatternValidation() {
        UserEO user = EOFactory.createDummyUser();
        user.setPassword("root@SOMEPASS2019");
        Set<ConstraintViolation<UserEO>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testPasswordDoesNotMatchPatternValidation() {
        UserEO user = EOFactory.createDummyUser();
        user.setPassword("root");
        Set<ConstraintViolation<UserEO>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }
}