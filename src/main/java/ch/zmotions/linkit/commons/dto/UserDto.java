package ch.zmotions.linkit.commons.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDto {
    private UUID id;

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
    private Boolean isNew;
    private Boolean enabled;
    private Boolean accountNonLocked;
    private List<RoleDto> roles = new ArrayList<>();

    public UserDto() {
    }

    public UserDto(RestrictedUserDto restrictedUser) {
        setUsername(restrictedUser.getUsername());
        setFirstname(restrictedUser.getFirstname());
        setLastname(restrictedUser.getLastname());
        setPassword(restrictedUser.getPassword());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public String getFullname() {
        return getFirstname() + " " + getLastname();
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

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof UserDto && equals((UserDto) o);
    }

    public boolean equals(UserDto dto) {
        return getId().equals(dto.getId());
    }

    public boolean isAdmin() {
        if (getRoles() != null) {
            return getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
        }
        return false;
    }

    public Boolean isNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
}
