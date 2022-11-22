package com.bittnettraning.admin.repositories;

import com.bittnettraning.admin.AbstractTest;
import com.bittnettraning.admin.entities.Course;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Sql(scripts = {"file:src/test/resources/db/clearAll.sql", "file:src/test/resources/db/admin-db.sql"})
class CourseRepositoryTest extends AbstractTest {

    private CourseRepository courseRepository;

    @Test
    void findCoursesByCourseNameContains() {
        List<Course> courses = courseRepository.findCoursesByCourseNameContains("Java");
        int expectedResult = 1;
        assertEquals(expectedResult, courses.size());
    }

    @Test
    void getCoursesByStudentId() {
        List<Course> courses = courseRepository.getCoursesByStudentId(1L);
        int expectedResult = 1;
        assertEquals(expectedResult, courses.size());
    }
}