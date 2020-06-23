  #-----------------------------------
  #USER RIGHTS MANAGEMENT
  #-----------------------------------
  CREATE USER 'admincdb'@'localhost' IDENTIFIED BY 'qwerty1234';
  CREATE USER 'admintest'@'localhost' IDENTIFIED BY 'qwerty1234';
  GRANT ALL PRIVILEGES ON `computer-database-db`.* TO 'admincdb'@'localhost' WITH GRANT OPTION;
  GRANT ALL PRIVILEGES ON `test-db`.* TO 'admintest'@'localhost' WITH GRANT OPTION;

  FLUSH PRIVILEGES;
