--add new column
ALTER TABLE user
ADD c4sg_developer_flg CHAR(1) NOT NULL DEFAULT 'N';

--load data in the column
UPDATE user
SET c4sg_developer_flg = 'Y' 
WHERE id IN (2,8, 12, 18,22,28,33,35,45,49,52,58,61,70,72,78,90,100);