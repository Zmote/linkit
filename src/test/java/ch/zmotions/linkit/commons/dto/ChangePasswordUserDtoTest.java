package ch.zmotions.linkit.commons.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ChangePasswordUserDtoTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @Test
    public void getOldpassword() {
        ChangePasswordUserDto changePasswordUserDto = new ChangePasswordUserDto();
        assertNull(changePasswordUserDto.getOldpassword());
        changePasswordUserDto.setOldpassword("Hello");
        assertEquals("Hello", changePasswordUserDto.getOldpassword());
    }

    @Test
    public void setOldpassword() {
        ChangePasswordUserDto changePasswordUserDto = new ChangePasswordUserDto();
        changePasswordUserDto.setOldpassword("Hello");
        assertEquals("Hello", changePasswordUserDto.getOldpassword());
    }

    @Test
    public void getNewpassword() {
        ChangePasswordUserDto changePasswordUserDto = new ChangePasswordUserDto();
        assertNull(changePasswordUserDto.getNewpassword());
        changePasswordUserDto.setNewpassword("Hello");
        assertEquals("Hello", changePasswordUserDto.getNewpassword());
    }

    @Test
    public void setNewpassword() {
        ChangePasswordUserDto changePasswordUserDto = new ChangePasswordUserDto();
        changePasswordUserDto.setNewpassword("Hello");
        assertEquals("Hello", changePasswordUserDto.getNewpassword());
    }

    @Test
    public void testNewPasswordDoesNotFullfillRegexValidation() {
        ChangePasswordUserDto changePasswordUserDto = new ChangePasswordUserDto();
        changePasswordUserDto.setNewpassword("Hello");

        Set<ConstraintViolation<ChangePasswordUserDto>> violations = validator.validate(changePasswordUserDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNewPasswordDoesFullfillRegexValidation() {
        ChangePasswordUserDto changePasswordUserDto = new ChangePasswordUserDto();
        changePasswordUserDto.setNewpassword("root@SOMEPASS2019");

        Set<ConstraintViolation<ChangePasswordUserDto>> violations = validator.validate(changePasswordUserDto);
        assertTrue(violations.isEmpty());
    }
}