package com.bittnettraning.admin.services.impl;

import com.bittnettraning.admin.entities.Course;
import com.bittnettraning.admin.entities.Trainer;
import com.bittnettraning.admin.repositories.CourseRepository;
import com.bittnettraning.admin.repositories.TrainerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void testFindCourseById() {
        Course course = new Course();
        course.setCourseId(1L);

        when(courseRepository.findById(any())).thenReturn(Optional.of(course));

        Course actualCourse = courseService.findCourseById(1L);

        assertEquals(course, actualCourse);

    }

    @Test
    void testExceptionForNotFoundCourseById() {
        assertThrows(EntityNotFoundException.class, () -> courseService.findCourseById(2L));
    }

    @Test
    void createCourse() {
        Trainer trainer = new Trainer();
        trainer.setTrainerId(1L);

        when(trainerRepository.findById(any())).thenReturn(Optional.of(trainer));

        courseService.createCourse("JPA", "1h 30 min", "Introduction in JPA", 1L);

        verify(courseRepository).save(any());
    }

    @Test
    void createOrUpdateCourse() {
        Course course = new Course();
        course.setCourseId(1L);

        courseService.createOrUpdateCourse(course);

        ArgumentCaptor<Course> argumentCaptor = ArgumentCaptor.forClass(Course.class);

        verify(courseRepository).save(argumentCaptor.capture());

        Course capturedValue = argumentCaptor.getValue();

        assertEquals(course, capturedValue);
    }

    @Test
    void findCoursesByCourseName() {
        String courseName = "JPA";
        courseService.findCoursesByCourseName(courseName);
    }

    @Test
    void assignStudentToCourse() {
    }

    @Test
    void getAllCourses() {
    }

    @Test
    void getCoursesForStudent() {
    }

    @Test
    void removeCourse() {
    }
}