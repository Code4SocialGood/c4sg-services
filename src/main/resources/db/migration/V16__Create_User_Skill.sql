-- Create Table `user_skill`
DROP TABLE IF EXISTS `user_skill`;
CREATE TABLE `user_skill`(
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `skill_id` INT(11) NOT NULL,
  `order` INT(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);

-- Recreate Table `skill` to rename column from skill to skill_name
DROP TABLE IF EXISTS `skill`;
CREATE TABLE `skill`(
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `skill_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`)
);