# admin-db

The project is structured on several layers, as follows:
1. Entity:  Course
            Trainer
            Role
            Student
            Customer
    In each class of the entity package, we added ORM annotations for the mapping between classes and database tables and properties.
    In addition to the class tables, we also have association tables that are generated in the database, for example:
• Between User and Role we have the userRole table
• Between Student and Course -> studentCourse

2. Repository (DAO)

All these interfaces extend JpaRepository which allows us to use all its methods such as findAll(), findById(), save(), create(), delete()..

3. Service & Service implementation

4. Security
I added formLogin() to force the user to authenticate
I also added forbidden access with the path "/403"

5. Web (Controller)

I added @PreAuthorize("hasAuthority('thisMethod')" method to protect resources.
To be able to use these annotations, I used the annotation @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnable = true) on the AdminApplication class.
This annotation was deprecated and was replaced with @EnableMethodSecurity.
And also antMatchers was replaced with requestMather.
The runner class is a class that implements CommandLineRunner.
This class contains the logic that helps us insert some initial data into our application.

Unit Tests
For the MVC application, I used UnitTests for all serviceimpl functionalities:
                             CourseServiceImplTest
                             TrainerServiceImplTest
                             RoleServiceImplTest
                             StudentServiceImplTest
                             UserServiceImplTest
Using JUnit and Mokito we can test all methods.

Integration Tests
I created the repositories package and tested all its interfaces using @DataJpaTest and Test Containers (MySqlContainer)
                         CourseRepositoryTest
                         TrainerRepositoryTest
                         RoleRepositoryTest
                         StudentRepositoryTest
                         UserRepositoryTest
