package com.bittnettraning.admin.web;

import com.bittnettraning.admin.entities.Course;
import com.bittnettraning.admin.entities.Trainer;
import com.bittnettraning.admin.entities.User;
import com.bittnettraning.admin.services.CourseService;
import com.bittnettraning.admin.services.TrainerService;
import com.bittnettraning.admin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static com.bittnettraning.admin.constants.Constants.*;

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
    @PreAuthorize("hasAnyAuthority('Admin','Trainer')")
    public String formCourses(Model model, Principal principal) {
        if (userService.doesCurrentUserHasRole(TRAINER)) {
            Trainer trainer = trainerService.findTrainerByEmail(principal.getName());
            model.addAttribute(CURRENT_TRAINER, trainer);
        }
        List<Trainer> trainers = trainerService.getTrainers();
        model.addAttribute(LIST_TRAINERS, trainers);
        model.addAttribute(COURSE, new Course());
        return "course-views/formCreate";
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('Admin','Trainer')")
    public String save(Course course) {
        courseService.createOrUpdateCourse(course);
        return userService.doesCurrentUserHasRole(TRAINER) ? "redirect:/courses/index/trainer" : "redirect:/courses/index";
    }

    @GetMapping("/index/student")
    @PreAuthorize("hasAuthority('Student')")
    public String coursesForCurrentStudent(Model model, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        List<Course> subscribedCourses = courseService.getCoursesForStudent(user.getStudent().getStudentId());
        List<Course> otherCourses = courseService.getAllCourses().stream().filter(course ->
                !subscribedCourses.contains(course)).collect(Collectors.toList());
        model.addAttribute(LIST_COURSES, subscribedCourses);
        model.addAttribute(OTHER_COURSES, otherCourses);
        model.addAttribute(FIRST_NAME, user.getStudent().getFirstName());
        model.addAttribute(LAST_NAME, user.getStudent().getLastName());
        return "course-views/student-courses";
    }

    @GetMapping("/enrollStudent")
    @PreAuthorize("hasAuthority('Student')")
    public String enrollCurrentStudentInCourse(Long courseId, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        courseService.assignStudentToCourse(courseId, user.getStudent().getStudentId());
        return "redirect:/courses/index/student";
    }

    @GetMapping("/index/trainer")
    @PreAuthorize("hasAuthority('Trainer')")
    public String coursesForCurrentTrainer(Model model, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        Trainer trainer = trainerService.findTrainerById(user.getTrainer().getTrainerId());
        model.addAttribute(LIST_COURSES, trainer.getCourses());
        model.addAttribute(FIRST_NAME, trainer.getFirstName());
        model.addAttribute(LAST_NAME, trainer.getLastName());
        return "course-views/trainer-courses";
    }

    @GetMapping("/trainer")
    @PreAuthorize("hasAuthority('Admin')")
    public String coursesByTrainerId(Model model, Long trainerId) {
        Trainer trainer = trainerService.findTrainerById(trainerId);
        model.addAttribute(LIST_COURSES, trainer.getCourses());
        model.addAttribute(FIRST_NAME, trainer.getFirstName());
        model.addAttribute(LAST_NAME, trainer.getLastName());
        return "course-views/trainer-courses";
    }
}
