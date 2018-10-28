package com.xlscoder.repository;

import com.xlscoder.model.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends JpaRepository<Key, Long> {
    Key findById(Long id);
    Key findByKeyName(String id);
}
