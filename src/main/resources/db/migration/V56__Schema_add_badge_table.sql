DROP TABLE IF EXISTS badge;
CREATE TABLE badge (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  project_id INT NOT NULL,
  created_time TIMESTAMP NOT NULL DEFAULT '2017-01-01 00:00:00',
  updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (project_id) REFERENCES project (id),
  FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE UNIQUE INDEX userid_projectid ON badge(user_id, project_id);