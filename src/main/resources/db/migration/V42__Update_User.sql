ALTER TABLE user DROP phone;
ALTER TABLE user DROP city;
ALTER TABLE user DROP zip;
ALTER TABLE user ADD facebook_url varchar(100);
ALTER TABLE user ADD twitter_url varchar(100);