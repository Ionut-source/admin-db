package com.bittnettraning.admin.repositories;

import com.bittnettraning.admin.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName (String name);
}
