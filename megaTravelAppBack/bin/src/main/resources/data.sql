insert into roles (id, name) values (2, "ROLE_ADMIN");
insert into roles (id, name) values (3, "ROLE_USER");

insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (4, "/", "MTRoot@gmail.com", 1, "/", "/");
insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (5, "London", "MegaTravelLondon@gmail.com", 0, "S1", "Engleska");
insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (6, "HongKong", "MegaTravelHongKong@gmail.com", 0, "S2", "USA");
insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (7, "Boston", "MegaTravelBoston@gmail.com", 0, "S3", "Kina");

insert into users (id, city, email, first_name, last_name, password) values (8, "/", "MTRoot@gmail.com", "MTRoot", "MTRoot", "$2a$10$hqAlGmj1Pf0uWCnYelh7vuhdIwYuE2HyvS4XBBEAVe5ErUksUlYzK");
insert into users (id, city, email, first_name, last_name, password) values (9, "London", "MegaTravelLondon@gmail.com", "MegaTravelLondon", "MegaTravelLondon", "$2a$10$hqAlGmj1Pf0uWCnYelh7vuF4S6yiKza6RPNm3d60L2.EcApMKI926");
insert into users (id, city, email, first_name, last_name, password) values (10, "Hong Kong", "MegaTravelHongKong@gmail.com", "MegaTravelHongKong", "MegaTravelHongKong", "$2a$10$hqAlGmj1Pf0uWCnYelh7vug8K1sPeZuYSNYEBgGFclod90CHK.FIC");
insert into users (id, city, email, first_name, last_name, password) values (11, "Boston", "MegaTravelBoston@gmail.com", "MegaTravelBoston", "MegaTravelBoston", "$2a$10$hqAlGmj1Pf0uWCnYelh7vudsk9bcvoKDJME6EE9yQIH8/aDcfTENu");

insert into users_roles (users_id, roles_id) values (8,2);
insert into users_roles (users_id, roles_id) values (9,3);
insert into users_roles (users_id, roles_id) values (10,3);
insert into users_roles (users_id, roles_id) values (11,3);

insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (12, "/", "MTAgent@gmail.com", 0, "SAgent", "/");
insert into agents(id, address, first_name, last_name, mbr, password, username) values (13, "/", "Agent", "Agent", "2653225CD", "$2a$10$um7ZtQ79vXrrS2jsZNjdZeSvwma.0yuKkeHM5tarAProLMHjFrgyG", "agent1");
