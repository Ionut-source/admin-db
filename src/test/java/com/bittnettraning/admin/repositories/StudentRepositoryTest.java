package com.bittnettraning.admin.repositories;

import com.bittnettraning.admin.AbstractTest;
import com.bittnettraning.admin.entities.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Sql(scripts = {"file:src/test/resources/db/clearAll.sql", "file:src/test/resources/db/admin-db.sql"})
class StudentRepositoryTest extends AbstractTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void testFindStudentByName() {
        List<Student> students = studentRepository.findStudentByName("Marin");
        assertEquals(1,students.size());

    }

    @Test
    void testFindStudentByEmail() {
        Student expectedStudent = new Student();
        expectedStudent.setStudentId(1L);
        expectedStudent.setFirstName("Ionut");
        expectedStudent.setLastName("Cumpanasoiu");
        expectedStudent.setLevel("beginner");

        Student student = studentRepository.findStudentByEmail("ionutC@gmail.com");

        assertEquals(expectedStudent, student);
    }

    @Test
    void testFindStudentByNotExistingEmail() {
        Student student = studentRepository.findStudentByEmail("student@gmail.com");
        assertNull(student);
    }
}