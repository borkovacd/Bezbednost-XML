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

insert into city (id, name,country_id) values (16, "Novi Sad", 15);

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

insert into accomodation (id, address, description, name, pic, agent_id, category_id, city_id, type_accomodation_id) values (1, 'Bulevar Jase Tomica', 'Najstariji hotel u gradu.', 'Hotel Novi Sad', 'no-photo', 13, 22, 16, 23);
insert into accomodation (id, address, description, name, pic, agent_id, category_id, city_id, type_accomodation_id) values (2, 'Novosadskog sajma 35', 'Na odlicnoj lokaciji', 'Hotel Park', 'no-photo', 13, 22, 16, 23);

insert into accomodation_additional_services(accomodation_id, additional_services_id) values (1, 36);
insert into accomodation_additional_services(accomodation_id, additional_services_id) values (1, 28);

insert into room (id, active, capacity, day, floor, has_balcony, reserved, accomodation_id) values (5, 0, 2, 90, 6, 1, 0, 1);
insert into room (id, active, capacity, day, floor, has_balcony, reserved, accomodation_id) values (6, 1, 4, 90, 2, 1, 0, 1);
insert into room (id, active, capacity, day, floor, has_balcony, reserved, accomodation_id) values (7, 0, 4, 90, 2, 1, 0, 2);
insert into room (id, active, capacity, day, floor, has_balcony, reserved, accomodation_id) values (8, 1, 4, 90, 2, 1, 0, 2);

insert into price (id, month, price, room_id) values (1, 1, 25, 6);
insert into price (id, month, price, room_id) values (2, 2, 25, 6);
insert into price (id, month, price, room_id) values (3, 3, 25, 6);
insert into price (id, month, price, room_id) values (4, 4, 30, 6);
insert into price (id, month, price, room_id) values (5, 5, 30, 6);
insert into price (id, month, price, room_id) values (6, 6, 30, 6);
insert into price (id, month, price, room_id) values (7, 7, 45, 6);
insert into price (id, month, price, room_id) values (8, 8, 45, 6);
insert into price (id, month, price, room_id) values (9, 9, 45, 6);
insert into price (id, month, price, room_id) values (10, 10, 20, 6);
insert into price (id, month, price, room_id) values (11, 11, 20, 6);
insert into price (id, month, price, room_id) values (12, 12, 20, 6);
insert into price (id, month, price, room_id) values (21, 1, 20, 8);
insert into price (id, month, price, room_id) values (22, 2, 28, 8);
insert into price (id, month, price, room_id) values (23, 3, 29, 8);
insert into price (id, month, price, room_id) values (24, 4, 30, 8);
insert into price (id, month, price, room_id) values (25, 5, 30, 8);
insert into price (id, month, price, room_id) values (26, 6, 30, 8);
insert into price (id, month, price, room_id) values (27, 7, 33, 8);
insert into price (id, month, price, room_id) values (28, 8, 30, 8);
insert into price (id, month, price, room_id) values (29, 9, 34, 8);
insert into price (id, month, price, room_id) values (30, 10, 30, 8);
insert into price (id, month, price, room_id) values (31, 11, 35, 8);
insert into price (id, month, price, room_id) values (32, 12, 50, 8);



