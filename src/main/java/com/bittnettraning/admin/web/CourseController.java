package com.bittnettraning.admin.web;

import com.bittnettraning.admin.entities.Course;
import com.bittnettraning.admin.entities.Trainer;
import com.bittnettraning.admin.services.CourseService;
import com.bittnettraning.admin.services.TrainerService;
import com.bittnettraning.admin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static com.bittnettraning.admin.constants.Admin.Constants.*;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    @PreAuthorize("hasAuthority('Admin')")
    public String courses(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword) {
        List<Course> courses = courseService.findCoursesByCourseName(keyword);
        model.addAttribute(LIST_COURSES, courses);
        model.addAttribute(KEYWORD, keyword);
        return "course-views/courses";
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('Admin')")
    public String deleteCourse(Long courseId, String keyword) {
        courseService.removeCourse(courseId);
        return "redirect:/courses/index?keyword=" + keyword;
    }

    @GetMapping("/formUpdate")
    @PreAuthorize("hasAnyAuthority('Admin','Trainer')")
    public String updateCourse(Model model, Long courseId, Principal principal) {
        if (userService.doesCurrentUserHasRole(TRAINER)) {
            Trainer trainer = trainerService.findTrainerByEmail(principal.getName());
            model.addAttribute(CURRENT_TRAINER, trainer);
        }
        Course course = courseService.findCourseById(courseId);
        List<Trainer> trainers = trainerService.getTrainers();
        model.addAttribute(COURSE, course);
        model.addAttribute(LIST_TRAINERS, trainers);
        return "course-views/formUpdate";
    }

    @GetMapping("/formCreate")
    public String formCourses(Model model) {
        List<Trainer> trainers = trainerService.getTrainers();
        model.addAttribute(LIST_TRAINERS, trainers);
        model.addAttribute(COURSE, new Course());
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
        model.addAttribute(LIST_COURSES, subscribedCourses);
        model.addAttribute(OTHER_COURSES, otherCourses);
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
        model.addAttribute(LIST_COURSES, trainer.getCourses());
        return "course-views/trainer-courses";
    }

    @GetMapping("/trainer")
    public String coursesByTrainerId(Model model, Long trainerId) {
        Trainer trainer = trainerService.findTrainerById(trainerId);
        model.addAttribute(LIST_COURSES, trainer.getCourses());
        return "course-views/trainer-courses";
    }
}
