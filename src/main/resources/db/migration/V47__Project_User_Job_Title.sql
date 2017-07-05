ALTER TABLE project add job_title_id INT;
ALTER TABLE user add job_title_id INT;

CREATE TABLE job_title (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  job_title VARCHAR(35) NOT NULL,
  display_order INT NOT NULL
);

INSERT INTO job_title (id, job_title, display_order) VALUES (1, 'Developer', 1);
INSERT INTO job_title (id, job_title, display_order) VALUES (2, 'UI/UX Designer', 2);
INSERT INTO job_title (id, job_title, display_order) VALUES (3, 'QA Engineer', 3);
INSERT INTO job_title (id, job_title, display_order) VALUES (4, 'Build & Release Engineer', 4);
INSERT INTO job_title (id, job_title, display_order) VALUES (5, 'Software Architect', 5);
INSERT INTO job_title (id, job_title, display_order) VALUES (6, 'Business Analyst', 6);
INSERT INTO job_title (id, job_title, display_order) VALUES (7, 'Project Manager', 7);
INSERT INTO job_title (id, job_title, display_order) VALUES (8, 'Sales & Marketing', 8);