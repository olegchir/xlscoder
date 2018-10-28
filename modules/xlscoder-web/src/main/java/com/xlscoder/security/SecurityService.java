package com.xlscoder.security;

public interface SecurityService {
    void autologin(String username, String password);
    String findLoggedInUsername();
    org.springframework.security.core.userdetails.User findLoggedInUser();
}
