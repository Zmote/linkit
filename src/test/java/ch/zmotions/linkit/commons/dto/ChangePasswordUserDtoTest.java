package ch.zmotions.linkit.commons.dto;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.Assert.*;

public class ChangePasswordUserDtoTest {
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