-- Fix FK by refencing project table instead of user table
DROP TABLE project_skill; 

CREATE TABLE project_skill (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  project_id INT NOT NULL,
  skill_id INT NOT NULL,
  display_order INT NOT NULL DEFAULT '0',
  created_time TIMESTAMP NOT NULL DEFAULT '2017-001-01 00:00:00',
  updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (project_id) REFERENCES project (id),
  FOREIGN KEY (skill_id) REFERENCES skill (id)
);

INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (1,1,1,0,'2016-02-08 22:25:44','2016-09-12 07:05:51');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (2,2,2,0,'2016-01-12 21:48:21','2016-10-31 10:18:32');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (3,3,3,0,'2016-05-23 01:15:53','2016-10-11 08:46:58');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (4,4,4,0,'2016-04-23 19:31:07','2016-08-27 10:38:42');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (5,5,5,0,'2016-02-15 17:52:22','2016-08-05 12:20:26');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (6,6,6,0,'2016-01-18 09:12:16','2016-09-23 18:38:10');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (7,7,7,0,'2016-02-12 06:57:30','2016-08-27 16:28:50');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (8,8,8,0,'2016-04-20 01:48:45','2016-07-02 18:56:12');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (9,9,9,0,'2016-05-27 15:37:35','2016-11-18 15:24:14');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (10,10,10,0,'2016-05-22 11:15:48','2016-12-03 04:04:13');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (11,11,11,0,'2016-06-09 14:01:48','2016-12-23 06:03:25');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (12,12,12,0,'2016-04-03 22:39:15','2016-09-09 19:57:30');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (13,13,13,0,'2016-06-23 17:18:52','2016-08-09 22:22:30');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (14,14,14,0,'2016-04-27 07:21:29','2016-12-19 12:21:09');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (15,15,15,0,'2016-06-18 06:58:19','2016-12-04 18:59:01');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (16,16,16,0,'2016-03-29 11:11:03','2016-11-22 05:37:44');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (17,17,17,0,'2016-03-30 18:08:03','2016-07-22 02:33:22');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (18,18,18,0,'2016-03-20 01:48:39','2016-07-01 17:18:38');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (19,19,19,0,'2016-04-23 16:30:47','2016-12-27 23:30:29');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (20,20,20,0,'2016-03-25 00:01:37','2016-11-12 20:28:07');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (21,21,21,0,'2016-03-08 00:22:38','2016-10-09 06:40:09');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (22,22,22,0,'2016-06-10 04:06:17','2016-09-30 17:42:29');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (23,23,23,0,'2016-06-08 11:11:33','2016-11-15 14:23:52');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (24,24,24,0,'2016-02-08 01:14:39','2016-12-20 09:54:23');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (25,25,25,0,'2016-06-26 09:15:16','2016-07-30 20:56:49');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (26,2,1,0,'2016-02-09 19:37:21','2016-11-28 02:53:08');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (27,1,2,0,'2016-01-29 13:30:09','2016-07-01 21:33:15');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (28,1,3,0,'2016-05-19 08:07:12','2016-07-22 16:16:34');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (29,8,4,0,'2016-04-04 06:43:15','2016-12-18 08:51:25');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (30,11,5,0,'2016-01-11 08:53:20','2016-09-22 05:58:28');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (31,12,6,0,'2016-06-03 23:54:09','2016-08-02 03:56:08');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (32,13,7,0,'2016-03-04 09:03:22','2016-08-24 08:54:38');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (33,14,8,0,'2016-06-28 07:04:14','2016-10-02 23:04:06');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (34,14,9,0,'2016-05-14 13:16:25','2016-11-05 09:39:03');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (35,14,10,0,'2016-05-31 14:09:42','2016-09-30 09:55:55');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (36,15,11,0,'2016-06-19 14:36:52','2016-10-01 15:14:33');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (37,15,12,0,'2016-01-13 10:26:15','2016-11-13 19:05:02');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (38,20,13,0,'2016-06-07 16:09:37','2016-11-26 10:12:47');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (39,21,14,0,'2016-01-25 19:13:49','2016-12-19 22:45:09');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (40,22,15,0,'2016-06-16 09:20:43','2016-08-10 03:59:19');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (41,23,16,0,'2016-04-30 15:43:42','2016-07-27 00:26:53');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (42,24,17,0,'2016-06-03 22:51:48','2016-12-04 15:32:46');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (43,24,18,0,'2016-05-19 07:37:53','2016-11-24 15:25:54');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (44,24,19,0,'2016-03-25 01:29:28','2016-09-30 15:52:01');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (45,24,20,0,'2016-02-24 22:18:13','2016-12-12 13:29:19');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (46,24,21,0,'2016-03-07 22:50:00','2016-08-04 18:49:31');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (47,24,22,0,'2016-02-07 22:10:01','2016-09-27 17:34:56');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (48,24,23,0,'2016-04-25 04:17:05','2016-12-07 19:07:43');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (49,2,24,0,'2016-05-31 09:18:14','2016-07-03 07:06:43');
INSERT INTO `project_skill` (`id`,`project_id`,`skill_id`,`display_order`,`created_time`,`updated_time`) VALUES (50,2,25,0,'2016-04-03 11:27:14','2016-12-25 20:30:54');

