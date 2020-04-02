/* We do not want to conflict with hibernates sequence for primary keys */
INSERT into users (id, password, username)
values (1000001, '$2y$10$50NQ8FBG.soMzekSp8JF9ODOTYxysxyJxzovUNtXkCbAjoJxl0nq2', 'test_lab');
INSERT into users (id, password, username)
values (1000002, '$2a$10$Dctp4SB7zWJZV7yASfTp.umRJ9GJVDABj6hWfOpDM9ho9XiIb2ktq', 'test_doctor');
