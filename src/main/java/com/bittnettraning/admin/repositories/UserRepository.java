package com.bittnettraning.admin.repositories;

import com.bittnettraning.admin.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
