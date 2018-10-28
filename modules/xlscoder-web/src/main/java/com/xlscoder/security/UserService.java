package com.xlscoder.security;

import com.xlscoder.model.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
