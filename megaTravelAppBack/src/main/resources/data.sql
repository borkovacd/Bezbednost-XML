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
insert into agents(id, address, first_name, last_name, mbr, password, username) values (13, "/", "Agent", "Agent", "2653225CD", "agent1", "agent1");

insert into clients(id, city, email, first_name, last_name, password, status, username) values (14, "Beograd", "markm@gmail.com", "Marko", "Markovic", "$2a$10$oVTxQOmorhFqM2WTByy7Pu1ij2yZXf40CK1mP8Weq8WrIVrVdIRr6", "NEAKTIVAN", "markoM");

insert into country (id, name) values (15, "UK");

insert into city (id, name,country_id) values (16, "London", 15);

insert into category (id, name) values (17, "nekategorisan");
insert into category (id, name) values (18, "jedna");
insert into category (id, name) values (19, "dve");
insert into category (id, name) values (20, "tri");
insert into category (id, name) values (21, "cetri");
insert into category (id, name) values (22, "pet");

insert into type_accomodation (id, name) values (23, "hotel");
insert into type_accomodation (id, name) values (24, "bad&breakfast");
insert into type_accomodation (id, name) values (25, "apartman");

insert into additional_services (id, name) values (26, "Parking");
insert into additional_services (id, name) values (27, "Wifi");
insert into additional_services (id, name) values (28, "Dorucak");
insert into additional_services (id, name) values (29, "Polupansion");
insert into additional_services (id, name) values (30, "Pansion");
insert into additional_services (id, name) values (31, "All inclusive");
insert into additional_services (id, name) values (32, "Kucni ljubimci");
insert into additional_services (id, name) values (33, "Tv");
insert into additional_services (id, name) values (34, "Minikuhinja/kuhinja");
insert into additional_services (id, name) values (35, "Privatno kupatilo");
insert into additional_services (id, name) values (36, "Otkazivanje");

