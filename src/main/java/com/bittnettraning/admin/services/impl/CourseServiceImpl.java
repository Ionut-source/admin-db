package com.bittnettraning.admin.services.impl;

import com.bittnettraning.admin.entities.Course;
import com.bittnettraning.admin.entities.Student;
import com.bittnettraning.admin.entities.Trainer;
import com.bittnettraning.admin.repositories.CourseRepository;
import com.bittnettraning.admin.repositories.StudentRepository;
import com.bittnettraning.admin.repositories.TrainerRepository;
import com.bittnettraning.admin.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Course findCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException(
                "Course with id" + courseId + "Not Found"));
    }

    @Override
    public Course createCourse(String courseName, String courseDuration, String courseDescription, Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(() -> new EntityNotFoundException(
                "Trainer with id " + trainerId + " Not Found"));
        return courseRepository.save(new Course(courseName, courseDuration, courseDescription, trainer));
    }

    @Override
    public Course createOrUpdateCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> findCoursesByCourseName(String keyword) {
        return courseRepository.findCoursesByCourseNameContains(keyword);
    }

    @Override
    public void assignStudentToCourse(Long courseID, Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException(
                "Student with id " + studentId + " Not Found"));
        Course course = courseRepository.findById(courseID).orElseThrow(() -> new EntityNotFoundException(
                "Course with id " + courseID + " Not Found"));
        course.assignStudentToCourse(student);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> getCoursesForStudent(Long studentId) {
        return courseRepository.getCoursesByStudentId(studentId);
    }

    @Override
    public void removeCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }
}
