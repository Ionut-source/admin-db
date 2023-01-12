package com.bittnettraning.admin.repositories;

import com.bittnettraning.admin.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {

    List<Course> findCoursesByCourseNameContains(String keyword);

    @Query(value = "SELECT * FROM courses as c WHERE c.course_id IN (SELECT e.course_id FROM enrolled_in AS e WHERE e.student_id=:studentId)", nativeQuery = true)
    List<Course> getCoursesByStudentId(@Param("studentId") Long studentId);

}