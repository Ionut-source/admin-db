package com.bittnettraning.admin.services;

import com.bittnettraning.admin.entities.Course;

import java.util.List;

public interface CourseService {


    Course findCourseById(Long courseID);

    Course createCourse(String courseName, String courseDuration, String courseDescription, Long trainerId);

    Course createOrUpdateCourse(Course course);

    List<Course> findCoursesByCourseName(String keyword);

    void assignStudentToCourse(Long courseID, Long studentId);

    List<Course> getAllCourses();

    List<Course> getCoursesForStudent(Long studentId);

    void removeCourse(Long courseId);
}
