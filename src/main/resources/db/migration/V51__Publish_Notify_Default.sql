--Change default from N to Y
ALTER TABLE user ALTER COLUMN notify_flag SET DEFAULT 'Y';
ALTER TABLE user ALTER COLUMN publish_flag SET DEFAULT 'Y';