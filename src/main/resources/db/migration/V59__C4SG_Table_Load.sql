
-- -----------------------------------------------------
-- Table story
-- -----------------------------------------------------
DELETE FROM story;
INSERT INTO story (user_organization_id, title, image, body, created_time) VALUES (1, "My First Story", "Tis another great story",  "http://tinyurl.com", CURTIME());
INSERT INTO story (user_organization_id, title, image, body, created_time) VALUES (1, "My Second Story", "Tis another great story",  "http://tinyurl.com", CURTIME());