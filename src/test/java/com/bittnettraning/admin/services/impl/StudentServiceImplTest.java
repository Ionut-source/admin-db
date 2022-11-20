package com.bittnettraning.admin.services.impl;

import com.bittnettraning.admin.entities.Course;
import com.bittnettraning.admin.entities.Student;
import com.bittnettraning.admin.entities.User;
import com.bittnettraning.admin.repositories.StudentRepository;
import com.bittnettraning.admin.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private UserService userService;
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void testFindStudentById() {
        Student student = new Student();
        student.setStudentId(1L);

        when(studentRepository.findById(any())).thenReturn(Optional.of(student));

        Student actualStudent = studentService.findStudentById(1L);

        assertEquals(student, actualStudent);
    }

    @Test
    void testExceptionForNotFoundUserById() {
        assertThrows(EntityNotFoundException.class, () -> studentService.findStudentById(any()));
    }

    @Test
    void testFindStudentsByName() {
        String name = "name";
        studentService.findStudentsByName(name);
        verify(studentRepository).findStudentByName(name);
    }

    @Test
    void testFindStudentByEmail() {
        String email = "test@gmail.com";
        studentService.findStudentByEmail(email);
        verify(studentRepository).findStudentByEmail(email);
    }

    @Test
    void testCreateStudent() {
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("pass");

        when(userService.createUser(any(), any())).thenReturn(user);

        studentService.createStudent("FN", "LN", "master", "user@gmail.com", "pass");

        verify(studentRepository).save(any());
    }

    @Test
    void testUpdateStudent() {
        Student student = new Student();
        student.setStudentId(1L);

        studentService.updateStudent(student);

        verify(studentRepository).save(student);
    }

    @Test
    void testGetAllStudents() {
        studentService.getAllStudents();
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testRemoveStudent() {
        Student student = new Student();
        student.setStudentId(1L);

        Course course = new Course();
        course.setCourseId(1L);

        student.getCourses().add(course);

        when(studentRepository.findById(any())).thenReturn(Optional.of(student));

        studentService.removeStudent(1L);

        verify(studentRepository).deleteById(any());
    }
}