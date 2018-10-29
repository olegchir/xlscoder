package com.xlscoder.model;

import com.xlscoder.security.Roles;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.xlscoder.model.Defaults.*;

@Entity
@Table(name = "\"Users\"",
        uniqueConstraints = {
                @UniqueConstraint(name = "UserID_UNIQUE", columnNames = {"UserID"}),
                @UniqueConstraint(name = "UserEmail_UNIQUE", columnNames = {"UserEmail"}),
        })
public class User {
    @Column(name="UserID", length = 11)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="Login")
    private String login;

    @Column(name="Password")
    private String password;

    @Transient
    private String passwordConfirm;

    @Column(name="Roles")
    @ManyToMany
    @JoinTable(name = "User_Role", joinColumns = @JoinColumn(name = "UserID"), inverseJoinColumns = @JoinColumn(name = "RoleID"))
    private Set<Role> roles = new HashSet<>();

    @Column(name="Keys")
    @ManyToMany
    @JoinTable(name = "User_Key", joinColumns = @JoinColumn(name = "UserID"), inverseJoinColumns = @JoinColumn(name = "KeyID"))
    private Set<Key> keys = new HashSet<>();

    @Column(name = "UserName", length = DEFAULT_STRING_LENGTH, nullable = false)
    private String userName;

    @Column(name = "UserSurname", length = DEFAULT_STRING_LENGTH, nullable = false)
    private String userSurname;

    @Column(name = "UserEmail", length = DEFAULT_STRING_LENGTH, nullable = false)
    private String userEmail;

    public boolean isAdmin() {
        for (Role role: getRoles()) {
            if (role.getName().equals(Roles.ROLE_ADMIN.name())) {
                return true;
            }
        }
        return false;
    }

    //=========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Set<Key> getKeys() {
        return keys;
    }

    public void setKeys(Set<Key> keys) {
        this.keys = keys;
    }
}