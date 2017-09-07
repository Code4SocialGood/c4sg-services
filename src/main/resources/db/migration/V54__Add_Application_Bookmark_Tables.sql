DROP TABLE IF EXISTS application;
CREATE TABLE application (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL UNIQUE,
  project_id INT NOT NULL UNIQUE,
  comment VARCHAR(10000),
  resume_flag CHAR(1) NOT NULL DEFAULT 'N' CHECK(resume_flag IN ('Y','N')),
  status CHAR(1) NOT NULL DEFAULT 'A' CHECK(status IN ('A','C','D')),
  applied_time TIMESTAMP,
  accepted_time TIMESTAMP,
  declined_time TIMESTAMP,
  created_time TIMESTAMP NOT NULL DEFAULT '2017-01-01 00:00:00',
  updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (project_id) REFERENCES project (id),
  FOREIGN KEY (user_id) REFERENCES user (id)
);

DROP TABLE IF EXISTS bookmark;
CREATE TABLE bookmark (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL UNIQUE,
  project_id INT NOT NULL UNIQUE,
  created_time TIMESTAMP NOT NULL DEFAULT '2017-01-01 00:00:00',
  updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (project_id) REFERENCES project (id),
  FOREIGN KEY (user_id) REFERENCES user (id)
);