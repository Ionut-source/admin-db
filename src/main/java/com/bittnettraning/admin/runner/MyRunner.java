package com.bittnettraning.admin.runner;


import com.bittnettraning.admin.entities.Course;
import com.bittnettraning.admin.entities.Student;
import com.bittnettraning.admin.entities.Trainer;
import com.bittnettraning.admin.entities.User;
import com.bittnettraning.admin.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner{

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TrainerService trainerService;

    public static final String ADMIN = "Admin";
    public static final String TRAINER = "Trainer";
    public static final String STUDENT = "Student";

    @Override
    public void run(String... args) throws Exception {

        User user1 = userService.createUser("user1@gmail.com", "1234");
        User user2 = userService.createUser("user2@yahoo.com", "2345");

        roleService.createRole(ADMIN);
        roleService.createRole(TRAINER);
        roleService.createRole(STUDENT);

        userService.assignRoleToUser(user1.getEmail(), ADMIN);
        userService.assignRoleToUser(user1.getEmail(), TRAINER);
        userService.assignRoleToUser(user2.getEmail(), STUDENT);

        Trainer adrianAnghel = trainerService.createTrainer("Adrian", "Anghel",
                "Experienced trainer", "adrianA@gmail.com", "1234");
        Trainer bogdanDumitru = trainerService.createTrainer("Bogdan", "Dumitru",
                "Beginner trainer", "bogdanDumi@yahoo.com", "3456");

        Student ionutCumpanasoiu = studentService.createStudent("Ionut", "Cumpanasoiu",
                "beginner","ionutC@gmail.com", "4567");
        Student razvanMarin = studentService.createStudent("Razvan", "Marin",
                "intermediate","razvanMar@yahoo.com", "5678");

        Course course1 = courseService.createCourse("Introduction in Java", "2 hours",
                "JDK and JRE", adrianAnghel.getTrainerId());
        Course course17 = courseService.createCourse("Hibernate and Spring", "6 hours",
                "Unit and Integration Testing", bogdanDumitru.getTrainerId());

        courseService.assignStudentToCourse(course1.getCourseId(), ionutCumpanasoiu.getStudentId());
        courseService.assignStudentToCourse(course17.getCourseId(), razvanMarin.getStudentId());

    }
}
