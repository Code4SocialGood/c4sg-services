
-- Fill in new fields
UPDATE user
SET
  introduction = "Full stack developer with 3 years of industry experience",
linked_inurl= "https://www.linkedin.com/feed/",
personal_web_site = "www.mtest.blogspot.com",
resume = "Thi$ is a s@mple resme with sPecial ch@r@cters!"
WHERE id BETWEEN 1 AND 9;