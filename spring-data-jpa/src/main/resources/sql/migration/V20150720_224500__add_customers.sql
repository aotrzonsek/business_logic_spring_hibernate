-- customers
insert into customer(id, first_name, last_name, phone_number, email, birth_date)
values (1, 'Jan', 'Kowalski', '0048718732387', 'jan@kowalski.pl', DATE('1980-01-20'));

insert into customer(id, first_name, last_name, phone_number, email, birth_date)
values (2, 'Anna', 'Nowak', '0048228734442', 'anna@nowak.pl', DATE('1970-11-05'));

-- customer cards

insert into customer_card(id, serial_number, expiration_date, customer_fk)
values (1, 'hdakhk979324ui9u', DATE('2016-06-30'), 1);

insert into customer_card(id, serial_number, expiration_date, customer_fk)
values (2, 'jdfskgr823492637', DATE('2014-05-15'), 1);

insert into customer_card(id, serial_number, expiration_date, customer_fk)
values (3, 'lksdjlhfds823479', DATE('2017-02-10'), 2);

insert into customer_card(id, serial_number, expiration_date, customer_fk)
values (4, 'sajhdksadh789798', DATE('2020-01-11'), null);