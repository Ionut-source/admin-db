package com.bittnettraning.admin.web;

import com.bittnettraning.admin.entities.Course;
import com.bittnettraning.admin.entities.Trainer;
import com.bittnettraning.admin.services.CourseService;
import com.bittnettraning.admin.services.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TrainerService trainerService;

    @GetMapping("/index")
    public String courses(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        List<Course> courses = courseService.findCoursesByCourseName(keyword);
        model.addAttribute("listCourses", courses);
        model.addAttribute("keyword", keyword);
        return "course-views/courses";
    }

    @GetMapping("/delete")
    public String deleteCourse(Long courseId, String keyword) {
        courseService.removeCourse(courseId);
        return "redirect:/courses/index?keyword=" + keyword;
    }

    @GetMapping("/formUpdate")
    public String updateCourse(Model model, long courseId) {
        Course course = courseService.findCourseById(courseId);
        List<Trainer> trainers = trainerService.getTrainers();
        model.addAttribute("course", course);
        model.addAttribute("listTrainers", trainers);
        return "course-views/formUpdate";
    }

    @GetMapping("/formCreate")
    public String formCourses(Model model) {
        List<Trainer> trainers = trainerService.getTrainers();
        model.addAttribute("listTrainers", trainers);
        model.addAttribute("course", new Course());
        return "course-views/formCreate";
    }

    @PostMapping("/save")
    public String save(Course course) {
        courseService.createOrUpdateCourse(course);
        return "redirect:/courses/index";
    }

    @GetMapping("/index/student")
    public String coursesForCurrentStudent(Model model) {
        Long studentId = 1L;
        List<Course> subscribedCourses = courseService.getCoursesForStudent(studentId);
        List<Course> otherCourses = courseService.getAllCourses().stream().filter(course ->
                !subscribedCourses.contains(course)).collect(Collectors.toList());
        model.addAttribute("listCourses", subscribedCourses);
        model.addAttribute("otherCourses", otherCourses);
        return "course-views/student-courses";
    }

    @GetMapping("/enrollStudent")
    public String enrollCurrentStudentInCourse(Long courseId) {
        Long studentId = 1L;
        courseService.assignStudentToCourse(courseId, studentId);
        return "redirect:/courses/index/student";
    }

    @GetMapping("/index/trainer")
    public String coursesForCurrentTrainer(Model model) {
        Long trainerId = 1L;
        Trainer trainer = trainerService.findTrainerById(trainerId);
        model.addAttribute("listCourses", trainer.getCourses());
        return "course-views/trainer-courses";
    }

    @GetMapping("/trainer")
    public String coursesByTrainerId(Model model, Long trainerId) {
        Trainer trainer = trainerService.findTrainerById(trainerId);
        model.addAttribute("listCourses", trainer.getCourses());
        return "course-views/trainer-courses";
    }
}
