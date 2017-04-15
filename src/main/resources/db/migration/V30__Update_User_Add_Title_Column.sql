--add new column
ALTER TABLE user ADD title varchar(100) ;
INSERT INTO `user` ( `username`, `first_name`, `last_name`, `email`, `phone`, `city`, `state`, `country`, `zip`, `introduction`, `linkedin_url`, `personal_url`, `role`, `public_profile_flag`, `chat_flag`, `forum_flag`, `developer_flag`, `status`, `created_time`, `updated_time`, `title`) VALUES
('testuser111', 'Murray', 'Smith', 'testvolunteeruser00@gmail.com', NULL, NULL, 'CA', 'USA', '10001', 'Hello, this is Andy Smith', NULL, NULL, 'V', 'Y', 'Y', 'Y', 'Y', 'A', '2017-01-01 06:00:00', '2017-04-09 02:25:26', 'My Title 1'),
('testuser112', 'Martins', 'Brown', 'testnonprofituser001@gmail.com', NULL, NULL, 'CA', 'USA', '10001', 'Hello, this is Bob Brown', NULL, NULL, 'O', 'Y', 'Y', 'Y', 'Y', 'A', '2017-01-01 06:00:00', '2017-04-09 02:25:26', 'My Title 2'),
('testuser113', 'Jessica', 'Dore', 'testc4sg02@gmail.com', NULL, NULL, 'CA', 'USA', '10001', 'Hello, this is Janie Doe', NULL, NULL, 'A', 'Y', 'Y', 'Y', 'Y', 'A', '2017-01-01 06:00:00', '2017-04-09 02:25:26', 'My Title 3'),
('testuser114', 'Johny', 'Does', 'testvolunteeruser03@gmail.com', NULL, NULL, 'CA', 'USA', '10001', 'Hello, this is John Doe', NULL, NULL, 'V', 'Y', 'Y', 'Y', 'Y', 'A', '2017-01-01 06:00:00', '2017-04-09 02:25:26', 'My Title 4')
