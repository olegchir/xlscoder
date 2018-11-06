package com.xlscoder.model;

import com.xlscoder.coder.KeyPairHolder;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static com.xlscoder.model.Defaults.*;

@Entity
@Table(name = "\"Keys\"",
        uniqueConstraints = {
                @UniqueConstraint(name = "KeyID_UNIQUE", columnNames = {"KeyID"}),
                @UniqueConstraint(name = "KeyName_UNIQUE", columnNames = {"KeyName"}),
        })
public class Key {
    @Column(name="KeyID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="Users")
    @ManyToMany(mappedBy = "keys")
    private Set<User> users = new HashSet<>();

    @Column(name = "KeyName", length = DEFAULT_STRING_LENGTH, nullable = false)
    private String keyName;

    @Column(name = "PrivateKey", length = DEFAULT_KEY_LENGTH, nullable = false)
    private String privateKey;

    @Column(name = "PublicKey", length = DEFAULT_KEY_LENGTH, nullable = false)
    private String publicKey;

    @Column(name = "PGPPrivateKey", length = DEFAULT_KEY_LENGTH, nullable = false)
    private String pgpPrivateKey;

    @Column(name = "PGPPublicKey", length = DEFAULT_KEY_LENGTH, nullable = false)
    private String pgpPublicKey;

    @Column(name = "SHASalt", length = DEFAULT_KEY_LENGTH, nullable = false)
    private String shaSalt;

    @Column(name = "pgpPassword", length = DEFAULT_KEY_LENGTH, nullable = false)
    private String pgpPassword;

    @Column(name = "pgpIdentity", length = DEFAULT_KEY_LENGTH, nullable = false)
    private String pgpIdentity;

    public void associateUser(User user) {
        this.users.add(user);
        user.getKeys().add(this);
    }

    public void disassociateUser(User user) {
        this.users.add(user);
        user.getKeys().remove(this);
    }

    public void updateUserAssociations() {
        for (User user: this.users) {
            associateUser(user);
        }
    }

    public String getShortPrivateKey() {
        return KeyPairHolder.firstNOfKey(privateKey, 10);
    }

    public String getShortPublicKey() {
        return KeyPairHolder.firstNOfKey(publicKey, 10);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPgpPrivateKey() {
        return pgpPrivateKey;
    }

    public void setPgpPrivateKey(String pgpPrivateKey) {
        this.pgpPrivateKey = pgpPrivateKey;
    }

    public String getPgpPublicKey() {
        return pgpPublicKey;
    }

    public void setPgpPublicKey(String pgpPublicKey) {
        this.pgpPublicKey = pgpPublicKey;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getShaSalt() {
        return shaSalt;
    }

    public void setShaSalt(String shaSalt) {
        this.shaSalt = shaSalt;
    }

    public String getPgpPassword() {
        return pgpPassword;
    }

    public void setPgpPassword(String pgpPassword) {
        this.pgpPassword = pgpPassword;
    }

    public String getPgpIdentity() {
        return pgpIdentity;
    }

    public void setPgpIdentity(String pgpIdentity) {
        this.pgpIdentity = pgpIdentity;
    }
}
