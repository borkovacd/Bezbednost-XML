insert into roles (id, name) values (1, "ROLE_ADMIN");
insert into roles (id, name) values (2, "ROLE_USER");
insert into roles (id, name) values (3, "ROLE_AGENT");

/*Permisije za admine*/
insert into permissions (id, name) values (1, "ADD_AGENT");
insert into permissions (id, name) values (2, "ADD_SERVICE");
insert into permissions (id, name) values (3, "DEL_SERVICE");
insert into permissions (id, name) values (4, "ADD_CAT");
insert into permissions (id, name) values (5, "DEL_CAT");
insert into permissions (id, name) values (6, "ADD_TYPE");
insert into permissions (id, name) values (7, "DEL_TYPE");


/*Permisije za agente*/
insert into permissions (id, name) values (8, "ADD_ACC");
insert into permissions (id, name) values (9, "EDIT_ACC");
insert into permissions (id, name) values (10, "DEL_ACC");

insert into permissions (id, name) values (11, "ADD_RES");

insert into permissions (id, name) values (12, "READ_CAT");
insert into permissions (id, name) values (13, "READ_SERV");
insert into permissions (id, name) values (14, "READ_TYPE");
insert into permissions (id, name) values (15, "READ_CITY");
insert into permissions (id, name) values (16, "READ_COUNTRY");

insert into permissions (id, name) values (17, "ADD_PRICE");

insert into permissions (id, name) values (18, "ADD_ROOM");
insert into permissions (id, name) values (19, "DEL_ROOM");
insert into permissions (id, name) values (20, "EDIT_ROOM");

/*Permisije za usere*/
insert into permissions (id, name) values (21, "RESERVE");
insert into permissions (id, name) values (22, "DEL_RES");




insert into permissions (id, name) values (23, "SEE_USERS");
insert into permissions (id, name) values (24, "ACT_USER");
insert into permissions (id, name) values (25, "BLOCK_USER");
insert into permissions (id, name) values (26, "DEL_USER");


insert into permissions (id, name) values (27, "SEND_MESS");

/*Podela permisija*/
insert into role_permissions (permission_id, role_id) values (1, 1);
insert into role_permissions (permission_id, role_id) values (2, 1);
insert into role_permissions (permission_id, role_id) values (3, 1);
insert into role_permissions (permission_id, role_id) values (4, 1);
insert into role_permissions (permission_id, role_id) values (5, 1);
insert into role_permissions (permission_id, role_id) values (6, 1);
insert into role_permissions (permission_id, role_id) values (7, 1);
insert into role_permissions (permission_id, role_id) values (23, 1);
insert into role_permissions (permission_id, role_id) values (24, 1);
insert into role_permissions (permission_id, role_id) values (25, 1);
insert into role_permissions (permission_id, role_id) values (26, 1);

insert into role_permissions (permission_id, role_id) values (8, 3);
insert into role_permissions (permission_id, role_id) values (9, 3);
insert into role_permissions (permission_id, role_id) values (10, 3);
insert into role_permissions (permission_id, role_id) values (11, 3);
insert into role_permissions (permission_id, role_id) values (12, 3);
insert into role_permissions (permission_id, role_id) values (13, 3);
insert into role_permissions (permission_id, role_id) values (14, 3);
insert into role_permissions (permission_id, role_id) values (15, 3);
insert into role_permissions (permission_id, role_id) values (16, 3);
insert into role_permissions (permission_id, role_id) values (17, 3);
insert into role_permissions (permission_id, role_id) values (18, 3);
insert into role_permissions (permission_id, role_id) values (19, 3);
insert into role_permissions (permission_id, role_id) values (20, 3);
insert into role_permissions (permission_id, role_id) values (27, 3);


insert into role_permissions (permission_id, role_id) values (21, 2);
insert into role_permissions (permission_id, role_id) values (22, 2);

insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (1, "/", "MTRoot@gmail.com", 1, "/", "/");
insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (2, "London", "MegaTravelLondon@gmail.com", 0, "S1", "Engleska");
insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (3, "HongKong", "MegaTravelHongKong@gmail.com", 0, "S2", "USA");
insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (4, "Boston", "MegaTravelBoston@gmail.com", 0, "S3", "Kina");

/*sifra admin*/
insert into users(id, city, enabled, email, first_name, last_name, nonlocked, password, status, username) values (1, "Beograd", 1, "admin@gmail.com", "Admin", "Admin", 1,"$2a$10$frbAPhjTDwVWCON6KKn8OOdmruQpC5RooLU2cOh0UI0LFvOyxGt7y", "AKTIVAN", "admin");

/*sifra mina*/
insert into users(id, city, enabled, email, first_name, last_name, nonlocked, password, status, username) values (2, "Novi Sad", 1, "mina@gmail.com", "Mina", "Minic", 1,"$2a$10$AfH8lUe2h3ZAIIITiCOwtuQVZrDXgbiQ99YF5R0lZy70/AqzfPTle", "AKTIVAN", "mina");


