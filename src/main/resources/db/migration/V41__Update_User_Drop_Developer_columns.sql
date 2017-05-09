--drop developer columns from user table
ALTER TABLE `user`
  DROP `developer_flag`,
  DROP `developer_commit`;