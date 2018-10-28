package com.xlscoder.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.xlscoder.model.Defaults.*;

@Entity
@Table(name = "\"Roles\"",
        uniqueConstraints = {
                @UniqueConstraint(name = "RoleID_UNIQUE", columnNames = {"RoleID"}),
                @UniqueConstraint(name = "RoleName_UNIQUE", columnNames = {"RoleName"}),
        })
public class Role {
    @Column(name="RoleID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="RoleName", nullable = false)
    private String name;

    @Column(name="RoleHumanReadableName", nullable = false)
    private String humanReadableName;

    @Column(name="Users")
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    //=========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getHumanReadableName() {
        return humanReadableName;
    }

    public void setHumanReadableName(String humanReadableName) {
        this.humanReadableName = humanReadableName;
    }
}