--Add map fields: longitude and latitude
ALTER TABLE organization ADD latitude decimal(9,6);
ALTER TABLE organization ADD longitude decimal(9,6);