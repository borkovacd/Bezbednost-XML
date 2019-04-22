insert into roles (id, name) values (2, "ROLE_ADMIN");
insert into roles (id, name) values (3, "ROLE_USER");

insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (4, "/", "MTRoot@gmail.com", 1, "/", "/");
insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (5, "London", "MegaTravelLondon@gmail.com", 0, "S1", "Engleska");
insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (6, "HongKong", "MegaTravelHongKong@gmail.com", 0, "S2", "USA");
insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (7, "Boston", "MegaTravelBoston@gmail.com", 0, "S3", "Kina");

insert into users (id, city, email, first_name, last_name, password) values (8, null, "MTRoot@gmail.com", null, null, "$2a$10$hqAlGmj1Pf0uWCnYelh7vuhdIwYuE2HyvS4XBBEAVe5ErUksUlYzK");
insert into users (id, city, email, first_name, last_name, password) values (9, null, "MegaTravelLondon@gmail.com", null, null, "$2a$10$hqAlGmj1Pf0uWCnYelh7vuF4S6yiKza6RPNm3d60L2.EcApMKI926");
insert into users (id, city, email, first_name, last_name, password) values (10, null, "MegaTravelHongKong@gmail.com", null, null, "$2a$10$hqAlGmj1Pf0uWCnYelh7vug8K1sPeZuYSNYEBgGFclod90CHK.FIC");
insert into users (id, city, email, first_name, last_name, password) values (11, null, "MegaTravelBoston@gmail.com", null, null, "$2a$10$hqAlGmj1Pf0uWCnYelh7vudsk9bcvoKDJME6EE9yQIH8/aDcfTENu");

insert into users_roles (users_id, roles_id) values (8,2);
insert into users_roles (users_id, roles_id) values (9,3);
insert into users_roles (users_id, roles_id) values (10,3);
insert into users_roles (users_id, roles_id) values (11,3);