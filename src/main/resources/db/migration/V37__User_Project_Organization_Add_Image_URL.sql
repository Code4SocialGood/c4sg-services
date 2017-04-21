ALTER TABLE user ADD avatar_url varchar(150);
ALTER TABLE user ADD resume_url varchar(150);
ALTER TABLE project ADD image_url varchar(150);
ALTER TABLE organization ADD logo_url varchar(150);

UPDATE user SET avatar_url = 'http://avachara.com/avatar/gallery/chara170417184259_7833.jpg';
UPDATE user SET resume_url = 'http://www.bellevue.edu/student-support/career-services/pdfs/resume-samples.pdf';
UPDATE project SET image_url = 'https://gallery.mailchimp.com/89ad8b5e6d0f615d934e3f374/images/0f1d9b58-0615-435e-b35e-299502d47b08.jpg';
UPDATE organization SET logo_url = 'http://rocktothefuture.org/wp-content/uploads/2017/03/RTTFLogoWEB.png';