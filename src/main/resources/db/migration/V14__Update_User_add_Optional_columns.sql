-- Modify columns
ALTER TABLE user
ADD  introduction         	 varchar(500) AFTER location,
ADD linked_inurl        	 	 varchar(100) AFTER introduction,
ADD personal_web_site     	 varchar(100) AFTER linked_inurl,
ADD resume        		 varchar(500) AFTER personal_web_site;
