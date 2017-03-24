-- add timestap column
-- timestamp column is not null by default
ALTER TABLE project ADD create_time timestamp DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE project ADD change_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE organization ADD create_time timestamp DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE organization ADD change_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE user ADD create_time timestamp DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE user ADD change_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;