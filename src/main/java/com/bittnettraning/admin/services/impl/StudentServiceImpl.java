package com.bittnettraning.admin.services.impl;

import com.bittnettraning.admin.entities.Course;
import com.bittnettraning.admin.entities.Student;
import com.bittnettraning.admin.entities.User;
import com.bittnettraning.admin.repositories.StudentRepository;
import com.bittnettraning.admin.services.StudentService;
import com.bittnettraning.admin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserService userService;

    @Override
    public Student findStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException(
                "Student with id " + studentId + " Not Found"));
    }

    @Override
    public List<Student> findStudentsByName(String name) {
        return studentRepository.findStudentByName(name);
    }

    @Override
    public Student findStudentByEmail(String email) {
        return studentRepository.findStudentByEmail(email);
    }

    @Override
    public Student createStudent(String firstName, String lastName, String level, String email, String password) {
        User user = userService.createUser(email, password);
        userService.assignRoleToUser(email, "Student");
        return studentRepository.save(new Student(firstName, lastName, level, user));
    }

    @Override
    public Student updateStudent(Student student) {
       return studentRepository.save(student);

    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void removeStudent(Long studentId) {
        Student student = findStudentById(studentId);
        Iterator<Course> iterator = student.getCourses().iterator();
        if (iterator.hasNext()) {
            Course course = iterator.next();
            course.removeStudentFromCourse(student);
        }
        studentRepository.deleteById(studentId);
    }
}
