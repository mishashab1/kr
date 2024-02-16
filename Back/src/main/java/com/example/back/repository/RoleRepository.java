package com.example.back.repository;

import com.example.back.model.Role;
import com.example.back.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);

    @Query(value = """
        select r from Role r join fetch r.users u\s
        where u.id = :id
    """)
    Optional<Role> findByUserId(Long id);
}
