package com.bittnettraning.admin.services.impl;

import com.bittnettraning.admin.entities.Course;
import com.bittnettraning.admin.repositories.CourseRepository;
import com.bittnettraning.admin.repositories.TrainerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        assertThrows(EntityNotFoundException.class,() -> courseService.findCourseById(2L));
    }

    @Test
    void createCourse() {
    }

    @Test
    void createOrUpdateCourse() {
    }

    @Test
    void findCoursesByCourseName() {
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