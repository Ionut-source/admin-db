package com.bittnettraning.admin.web;

import com.bittnettraning.admin.entities.Student;
import com.bittnettraning.admin.entities.User;
import com.bittnettraning.admin.services.StudentService;
import com.bittnettraning.admin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

import static com.bittnettraning.admin.constants.Admin.Constants.KEYWORD;
import static com.bittnettraning.admin.constants.Admin.Constants.LIST_STUDENTS;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String students(Model model, @RequestParam(value = KEYWORD, defaultValue = "") String keyword) {
        List<Student> students = studentService.findStudentsByName(keyword);
        model.addAttribute(LIST_STUDENTS, students);
        model.addAttribute(KEYWORD, keyword);
        return "student-views/students";
    }

    @GetMapping("/delete")
    public String deleteStudent(Long studentId, String keyword) {
        studentService.removeStudent(studentId);
        return "redirect:/students/index?keyword=" + keyword;
    }

    @GetMapping("/formUpdate")
    public String updateStudent(Model model, Long studentId) {
        Student student = studentService.findStudentById(studentId);
        model.addAttribute("student", student);
        return "student-views/formUpdate";
    }

    @PostMapping("/update")
    public String update(Student student) {
        studentService.updateStudent(student);
        return "redirect:/students/index";
    }

    @GetMapping("/formCreate")
    public String formStudent(Model model) {
        model.addAttribute("student", new Student());
        return "student-views/formCreate";
    }

    @PostMapping("/save")
    public String save(@Valid Student student, BindingResult bindingResult) {
        User user = userService.findUserByEmail(student.getUser().getEmail());
        if (user != null) bindingResult.rejectValue
                ("user.email", null, "There is already an account registered with this email!");
        if (bindingResult.hasErrors()) return "student-views/formCreate";
        studentService.createStudent(student.getFirstName(), student.getLastName(), student.getLevel(),
                student.getUser().getEmail(), student.getUser().getPassword());
        return "redirect:/students/index";
    }
}
