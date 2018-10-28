package com.xlscoder.repository;

import com.xlscoder.model.Key;
import com.xlscoder.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findById(Long id);
    Role findByName(String name);
}
