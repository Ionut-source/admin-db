package com.bittnettraning.admin.services;

import com.bittnettraning.admin.entities.Student;

import java.util.List;

public interface StudentService {

    Student findStudentById(Long studentId);

    List<Student> findStudentsByName(String name);

    Student findStudentByEmail(String email);

    Student createStudent(String firstName, String lastName, String level, String email, String password);

    Student updateStudent(Student student);

    List<Student> getAllStudents();

    void removeStudent(Long studentId);
}
