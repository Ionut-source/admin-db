package com.bittnettraning.admin.web;

import com.bittnettraning.admin.entities.Course;
import com.bittnettraning.admin.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/index")
    public String courses(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        List<Course> courses = courseService.findCoursesByCourseName(keyword);
        model.addAttribute("listCourses", courses);
        model.addAttribute("keyword", keyword);
        return "course-views/courses";
    }
}