insert into users_roles (users_id, roles_id) values (1,1);
insert into users_roles (users_id, roles_id) values (2,2);

insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (5, "/", "MTAgent@gmail.com", 0, "SAgent", "/");

/*sifra agent1*/
insert into agents(id, address, enabled, first_name, last_name, mbr, nonlocked, password, username) values (1, "/", 1, "Agent", "Agent", "2653225CD", 1,  "$2a$10$PWUtTOKyHFhDSRPyFzD3tu9n2xxgLz4khi9zyFElJVp9I2ANsKuyi", "agent1");

insert into agents_roles (agents_id, roles_id) values (1,3);

insert into country (id, name) values (1, "Srbija");
insert into country (id, name) values (2, "Italija");

insert into city (id, name,country_id) values (1, "Novi Sad", 1);
insert into city (id, name,country_id) values (2, "Milano", 2);

insert into category (id, name) values (1, "nekategorisan");
insert into category (id, name) values (2, "jedna");
insert into category (id, name) values (3, "dve");
insert into category (id, name) values (4, "tri");
insert into category (id, name) values (5, "cetri");
insert into category (id, name) values (6, "pet");

insert into type_accomodation (id, name) values (1, "hotel");
insert into type_accomodation (id, name) values (2, "bed&breakfast");
insert into type_accomodation (id, name) values (3, "apartman");

insert into additional_services (id, name) values (1, "Parking");
insert into additional_services (id, name) values (2, "Wifi");
insert into additional_services (id, name) values (3, "Dorucak");
insert into additional_services (id, name) values (4, "Polupansion");
insert into additional_services (id, name) values (5, "Pansion");
insert into additional_services (id, name) values (6, "All inclusive");
insert into additional_services (id, name) values (7, "Kucni ljubimci");
insert into additional_services (id, name) values (8, "Tv");
insert into additional_services (id, name) values (9, "Minikuhinja/kuhinja");
insert into additional_services (id, name) values (10, "Privatno kupatilo");
insert into additional_services (id, name) values (11, "Otkazivanje");

insert into accomodation (id, address, description, name, pic, agent_id, category_id, city_id, type_accomodation_id) values (1, 'Bulevar Jase Tomica', 'Najstariji hotel u gradu.', 'Hotel Novi Sad', 'no-photo', 1, 6, 1, 1);
insert into accomodation (id, address, description, name, pic, agent_id, category_id, city_id, type_accomodation_id) values (2, 'Novosadskog sajma 35', 'Na odlicnoj lokaciji', 'Hotel Park', 'no-photo', 1, 5, 1, 1);
insert into accomodation (id, address, description, name, pic, agent_id, category_id, city_id, type_accomodation_id) values (3, 'Milano street 53', 'Best hotel', 'Hotel Milano', 'no-photo', 1, 6, 2, 3);


insert into accomodation_additional_services(accomodation_id, additional_services_id) values (1, 11);
insert into accomodation_additional_services(accomodation_id, additional_services_id) values (1, 3);

insert into accomodation_additional_services(accomodation_id, additional_services_id) values (2, 9);
insert into accomodation_additional_services(accomodation_id, additional_services_id) values (2, 11);
insert into accomodation_additional_services(accomodation_id, additional_services_id) values (2, 2);

insert into accomodation_additional_services(accomodation_id, additional_services_id) values (3, 1);
insert into accomodation_additional_services(accomodation_id, additional_services_id) values (3, 2);
insert into accomodation_additional_services(accomodation_id, additional_services_id) values (3, 5);
insert into accomodation_additional_services(accomodation_id, additional_services_id) values (3, 6);
insert into accomodation_additional_services(accomodation_id, additional_services_id) values (3, 7);
insert into accomodation_additional_services(accomodation_id, additional_services_id) values (3, 8);
insert into accomodation_additional_services(accomodation_id, additional_services_id) values (3, 10);
insert into accomodation_additional_services(accomodation_id, additional_services_id) values (3, 11);

insert into room (id, active, capacity, day, floor, has_balcony, reserved, accomodation_id) values (1, 0, 2, 3, 6, 1, 0, 1);
insert into room (id, active, capacity, day, floor, has_balcony, reserved, accomodation_id) values (2, 1, 4, 3, 2, 1, 0, 1);
insert into room (id, active, capacity, day, floor, has_balcony, reserved, accomodation_id) values (3, 0, 4, 3, 2, 1, 0, 2);
insert into room (id, active, capacity, day, floor, has_balcony, reserved, accomodation_id) values (4, 1, 4, 3, 2, 1, 0, 2);

insert into room (id, active, capacity, day, floor, has_balcony, reserved, accomodation_id) values (5, 1, 4, 3, 2, 1, 0, 3);
insert into room (id, active, capacity, day, floor, has_balcony, reserved, accomodation_id) values (6, 1, 3, 3, 3, 1, 0, 3);
insert into room (id, active, capacity, day, floor, has_balcony, reserved, accomodation_id) values (7, 1, 5, 3, 4, 0, 0, 3);

