ALTER TABLE user ADD latitude decimal(9,6);
ALTER TABLE user ADD longitude decimal(9,6);

UPDATE user SET latitude = 35.929673;
UPDATE user SET longitude = -78.948237;