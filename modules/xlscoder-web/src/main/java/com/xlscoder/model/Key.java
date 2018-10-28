package com.xlscoder.model;

import com.xlscoder.coder.KeyPairHolder;

import javax.persistence.*;

import static com.xlscoder.model.Defaults.*;

@Entity
@Table(name = "\"Keys\"",
        uniqueConstraints = {
                @UniqueConstraint(name = "KeyID_UNIQUE", columnNames = {"KeyID"}),
                @UniqueConstraint(name = "KeyName_UNIQUE", columnNames = {"KeyName"}),
                @UniqueConstraint(name = "UserID_UNIQUE", columnNames = {"UserID"}),
        })
public class Key {
    @Column(name="KeyID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "UserID", nullable = false, foreignKey=@ForeignKey(name="FK_UserID"))
    private User user;

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

    public String getShortPrivateKey() {
        return KeyPairHolder.firstNOfKey(privateKey, 50);
    }

    public String getShortPublicKey() {
        return KeyPairHolder.firstNOfKey(publicKey, 50);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}