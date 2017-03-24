-- Modify columns
ALTER TABLE user DROP slack_registered_flag;
ALTER TABLE user ADD slack_registered_flag char(1) not null default 'N';
