-- -----------------------------------------------------
-- C4SG Table Creation
-- -----------------------------------------------------
DROP TABLE IF EXISTS user_project ;
DROP TABLE IF EXISTS user_organization ;
DROP TABLE IF EXISTS user_skill ;
DROP TABLE IF EXISTS project ;
DROP TABLE IF EXISTS organization ;
DROP TABLE IF EXISTS user ;
DROP TABLE IF EXISTS skill ;
DROP TABLE IF EXISTS cause ;
DROP TABLE IF EXISTS application ;

-- -----------------------------------------------------
-- Table project
-- -----------------------------------------------------
CREATE TABLE project (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  organization_id INT NOT NULL,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(1000),
  address1 VARCHAR(100),
  address2 VARCHAR(100),
  city VARCHAR(100),
  state VARCHAR(100),
  country VARCHAR(100),
  zip VARCHAR(16),
  remote_flag CHAR(1) NOT NULL DEFAULT 'Y',
  status CHAR(1) NOT NULL DEFAULT 'A',
  created_time TIMESTAMP NOT NULL DEFAULT '2017-001-01 00:00:00',
  updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  );

-- -----------------------------------------------------
-- Table organization
-- -----------------------------------------------------
CREATE TABLE organization (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  website_url VARCHAR(100),
  description VARCHAR(1000),
  address1 VARCHAR(100),
  address2 VARCHAR(100),
  city VARCHAR(100),
  state VARCHAR(100),
  country VARCHAR(100),
  zip VARCHAR(16),
  contact_name VARCHAR(100),
  contact_phone VARCHAR(16),
  contact_email VARCHAR(100),
  category CHAR(1) NOT NULL DEFAULT 'N',
  status CHAR(1) NOT NULL DEFAULT 'A',
  created_time TIMESTAMP NOT NULL DEFAULT '2017-001-01 00:00:00',
  updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- -----------------------------------------------------
-- Table user
-- -----------------------------------------------------
CREATE TABLE user (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100),
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  email VARCHAR(100) NOT NULL,
  phone VARCHAR(100),
  city VARCHAR(100),
  state VARCHAR(100),
  country VARCHAR(100),
  zip VARCHAR(16),
  introduction VARCHAR(1000),
  linkedin_url VARCHAR(100),
  personal_url VARCHAR(100),
  role CHAR(1) NOT NULL DEFAULT 'V',
  public_profile_flag CHAR(1) NOT NULL DEFAULT 'N',
  chat_flag CHAR(1) NOT NULL DEFAULT 'N',
  forum_flag CHAR(1) NOT NULL DEFAULT 'N',
  developer_flag CHAR(1) NOT NULL DEFAULT 'N',
  status CHAR(1) NOT NULL DEFAULT 'A',
  created_time TIMESTAMP NOT NULL DEFAULT '2017-001-01 00:00:00',
  updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE (email)
);

-- -----------------------------------------------------
-- Table skill
-- -----------------------------------------------------
CREATE TABLE skill (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  skill_name VARCHAR(100) NOT NULL
);

-- -----------------------------------------------------
-- Table user_organization
-- -----------------------------------------------------
CREATE TABLE user_organization (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  organization_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user (id),
  FOREIGN KEY (organization_id) REFERENCES organization (id)
);

-- -----------------------------------------------------
-- Table user_project
-- -----------------------------------------------------
CREATE TABLE user_project (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  project_id INT NOT NULL,
  status CHAR(1) NOT NULL DEFAULT 'A',
  FOREIGN KEY (user_id) REFERENCES user (id),
  FOREIGN KEY (project_id) REFERENCES project (id)
);

-- -----------------------------------------------------
-- Table user_skill
-- -----------------------------------------------------
CREATE TABLE user_skill (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  skill_id INT NOT NULL,
  display_order INT NOT NULL DEFAULT '0',
  FOREIGN KEY (user_id) REFERENCES user (id),
  FOREIGN KEY (skill_id) REFERENCES skill (id)
);