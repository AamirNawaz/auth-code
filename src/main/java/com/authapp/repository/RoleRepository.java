package com.authapp.repository;

import com.authapp.model.Customer;
import com.authapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(Role roleName);
}
