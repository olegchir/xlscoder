package com.xlscoder.security;

import com.xlscoder.model.Role;

import java.util.List;

public interface RoleService {
    Role findAdminRole();
    Role findConsumerRole();
    Role findProducerRole();
    Role findNoobRole();
    List<Role> findLoggedInRoles();
}
