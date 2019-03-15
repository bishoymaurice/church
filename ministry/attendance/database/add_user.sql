insert into ministryr2.users_profile (id, roles) values (1, 'user');

commit;

insert into ministryr2.users (id, profile, name, password) values ((select nvl((max(id)+1), 1) from ministryr2.users), 1, 'user', 'user');

commit;
