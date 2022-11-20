package com.bittnettraning.admin.services.impl;

import com.bittnettraning.admin.entities.Course;
import com.bittnettraning.admin.entities.Trainer;
import com.bittnettraning.admin.entities.User;
import com.bittnettraning.admin.repositories.TrainerRepository;
import com.bittnettraning.admin.services.CourseService;
import com.bittnettraning.admin.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private UserService userService;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Test
    void testFindTrainerById() {
        Trainer trainer = new Trainer();
        trainer.setTrainerId(1L);

        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));

        Trainer actualTrainer = trainerService.findTrainerById(1L);

        assertEquals(trainer, actualTrainer);

    }

    @Test
    void testFindTrainerByName() {
        String trainerName = "trainerFN";
        trainerService.findTrainerByName(trainerName);
        verify(trainerRepository).findTrainerByName(trainerName);

    }

    @Test
    void testFindTrainerByEmail() {
        String email = "trainerEmail@gmail.com";
        trainerService.findTrainerByEmail(email);
        verify(trainerRepository).findTrainerByEmail(email);
    }

    @Test
    void createTrainer() {
        User user = new User("user1@gmail.com", "test");

        when(userService.createUser(any(), any())).thenReturn(user);

        trainerService.createTrainer(
                "fName", "lName", "summary","user1@gmail.com", "test" );

        verify(trainerRepository).save(any());
     }

    @Test
    void testUpdateTrainer() {
        Trainer trainer = new Trainer();
        trainer.setTrainerId(1L);

        trainerService.updateTrainer(trainer);
        verify(trainerRepository).save(trainer);
    }

    @Test
    void testGetTrainers() {
        trainerService.getTrainers();
        verify(trainerRepository).findAll();
    }

    @Test
    void testRemoveTrainer() {
        Trainer trainer = new Trainer();
        trainer.setTrainerId(1L);
        Course course = new Course();
        course.setCourseId(1L);
        trainer.getCourses().add(course);

        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));

        trainerService.removeTrainer(1L);

        verify(courseService, times(1)).removeCourse(any());
        verify(trainerRepository).deleteById(any());
    }
}