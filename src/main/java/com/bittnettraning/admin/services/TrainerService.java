package com.bittnettraning.admin.services;

import com.bittnettraning.admin.entities.Trainer;

import java.util.List;

public interface TrainerService {

    Trainer findTrainerById(Long trainerId);

    List<Trainer> findTrainerByName(String name);

    Trainer findTrainerByEmail(String email);

    Trainer createTrainer(String firstName, String lastName, String summary, String email, String password);

    Trainer updateTrainer(Trainer trainer);

    List<Trainer> getTrainers();

    void removeTrainer(Long trainerId);
}
