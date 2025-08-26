package ch.zmotions.linkit.commons.dto;

import ch.zmotions.linkit.config.DtoFactory;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RestrictedUserDtoTest {
	private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        try(ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()){
            validator = validatorFactory.getValidator();
        }
    }


    @Test
    public void getFirstname() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        assertEquals("Test", restrictedUserDto.getFirstname());
    }

    @Test
    public void setFirstname() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        assertEquals("Test", restrictedUserDto.getFirstname());
        restrictedUserDto.setFirstname("Admin");
        assertEquals("Admin", restrictedUserDto.getFirstname());
    }

    @Test
    public void getLastname() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        assertEquals("Test", restrictedUserDto.getLastname());
    }

    @Test
    public void setLastname() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        assertEquals("Test", restrictedUserDto.getLastname());
        restrictedUserDto.setLastname("Admin");
        assertEquals("Admin", restrictedUserDto.getLastname());
    }

    @Test
    public void getUsername() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        assertEquals("Test" + DtoFactory.knownUserId, restrictedUserDto.getUsername());
    }

    @Test
    public void setUsername() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        assertEquals("Test" + DtoFactory.knownUserId, restrictedUserDto.getUsername());
        restrictedUserDto.setUsername("Admin");
        assertEquals("Admin", restrictedUserDto.getUsername());
    }

    @Test
    public void getPassword() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        assertEquals("testpassword", restrictedUserDto.getPassword());
    }

    @Test
    public void setPassword() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        assertEquals("testpassword", restrictedUserDto.getPassword());
        restrictedUserDto.setPassword("adminpassword");
        assertEquals("adminpassword", restrictedUserDto.getPassword());
    }

    @Test
    public void testBlankUsernameValidaiton() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        restrictedUserDto.setUsername("     ");
        Set<ConstraintViolation<RestrictedUserDto>> violations = validator.validate(restrictedUserDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testEmptyUsernameValidation() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        restrictedUserDto.setUsername("");
        Set<ConstraintViolation<RestrictedUserDto>> violations = validator.validate(restrictedUserDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testMinThreeCharacterLongUsernameValidation() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        restrictedUserDto.setUsername("zd");
        Set<ConstraintViolation<RestrictedUserDto>> violations = validator.validate(restrictedUserDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testBlankFirstnameValidaiton() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        restrictedUserDto.setFirstname("     ");
        Set<ConstraintViolation<RestrictedUserDto>> violations = validator.validate(restrictedUserDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testEmptyFirstnameValidation() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        restrictedUserDto.setFirstname("");
        Set<ConstraintViolation<RestrictedUserDto>> violations = validator.validate(restrictedUserDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testMinThreeCharacterLongFirstnameValidation() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        restrictedUserDto.setFirstname("zd");
        Set<ConstraintViolation<RestrictedUserDto>> violations = validator.validate(restrictedUserDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testBlankLastnameValidaiton() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        restrictedUserDto.setLastname("     ");
        Set<ConstraintViolation<RestrictedUserDto>> violations = validator.validate(restrictedUserDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testEmptyLastnameValidation() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        restrictedUserDto.setLastname("");
        Set<ConstraintViolation<RestrictedUserDto>> violations = validator.validate(restrictedUserDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testMinThreeCharacterLongLastnameValidation() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        restrictedUserDto.setLastname("zd");
        Set<ConstraintViolation<RestrictedUserDto>> violations = validator.validate(restrictedUserDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testPasswordMatchesPatternValidation() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        restrictedUserDto.setPassword("root@SOMEPASS2019");
        Set<ConstraintViolation<RestrictedUserDto>> violations = validator.validate(restrictedUserDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testPasswordDoesNotMatchPatternValidation() {
        RestrictedUserDto restrictedUserDto = DtoFactory.createDummyRestrictedUserDto();
        restrictedUserDto.setPassword("root");
        Set<ConstraintViolation<RestrictedUserDto>> violations = validator.validate(restrictedUserDto);
        assertFalse(violations.isEmpty());
    }
}