-- Populate skill table
DELETE FROM `skill`;

INSERT INTO `skill` (`skill_name`) VALUES ('HTML');
INSERT INTO `skill` (`skill_name`) VALUES ('CSS');
INSERT INTO `skill` (`skill_name`) VALUES ('JavaScript');
INSERT INTO `skill` (`skill_name`) VALUES ('Java');
INSERT INTO `skill` (`skill_name`) VALUES ('Python');
INSERT INTO `skill` (`skill_name`) VALUES ('Ruby');
INSERT INTO `skill` (`skill_name`) VALUES ('PhP');
INSERT INTO `skill` (`skill_name`) VALUES ('C++');
INSERT INTO `skill` (`skill_name`) VALUES ('SQL');
INSERT INTO `skill` (`skill_name`) VALUES ('Wordpress');
INSERT INTO `skill` (`skill_name`) VALUES ('Drupal');
INSERT INTO `skill` (`skill_name`) VALUES ('SEO');
INSERT INTO `skill` (`skill_name`) VALUES ('UI/UX Design');
INSERT INTO `skill` (`skill_name`) VALUES ('Graphic Design');

-- Populate test data: user_skill
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (2,7);
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (2,8);
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (2,9);
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (2,10);
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (2,5);
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (2,6);
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (3,1);
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (3,3);
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (3,4);
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (3,5);
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (3,7);
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (3,9);
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (4,1);
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (4,2);
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (4,3);
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (4,4);
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (4,5);
INSERT INTO `user_skill` (`user_id`, `skill_id`) VALUES (4,6);