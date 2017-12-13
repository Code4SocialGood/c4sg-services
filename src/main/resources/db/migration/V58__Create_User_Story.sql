-- Create Table `user_skill`
DROP TABLE IF EXISTS `story`;
CREATE TABLE `story`(
   `id`                     INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_organization_id`    INT(11) NOT NULL,
  `title`                   VARCHAR(100) NOT NULL,
  `image`                   VARCHAR(100) DEFAULT NULL,
  `body`                    VARCHAR(100) DEFAULT NULL,
  `created_time`            TIMESTAMP NOT NULL DEFAULT '2017-001-01 00:00:00'
);