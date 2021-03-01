/*

-- Query: SELECT * FROM conferencium_spring.users


LIMIT 0, 1000

-- Date: 2021-03-01 13:53
*/
USE conferencium_spring;
INSERT INTO `users` (`id`,`email`,`first_name`,`last_name`,`password`,`role`) VALUES (1,'speaker@gmail.com','F','S','$2a$11$Yad8.YXZ5P.fhxzOluB/NeE/ogv6kfP4SFpnbJGp/SVfZNJR7f/AG','SPEAKER');
INSERT INTO `users` (`id`,`email`,`first_name`,`last_name`,`password`,`role`) VALUES (2,'moderator@gmail.com','F','M','$2a$11$UGiuL2HMk7urq1fE8Yk65O9iId4hr/4p55Xnn6l3Eg.sUZPRuxEO6','MODERATOR');
INSERT INTO `users` (`id`,`email`,`first_name`,`last_name`,`password`,`role`) VALUES (3,'participant@gmail.com','F','P','$2a$11$aLpjzaffQaazx7JLJtWJ0u9P9p1W4JOpupE8lQTf8BpklTQubrFUC','PARTICIPANT');
