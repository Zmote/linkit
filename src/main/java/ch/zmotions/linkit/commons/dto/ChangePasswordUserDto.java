package ch.zmotions.linkit.commons.dto;

import jakarta.validation.constraints.Pattern;

public class ChangePasswordUserDto {
    private String oldpassword;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&./])[A-Za-z\\d@$!%*?.&/]{8,}$",
            message = "Das Passwort muss mindestens 8 Zeichen lang sein, ein Grosszeichen, " +
                    "ein Kleinzeichen, eine Zahl und einen Spezialcharakter (@$!%*?.&/) enthalten")
    private String newpassword;

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
}
