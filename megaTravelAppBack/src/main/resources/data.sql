insert into roles (id, name) values (1, "ROLE_ADMIN");
insert into roles (id, name) values (2, "ROLE_USER");
insert into roles (id, name) values (3, "ROLE_AGENT");

insert into permissions (id, name) values (1, "ADD_AGENT");
insert into permissions (id, name) values (2, "ADD_SERVICE");
insert into permissions (id, name) values (3, "ADD_CAT");
insert into permissions (id, name) values (4, "ADD_TYPE");

insert into role_permissions (permission_id, role_id) values (1, 1);
insert into role_permissions (permission_id, role_id) values (2, 1);
insert into role_permissions (permission_id, role_id) values (3, 1);
insert into role_permissions (permission_id, role_id) values (4, 1);

insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (1, "/", "MTRoot@gmail.com", 1, "/", "/");
insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (2, "London", "MegaTravelLondon@gmail.com", 0, "S1", "Engleska");
insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (3, "HongKong", "MegaTravelHongKong@gmail.com", 0, "S2", "USA");
insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (4, "Boston", "MegaTravelBoston@gmail.com", 0, "S3", "Kina");

/*sifra admin*/
insert into users(id, city, enabled, email, first_name, last_name, nonlocked, password, status, username) values (1, "Beograd", 1, "admin@gmail.com", "Admin", "Admin", 1,"$2a$10$frbAPhjTDwVWCON6KKn8OOdmruQpC5RooLU2cOh0UI0LFvOyxGt7y", "AKTIVAN", "admin");

/*sifra mina*/
insert into users(id, city, enabled, email, first_name, last_name, nonlocked, password, status, username) values (2, "Novi Sad", 1, "mina@gmail.com", "Mina", "Minic", 1,"$2a$10$AfH8lUe2h3ZAIIITiCOwtuQVZrDXgbiQ99YF5R0lZy70/AqzfPTle", "NEAKTIVAN", "mina");

insert into users_roles (users_id, roles_id) values (1,1);
insert into users_roles (users_id, roles_id) values (2,2);

insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (5, "/", "MTAgent@gmail.com", 0, "SAgent", "/");
insert into agents(id, address, first_name, last_name, mbr, password, username) values (1, "/", "Agent", "Agent", "2653225CD", "agent1", "agent1");

insert into agents_roles (agents_id, roles_id) values (1,3);


insert into country (id, name) values (1, "UK");

insert into city (id, name,country_id) values (1, "Novi Sad", 1);

insert into category (id, name) values (1, "nekategorisan");
insert into category (id, name) values (2, "jedna");
insert into category (id, name) values (3, "dve");
insert into category (id, name) values (4, "tri");
insert into category (id, name) values (5, "cetri");
insert into category (id, name) values (6, "pet");

insert into type_accomodation (id, name) values (1, "hotel");
insert into type_accomodation (id, name) values (2, "bad&breakfast");
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
insert into accomodation (id, address, description, name, pic, agent_id, category_id, city_id, type_accomodation_id) values (2, 'Novosadskog sajma 35', 'Na odlicnoj lokaciji', 'Hotel Park', 'no-photo', 1, 6, 1, 1);

insert into accomodation_additional_services(accomodation_id, additional_services_id) values (1, 11);
insert into accomodation_additional_services(accomodation_id, additional_services_id) values (1, 3);

insert into room (id, active, capacity, day, floor, has_balcony, reserved, accomodation_id) values (1, 0, 2, 90, 6, 1, 0, 1);
insert into room (id, active, capacity, day, floor, has_balcony, reserved, accomodation_id) values (2, 1, 4, 90, 2, 1, 0, 1);
insert into room (id, active, capacity, day, floor, has_balcony, reserved, accomodation_id) values (3, 0, 4, 90, 2, 1, 0, 2);
insert into room (id, active, capacity, day, floor, has_balcony, reserved, accomodation_id) values (4, 1, 4, 90, 2, 1, 0, 2);

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

insert into reservation (id, confirmed, from_date, to_date, agent_id, room_id, user_id) values (1, 0, DATE '2019-05-20', DATE '2019-06-05', 1, 2, 2);



