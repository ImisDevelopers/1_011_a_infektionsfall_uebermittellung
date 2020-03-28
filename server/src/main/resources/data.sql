INSERT into users (id, password, username)
values (1, '$2y$10$50NQ8FBG.soMzekSp8JF9ODOTYxysxyJxzovUNtXkCbAjoJxl0nq2', 'test_lab');
INSERT into users (id, password, username)
values (2, '$2y$10$50NQ8FBG.soMzekSp8JF9ODOTYxysxyJxzovUNtXkCbAjoJxl0nq2', 'test_doctor');
INSERT into authority(id, display_name, role)
values (1, 'labor', 'LABORATORY');
INSERT into authority(id, display_name, role)
VALUES (2, 'Doktor', 'DOCTOR');
INSERT into users_roles (user_id, roles_id)
VALUES (1, 1);
INSERT into users_roles (user_id, roles_id)
VALUES (2, 2);