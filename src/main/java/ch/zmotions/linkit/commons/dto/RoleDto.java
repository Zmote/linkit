package ch.zmotions.linkit.commons.dto;

import java.util.UUID;

public class RoleDto {
    private UUID id;
    private String name;

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

    @Override
    public boolean equals(Object o){
        return o instanceof RoleDto && equals((RoleDto) o);
    }

    public boolean equals(RoleDto dto){
        return getId().equals(dto.getId());
    }
}
