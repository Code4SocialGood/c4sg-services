ALTER TABLE user drop public_profile_flag; 
ALTER TABLE user add notify_flag CHAR(1) NOT NULL DEFAULT 'N';
ALTER TABLE user add publish_flag CHAR(1) NOT NULL DEFAULT 'N'; 