package com.bittnettraning.admin.web;

import com.bittnettraning.admin.entities.Student;
import com.bittnettraning.admin.entities.User;
import com.bittnettraning.admin.services.StudentService;
import com.bittnettraning.admin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.bittnettraning.admin.constants.Constants.KEYWORD;
import static com.bittnettraning.admin.constants.Constants.LIST_STUDENTS;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;

    @GetMapping("/index")
    @PreAuthorize("hasAuthority('Admin')")
    public String students(Model model, @RequestParam(value = KEYWORD, defaultValue = "") String keyword) {
        List<Student> students = studentService.findStudentsByName(keyword);
        model.addAttribute(LIST_STUDENTS, students);
        model.addAttribute(KEYWORD, keyword);
        return "student-views/students";
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('Admin')")
    public String deleteStudent(Long studentId, String keyword) {
        studentService.removeStudent(studentId);
        return "redirect:/students/index?keyword=" + keyword;
    }

    @GetMapping("/formUpdate")
    @PreAuthorize("hasAuthority('Student')")
    public String updateStudent(Model model, Principal principal) {
        Student student = studentService.findStudentByEmail(principal.getName());
        model.addAttribute("student", student);
        return "student-views/formUpdate";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('Student')")
    public String update(Student student) {
        studentService.updateStudent(student);
        return "redirect:/courses/index/student";
    }

    @GetMapping("/formCreate")
    @PreAuthorize("hasAuthority('Admin')")
    public String formStudent(Model model) {
        model.addAttribute("student", new Student());
        return "student-views/formCreate";
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('Admin')")
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
