package ch.zmotions.linkit.commons.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class RestrictedUserDto {
    @NotBlank(message = "Darf nicht aus Leerzeichen bestehen")
    @NotEmpty(message = "Darf nicht leer sein")
    @Length(min = 3, message = "Muss mindestens drei Charakter lang sein")
    private String firstname;
    @NotBlank(message = "Darf nicht aus Leerzeichen bestehen")
    @NotEmpty(message = "Darf nicht leer sein")
    @Length(min = 3, message = "Muss mindestens drei Charakter lang sein")
    private String lastname;
    @NotBlank(message = "Darf nicht aus Leerzeichen bestehen")
    @NotEmpty(message = "Darf nicht leer sein")
    @Length(min = 3, message = "Muss mindestens drei Charakter lang sein")
    private String username;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&./])[A-Za-z\\d@$!%*?.&/]{8,}$",
            message = "Das Passwort muss mindestens 8 Zeichen lang sein, ein Grosszeichen, " +
                    "ein Kleinzeichen, eine Zahl und einen Spezialcharakter (@$!%*?.&/) enthalten")
    private String password;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
