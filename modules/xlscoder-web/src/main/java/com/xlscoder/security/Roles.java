package com.xlscoder.security;

public enum Roles {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_PRODUCER("ROLE_PRODUCER"),
    ROLE_CONSUMER("ROLE_CONSUMER"),
    ROLE_NOOB("ROLE_NOOB");

    private final String name;

    private Roles(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
