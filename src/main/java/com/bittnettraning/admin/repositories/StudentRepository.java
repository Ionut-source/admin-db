package com.bittnettraning.admin.repositories;

import com.bittnettraning.admin.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT s FROM Student AS s WHERE s.firstName LIKE %:name% OR s.lastName LIKE %:name%")
    List<Student> findStudentByName (@Param("name")String name);

    @Query(value = "SELECT s FROM Student AS s WHERE s.user.email=:email")
    Student findStudentByEmail (@Param("email") String email);

}