insert into price (id, month, price, room_id) values (1, 1, 25, 2);
insert into price (id, month, price, room_id) values (2, 2, 25, 2);
insert into price (id, month, price, room_id) values (3, 3, 25, 2);
insert into price (id, month, price, room_id) values (4, 4, 30, 2);
insert into price (id, month, price, room_id) values (5, 5, 30, 2);
insert into price (id, month, price, room_id) values (6, 6, 30, 2);
insert into price (id, month, price, room_id) values (7, 7, 45, 2);
insert into price (id, month, price, room_id) values (8, 8, 45, 2);
insert into price (id, month, price, room_id) values (9, 9, 45, 2);
insert into price (id, month, price, room_id) values (10, 10, 20, 2);
insert into price (id, month, price, room_id) values (11, 11, 20, 2);
insert into price (id, month, price, room_id) values (12, 12, 20, 2);
insert into price (id, month, price, room_id) values (21, 1, 20, 4);
insert into price (id, month, price, room_id) values (22, 2, 28, 4);
insert into price (id, month, price, room_id) values (23, 3, 29, 4);
insert into price (id, month, price, room_id) values (24, 4, 30, 4);
insert into price (id, month, price, room_id) values (25, 5, 30, 4);
insert into price (id, month, price, room_id) values (26, 6, 30, 4);
insert into price (id, month, price, room_id) values (27, 7, 33, 4);
insert into price (id, month, price, room_id) values (28, 8, 30, 4);
insert into price (id, month, price, room_id) values (29, 9, 34, 4);
insert into price (id, month, price, room_id) values (30, 10, 30, 4);
insert into price (id, month, price, room_id) values (31, 11, 35, 4);
insert into price (id, month, price, room_id) values (32, 12, 50, 4);


insert into price (id, month, price, room_id) values (33, 1, 20, 5);
insert into price (id, month, price, room_id) values (34, 2, 28, 5);
insert into price (id, month, price, room_id) values (35, 3, 40, 5);
insert into price (id, month, price, room_id) values (36, 4, 30, 5);
insert into price (id, month, price, room_id) values (37, 5, 30, 5);
insert into price (id, month, price, room_id) values (38, 6, 30, 5);
insert into price (id, month, price, room_id) values (39, 7, 40, 5);
insert into price (id, month, price, room_id) values (40, 8, 40, 5);
insert into price (id, month, price, room_id) values (41, 9, 50, 5);
insert into price (id, month, price, room_id) values (42, 10, 30, 5);
insert into price (id, month, price, room_id) values (43, 11, 35, 5);
insert into price (id, month, price, room_id) values (44, 12, 50, 5);

insert into price (id, month, price, room_id) values (45, 1, 20, 6);
insert into price (id, month, price, room_id) values (46, 2, 15, 6);
insert into price (id, month, price, room_id) values (47, 3, 45, 6);
insert into price (id, month, price, room_id) values (48, 4, 30, 6);
insert into price (id, month, price, room_id) values (49, 5, 45, 6);
insert into price (id, month, price, room_id) values (50, 6, 30, 6);
insert into price (id, month, price, room_id) values (51, 7, 40, 6);
insert into price (id, month, price, room_id) values (52, 8, 35, 6);
insert into price (id, month, price, room_id) values (53, 9, 50, 6);
insert into price (id, month, price, room_id) values (54, 10, 25, 6);
insert into price (id, month, price, room_id) values (55, 11, 20, 6);
insert into price (id, month, price, room_id) values (56, 12, 50, 6);

insert into price (id, month, price, room_id) values (57, 1, 30, 7);
insert into price (id, month, price, room_id) values (58, 2, 15, 7);
insert into price (id, month, price, room_id) values (59, 3, 30, 7);
insert into price (id, month, price, room_id) values (60, 4, 30, 7);
insert into price (id, month, price, room_id) values (61, 5, 50, 7);
insert into price (id, month, price, room_id) values (62, 6, 30, 7);
insert into price (id, month, price, room_id) values (63, 7, 25, 7);
insert into price (id, month, price, room_id) values (64, 8, 45, 7);
insert into price (id, month, price, room_id) values (65, 9, 35, 7);
insert into price (id, month, price, room_id) values (66, 10, 25, 7);
insert into price (id, month, price, room_id) values (67, 11, 25, 7);
insert into price (id, month, price, room_id) values (68, 12, 40, 7);


insert into reservation (id, confirmed, from_date, to_date, agent_id, room_id, user_id, price) values (1, 0, DATE '2019-05-20', DATE '2019-05-27', 1, 2, 2, 150);
insert into reservation_agent (id, from_date, to_date, agent_id, room_id) values (1, DATE '2019-06-20', DATE '2019-06-27', 1, 4);


/*insert into message (id, text, recipient_id, sender_id) values (1, "Hej agente", 1, 2);
insert into response (id, text,message_id, recipient_id, sender_id) values (1, "Hej useru",1, 2, 1);*/
/*insert into message (id, text, recipient_id, sender_id) values (2, "Odgovori mi agente ja sam admin", 1, 1);*/




