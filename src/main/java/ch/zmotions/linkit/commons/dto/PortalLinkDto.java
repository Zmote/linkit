package ch.zmotions.linkit.commons.dto;

import ch.zmotions.linkit.commons.types.PortalLinkType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PortalLinkDto {
    private UUID id;
    private String name;
    private String description;
    private String category;
    private String href;
    private String media;
    private PortalLinkType type;
    private String login;
    private String password;
    private UserDto owner;
    private List<UserDto> sharedUsers = new ArrayList<>();

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

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PortalLinkDto && equals((PortalLinkDto) o);
    }

    public boolean equals(PortalLinkDto dto) {
        return getId().equals(dto.getId());
    }

    public List<UserDto> getSharedUsers() {
        return sharedUsers;
    }

    public void setSharedUsers(List<UserDto> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
