package ch.zmotions.linkit.service.domain;


import ch.zmotions.linkit.commons.types.PortalLinkType;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "PortalLinks")
public class PortalLinkEO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String category;

    @Column
    private String href;

    @Column
    private String media;

    @Column
    private PortalLinkType type;

    @Column
    private Boolean isPublic;

    @Column
    private String login;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name = "fk_owner")
    private UserEO owner;

    @ManyToMany(mappedBy = "sharedLinks")
    private List<UserEO> sharedUsers = new ArrayList<>();

    public PortalLinkEO() {
        setName("");
        setDescription("");
        setHref("");
        setMedia("");
        setType(PortalLinkType.WEB);
        setOwner(null);
        setLogin(null);
        setPassword(null);
    }

    public PortalLinkEO(UUID id, String name, String description, String href, String media, PortalLinkType type) {
        setId(id);
        setName(name);
        setDescription(description);
        setHref(href);
        setMedia(media);
        setType(type);
        setOwner(null);
        setLogin(null);
        setPassword(null);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public PortalLinkType getType() {
        return type;
    }

    public void setType(PortalLinkType type) {
        this.type = type;
    }

    public UserEO getOwner() {
        return owner;
    }

    public void setOwner(UserEO owner) {
        setPublic(owner == null);
        this.owner = owner;
    }

    public Boolean isPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public List<UserEO> getSharedUsers() {
        return sharedUsers;
    }

    public void setSharedUsers(List<UserEO> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }

    public boolean addSharedUser(UserEO user) {
        return sharedUsers.add(user);
    }

    public boolean removeSharedUser(UserEO user) {
        return sharedUsers.remove(user);
    }

    public void clearSharedUsers() {
        sharedUsers.clear();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PortalLinkEO && equals((PortalLinkEO) o);
    }

    public boolean equals(PortalLinkEO other) {
        return getId().equals(other.getId());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
