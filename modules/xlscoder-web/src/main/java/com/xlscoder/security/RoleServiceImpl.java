package com.xlscoder.security;

import com.xlscoder.model.Role;
import com.xlscoder.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private SecurityService securityService;

    @Override
    public Role findAdminRole() {
        return roleRepository.findByName(Roles.ROLE_ADMIN.toString());
    }

    @Override
    public Role findConsumerRole() {
        return roleRepository.findByName(Roles.ROLE_CONSUMER.toString());
    }

    @Override
    public Role findProducerRole() {
        return roleRepository.findByName(Roles.ROLE_PRODUCER.toString());
    }

    @Override
    public Role findNoobRole() {
        return roleRepository.findByName(Roles.ROLE_NOOB.toString());
    }

    @Override
    public List<Role> findLoggedInRoles() {
        List<Role> result = new ArrayList<>();
        boolean first = true;
        for (GrantedAuthority authority : securityService.findLoggedInUser().getAuthorities()) {
            String roleName = authority.getAuthority();
            Role role = roleRepository.findByName(roleName);
            result.add(role);
        }
        return result;
    }
}
