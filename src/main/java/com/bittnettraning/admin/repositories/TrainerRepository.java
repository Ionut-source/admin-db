package com.bittnettraning.admin.repositories;

import com.bittnettraning.admin.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    @Query( value = "SELECT t FROM Trainer AS t WHERE t.firstName LIKE %:name% OR t.lastName LIKE %:name%")
    List<Trainer> findTrainerByName(@Param("name") String name);

    @Query(value = "SELECT t FROM Trainer AS t WHERE t.user.email=:email")
    Trainer findTrainerByEmail(@Param("email") String email);
}
