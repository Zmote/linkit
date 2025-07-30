package ch.zmotions.linkit.commons.dto;

import ch.zmotions.linkit.config.DtoFactory;
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

public class UserDtoTest {

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
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertEquals(DtoFactory.knownUserId, userDto.getId());
    }

    @Test
    public void setId() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertEquals(DtoFactory.knownUserId, userDto.getId());
        UUID newUuid = UUID.randomUUID();
        userDto.setId(newUuid);
        assertEquals(newUuid, userDto.getId());
    }

    @Test
    public void getFirstname() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertEquals("Test", userDto.getFirstname());
    }

    @Test
    public void setFirstname() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertEquals("Test", userDto.getFirstname());
        userDto.setFirstname("Other");
        assertEquals("Other", userDto.getFirstname());
    }

    @Test
    public void getLastname() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertEquals("Test", userDto.getLastname());
    }

    @Test
    public void setLastname() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertEquals("Test", userDto.getLastname());
        userDto.setLastname("Other");
        assertEquals("Other", userDto.getLastname());
    }

    @Test
    public void getFullname() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertEquals("Test Test", userDto.getFullname());
        userDto.setFirstname("Other");
        assertEquals("Other Test", userDto.getFullname());
    }

    @Test
    public void getUsername() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertEquals("Test" + DtoFactory.knownUserId, userDto.getUsername());
    }

    @Test
    public void setUsername() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertEquals("Test" + DtoFactory.knownUserId, userDto.getUsername());
        userDto.setUsername("admin");
        assertEquals("admin", userDto.getUsername());
    }

    @Test
    public void getPassword() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertEquals("testpassword", userDto.getPassword());
    }

    @Test
    public void setPassword() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertEquals("testpassword", userDto.getPassword());
        userDto.setPassword("otherpassword");
        assertEquals("otherpassword", userDto.getPassword());
    }

    @Test
    public void getRoles() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertFalse(userDto.getRoles().isEmpty());
        assertEquals(2, userDto.getRoles().size());
    }

    @Test
    public void setRoles() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertFalse(userDto.getRoles().isEmpty());
        assertEquals(2, userDto.getRoles().size());
        userDto.getRoles().add(DtoFactory.createDummyRoleDtoUser());
        assertEquals(3, userDto.getRoles().size());
    }

    @Test
    public void equals() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        Object object = new Object();
        assertNotEquals(userDto, object);
    }

    @Test
    public void equals2() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        UserDto otherUserDto = DtoFactory.createRandomDummyUserDto();
        assertNotEquals(userDto, otherUserDto);
        assertEquals(userDto, userDto);
    }

    @Test
    public void isAdmin() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertTrue(userDto.isAdmin());
        userDto.setRoles(new ArrayList<RoleDto>() {{
            add(DtoFactory.createDummyRoleDtoUser());
        }});
        assertFalse(userDto.isAdmin());
    }

    @Test
    public void isNew() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertTrue(userDto.isNew());
    }

    @Test
    public void setNew() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertTrue(userDto.isNew());
        userDto.setNew(false);
        assertFalse(userDto.isNew());
    }

    @Test
    public void getEnabled() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertTrue(userDto.getEnabled());
    }

    @Test
    public void setEnabled() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertTrue(userDto.getEnabled());
        userDto.setEnabled(false);
        assertFalse(userDto.getEnabled());
    }

    @Test
    public void getAccountNonLocked() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertTrue(userDto.getAccountNonLocked());
    }

    @Test
    public void setAccountNonLocked() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        assertTrue(userDto.getAccountNonLocked());
        userDto.setAccountNonLocked(false);
        assertFalse(userDto.getAccountNonLocked());
    }

    @Test
    public void testBlankUsernameValidaiton() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        userDto.setUsername("     ");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testEmptyUsernameValidation() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        userDto.setUsername("");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testMinThreeCharacterLongUsernameValidation() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        userDto.setUsername("zd");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testBlankFirstnameValidaiton() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        userDto.setFirstname("     ");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testEmptyFirstnameValidation() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        userDto.setFirstname("");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testMinThreeCharacterLongFirstnameValidation() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        userDto.setFirstname("zd");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testBlankLastnameValidaiton() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        userDto.setLastname("     ");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testEmptyLastnameValidation() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        userDto.setLastname("");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testMinThreeCharacterLongLastnameValidation() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        userDto.setLastname("zd");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testPasswordMatchesPatternValidation() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        userDto.setPassword("root@SOMEPASS2019");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testPasswordDoesNotMatchPatternValidation() {
        UserDto userDto = DtoFactory.createDummyUserDto();
        userDto.setPassword("root");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
    }
}