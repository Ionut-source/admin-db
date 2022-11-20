INSERT INTO `courses` (`course_id`, `course_description`, `course_duration`, `course_name`, `trainer_id`) VALUES
(1, 'JDK and JRE', '2 hours', 'Introduction in Java', 1),
(2, 'Unit and Integration Testing', '6 hours', 'Hibernate and Spring', 2);

INSERT INTO `enrolled_in` (`course_id`, `student_id`) VALUES
(1, 1),
(2, 2);

INSERT INTO `roles` (`role_id`, `name`) VALUES
(1, 'Admin'),
(3, 'Student'),
(2, 'Trainer');

INSERT INTO `students` (`student_id`, `first_name`, `last_name`, `level`, `user_id`) VALUES
(1, 'Ionut', 'Cumpanasoiu', 'beginner', 5),
(2, 'Razvan', 'Marin', 'intermediate', 6);

INSERT INTO `trainers` (`trainer_id`, `first_name`, `last_name`, `summary`, `user_id`) VALUES
(1, 'Adrian', 'Anghel', 'Experienced trainer', 3),
(2, 'Bogdan', 'Dumitru', 'Beginner trainer', 4);

INSERT INTO `users` (`user_id`, `email`, `password`) VALUES
(1, 'user1@gmail.com', '1234'),
(2, 'user2@yahoo.com', '2345'),
(3, 'adrianA@gmail.com', '1234'),
(4, 'bogdanDumi@yahoo.com', '3456'),
(5, 'ionutC@gmail.com', '4567'),
(6, 'razvanMar@yahoo.com', '5678');

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(1, 2),
(3, 2),
(4, 2),
(2, 3),
(5, 3),
(6, 3);