-- add timestap column
-- https://dev.mysql.com/doc/refman/5.5/en/timestamp-initialization.html
-- at most one TIMESTAMP column per table could be automatically initialized or updated to the current date and time
-- timestamp column is not null by default
-- Usage: you must set NULL to create_at column during insert. create_at column will be inserted with current timestamp.
-- Sample: insert into project (name, description, created_at) values ('project test', 'project test', null)
ALTER TABLE project ADD created_at timestamp DEFAULT '2017-001-01 00:00:00';
ALTER TABLE project ADD updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE organization ADD created_at timestamp DEFAULT '2017-001-01 00:00:00';
ALTER TABLE organization ADD updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE user ADD created_at timestamp DEFAULT '2017-001-01 00:00:00';
ALTER TABLE user ADD updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;