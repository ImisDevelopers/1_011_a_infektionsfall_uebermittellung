/* We do not want to conflict with hibernates sequence for primary keys */
INSERT into users (id, password, username)
values (1000001, '$2y$10$50NQ8FBG.soMzekSp8JF9ODOTYxysxyJxzovUNtXkCbAjoJxl0nq2', 'test');
INSERT into users (id, password, username)
values (1000002, '$2a$10$Dctp4SB7zWJZV7yASfTp.umRJ9GJVDABj6hWfOpDM9ho9XiIb2ktq', 'test_doctor');
INSERT into authority(id, display_name, role)
values (1, 'Labor', 'LABORATORY');
INSERT into authority(id, display_name, role)
VALUES (2, 'Doktor', 'DOCTORS_OFFICE');
INSERT into authority(id, display_name, role)
VALUES (3, 'Klinik', 'CLINIC');
INSERT into authority(id, display_name, role)
VALUES (4, 'Teststelle', 'TEST_SITE');
INSERT into users_roles (user_id, roles_id)
VALUES (1000001, 1);
INSERT into users_roles (user_id, roles_id)
VALUES (1000002, 2);
