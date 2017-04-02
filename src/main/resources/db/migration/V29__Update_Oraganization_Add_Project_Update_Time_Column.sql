--add new column
ALTER TABLE organization ADD project_updated_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;