package ch.zmotions.linkit.service.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Users")
public class UserEO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Darf nicht aus Leerzeichen bestehen")
    @NotEmpty(message = "Darf nicht leer sein")
    @Length(min = 3, message = "Muss mindestens drei Charakter lang sein")
    @Column
    private String firstname;

    @NotBlank(message = "Darf nicht aus Leerzeichen bestehen")
    @NotEmpty(message = "Darf nicht leer sein")
    @Length(min = 3, message = "Muss mindestens drei Charakter lang sein")
    @Column
    private String lastname;

    @NotBlank(message = "Darf nicht aus Leerzeichen bestehen")
    @NotEmpty(message = "Darf nicht leer sein")
    @Length(min = 3, message = "Muss mindestens drei Charakter lang sein")
    @Column(unique = true)
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&./])[A-Za-z\\d@$!%*?.&/]{8,}$",
            message = "Das Passwort muss mindestens 8 Zeichen lang sein, ein Grosszeichen, " +
                    "ein Kleinzeichen, eine Zahl und einen Spezialcharakter (@$!%*?.&/) enthalten")
    @Column
    private String password;

    @Column
    private Boolean isNew = true;

    @Column
    private Boolean enabled = true;

    @Column
    private Boolean accountNonLocked = true;

    @Column
    private int loginAttempts = 0;

    @ManyToMany
    private List<RoleEO> roles = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<PortalLinkEO> myLinks = new ArrayList<>();

    @ManyToMany
    private List<PortalLinkEO> sharedLinks = new ArrayList<>();

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

    public List<RoleEO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEO> roles) {
        this.roles = roles;
    }

    public boolean addRole(RoleEO role) {
        return roles.add(role);
    }

    public boolean removeRole(RoleEO role) {
        return roles.remove(role);
    }

    public List<PortalLinkEO> getMyLinks() {
        return myLinks;
    }

    public void setMyLinks(List<PortalLinkEO> myLinks) {
        this.myLinks = myLinks;
    }

    public boolean addMyLink(PortalLinkEO portalLink) {
        return myLinks.add(portalLink);
    }

    public boolean removeMyLink(PortalLinkEO portalLink) {
        return myLinks.remove(portalLink);
    }

    public List<PortalLinkEO> getSharedLinks() {
        return sharedLinks;
    }

    public void setSharedLinks(List<PortalLinkEO> sharedLinks) {
        this.sharedLinks = sharedLinks;
    }

    public boolean addSharedLink(PortalLinkEO sharedLink) {
        return sharedLinks.add(sharedLink);
    }

    public boolean removeSharedLink(PortalLinkEO sharedLink) {
        return sharedLinks.remove(sharedLink);
    }

    public void clearRoles() {
        roles.clear();
    }

    public void clearSharedLinks() {
        sharedLinks.clear();
    }

    public void clearMyLinks() {
        myLinks.clear();
    }

    public Boolean isAdmin() {
        return getRoles() != null && getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof UserEO && equals((UserEO) o);
    }

    public boolean equals(UserEO other) {
        return getId().equals(other.getId());
    }

    public Boolean getNew() {
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

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }
}
