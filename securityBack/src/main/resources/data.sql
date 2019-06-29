insert into roles (id, name) values (1, "ROLE_ADMIN");
insert into roles (id, name) values (2, "ROLE_USER");

insert into permissions (id, name) values (1, "CREATE_CERT");
insert into permissions (id, name) values (2, "READ_CERT");
insert into permissions (id, name) values (3, "DELETE");
insert into permissions (id, name) values (4, "REVOKE_CERT");
insert into permissions (id, name) values (5, "READ_ALLCERT");
insert into permissions (id, name) values (6, "READ_SUB_SOFT");

insert into role_permissions (permission_id, role_id) values (1, 1);
insert into role_permissions (permission_id, role_id) values (2, 1);
insert into role_permissions (permission_id, role_id) values (3, 1);
insert into role_permissions (permission_id, role_id) values (4, 1);
insert into role_permissions (permission_id, role_id) values (5, 1);
insert into role_permissions (permission_id, role_id) values (6, 1);

insert into role_permissions (permission_id, role_id) values (1, 2);
insert into role_permissions (permission_id, role_id) values (2, 2);
insert into role_permissions (permission_id, role_id) values (4, 2);
insert into role_permissions (permission_id, role_id) values (6, 2);


insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (1, "/", "MTRoot@gmail.com", 1, "/", "/");
insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (2, "London", "MegaTravelLondon@gmail.com", 0, "S1", "Engleska");
insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (3, "HongKong", "MegaTravelHongKong@gmail.com", 0, "S2", "USA");
insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (4, "Boston", "MegaTravelBoston@gmail.com", 0, "S3", "Kina");

insert into users (id, city, email, enabled, first_name, last_name, nonlocked, password) values (1, "/", "MTRoot@gmail.com", 1, "MTRoot", "MTRoot", 1,"$2a$10$hqAlGmj1Pf0uWCnYelh7vuhdIwYuE2HyvS4XBBEAVe5ErUksUlYzK");
insert into users (id, city, email, enabled, first_name, last_name, nonlocked, password) values (2, "London", "MegaTravelLondon@gmail.com", 1, "MegaTravelLondon", "MegaTravelLondon", 1,"$2a$10$hqAlGmj1Pf0uWCnYelh7vuF4S6yiKza6RPNm3d60L2.EcApMKI926");
insert into users (id, city, email, enabled, first_name, last_name, nonlocked, password) values (3, "Hong Kong", "MegaTravelHongKong@gmail.com", 1, "MegaTravelHongKong", "MegaTravelHongKong", 1,"$2a$10$hqAlGmj1Pf0uWCnYelh7vug8K1sPeZuYSNYEBgGFclod90CHK.FIC");
insert into users (id, city, email, enabled, first_name, last_name, nonlocked, password) values (4, "Boston", "MegaTravelBoston@gmail.com", 1, "MegaTravelBoston", "MegaTravelBoston", 1,"$2a$10$hqAlGmj1Pf0uWCnYelh7vudsk9bcvoKDJME6EE9yQIH8/aDcfTENu");

insert into users_roles (users_id, roles_id) values (1,1);
insert into users_roles (users_id, roles_id) values (2,2);
insert into users_roles (users_id, roles_id) values (3,2);
insert into users_roles (users_id, roles_id) values (4,2);

insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (5, "/", "MTAgent@gmail.com", 1, "SAgent", "/");
insert into subject_softwares (id, city, email, has_certificate, software_id, state) values (6, "/", "localhost@gmail.com", 1, "loclh", "/");
