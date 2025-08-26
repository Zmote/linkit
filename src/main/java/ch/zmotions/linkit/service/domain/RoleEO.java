package ch.zmotions.linkit.service.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Roles")
public class RoleEO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<UserEO> users = new ArrayList<>();

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

    public List<UserEO> getUsers() {
        return users;
    }

    public void setUsers(List<UserEO> users) {
        this.users = users;
    }

    public boolean addUser(UserEO user) {
        return users.add(user);
    }

    public boolean removeUser(UserEO user) {
        return users.remove(user);
    }

    public void clearUsers() {
        users.clear();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof RoleEO && equals((RoleEO) o);
    }

    public boolean equals(RoleEO role) {
        return getName().equals(role.getName());
    }
}
