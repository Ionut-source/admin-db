package com.bittnettraning.admin.web;

import com.bittnettraning.admin.entities.Student;
import com.bittnettraning.admin.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.bittnettraning.admin.constants.Admin.Constants.KEYWORD;
import static com.bittnettraning.admin.constants.Admin.Constants.LIST_STUDENTS;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping("/index")
    public String students(Model model,@RequestParam(value = KEYWORD, defaultValue = "") String keyword){
        List<Student> students = studentService.findStudentsByName(keyword);
        model.addAttribute(LIST_STUDENTS, students);
        model.addAttribute(KEYWORD, keyword);
        return "student-views/students";
    }
}
