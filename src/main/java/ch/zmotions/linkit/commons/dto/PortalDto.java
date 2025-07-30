package ch.zmotions.linkit.commons.dto;

import java.util.UUID;

public class PortalDto {
    private UUID id;
    private String name;
    private String copyright;
    private String customCss;
    private int maxLoginAttempts;
    private String blockedList;

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

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getCustomCss() {
        return customCss;
    }

    public void setCustomCss(String customCss) {
        this.customCss = customCss;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PortalDto && equals((PortalDto) o);
    }

    public boolean equals(PortalDto dto) {
        return getId().equals(dto.getId());
    }

    public String getBlockedList() {
        return blockedList;
    }

    public void setBlockedList(String blockedList) {
        this.blockedList = blockedList;
    }

    public int getMaxLoginAttempts() {
        return maxLoginAttempts;
    }

    public void setMaxLoginAttempts(int maxLoginAttempts) {
        this.maxLoginAttempts = maxLoginAttempts;
    }
}
