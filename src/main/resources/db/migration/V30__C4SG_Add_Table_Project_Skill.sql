-- -----------------------------------------------------
-- Table project_skill
-- -----------------------------------------------------
CREATE TABLE project_skill (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  project_id INT NOT NULL,
  skill_id INT NOT NULL,
  display_order INT NOT NULL DEFAULT '0',
  FOREIGN KEY (project_id) REFERENCES user (id),
  FOREIGN KEY (skill_id) REFERENCES skill (id)
);