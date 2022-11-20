package com.bittnettraning.admin.services.impl;

import com.bittnettraning.admin.entities.Course;
import com.bittnettraning.admin.entities.Trainer;
import com.bittnettraning.admin.entities.User;
import com.bittnettraning.admin.repositories.TrainerRepository;
import com.bittnettraning.admin.services.CourseService;
import com.bittnettraning.admin.services.TrainerService;
import com.bittnettraning.admin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;

    @Override
    public Trainer findTrainerById(Long trainerId) {
        return trainerRepository.findById(trainerId).orElseThrow(() -> new EntityNotFoundException(
                "Trainer with id " + trainerId + " Not Found"));
    }

    @Override
    public List<Trainer> findTrainerByName(String name) {
        return trainerRepository.findTrainerByName(name);
    }

    @Override
    public Trainer findTrainerByEmail(String email) {
        return trainerRepository.findTrainerByEmail(email);
    }

    @Override
    public Trainer createTrainer(String firstName, String lastName, String summary, String email, String password) {
        User user = userService.createUser(email, password);
        userService.assignRoleToUser(email, "Trainer");
        return trainerRepository.save(new Trainer(firstName, lastName, summary, user));
    }

    @Override
    public Trainer updateTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    @Override
    public List<Trainer> getTrainers() {
        return trainerRepository.findAll();
    }

    @Override
    public Trainer removeTrainer(Long trainerId) {
        Trainer trainer = findTrainerById(trainerId);
        for (Course course : trainer.getCourses()) {
            courseService.removeCourse(course.getCourseId());
        }
        trainerRepository.deleteById(trainerId);
        return trainer;
    }
}
