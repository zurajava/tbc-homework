--create database
drop database if exists tbc_homework;
CREATE DATABASE tbc_homework;
--run below script after database create
create table client
(
    first_name varchar,
    last_name  varchar,
    age        integer,
    iban       varchar,
    date       date
);

alter table client
    owner to zura;


create table transaction
(
    iban        varchar,
    amount      numeric,
    currency_id integer,
    date        date
);

alter table transaction
    owner to zura;

create table currency
(
    id       integer,
    ccy_from varchar,
    ccy_to   varchar,
    rate     numeric
);

alter table currency
    owner to zura;


INSERT INTO public.client (first_name, last_name, age, iban, date) VALUES ('ილია', 'ჭავჭავაძე', 36, 'RDRTGE2200000123', '2020-09-21');
INSERT INTO public.client (first_name, last_name, age, iban, date) VALUES ('აკაკი', 'წერეთელი', 38, 'RDRTGE2200000321', '2020-09-22');
INSERT INTO public.client (first_name, last_name, age, iban, date) VALUES ('აკაკი', 'წერეთელი', 38, 'RDRTGE2200000324', '2020-09-28');
INSERT INTO public.client (first_name, last_name, age, iban, date) VALUES ('აკაკი', 'წერეთელი', 38, 'RDRTGE2200000325', '2020-09-30');

INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000123', 23.34, 1, '2020-09-21');
INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000123', 123.34, 1, '2020-09-22');
INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000324', 233.88, 1, '2020-09-23');
INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000123', 24.34, 2, '2020-09-24');
INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000123', 224.34, 2, '2020-09-25');
INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000324', 234.88, 2, '2020-09-26');
INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000123', 25.34, 3, '2020-09-27');
INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000324', 235.88, 4, '2020-09-28');
INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000123', 326.34, 3, '2020-09-29');
INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000324', 236.88, 3, '2020-09-30');
INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000325', 238.88, 1, '2020-10-01');
INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000123', 27.34, 2, '2020-10-02');
INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000324', 237.88, 3, '2020-10-03');
INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000123', 428.34, 2, '2020-10-04');
INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000325', 239.88, 2, '2020-10-05');
INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000325', 240.88, 2, '2020-10-06');
INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000325', 241.88, 1, '2020-10-07');
INSERT INTO public.transaction (iban, amount, currency_id, date) VALUES ('RDRTGE2200000325', 242.88, 3, '2020-10-08');

INSERT INTO public.currency (id, ccy_from, ccy_to, rate) VALUES (1, 'GEL', 'GEL', 1);
INSERT INTO public.currency (id, ccy_from, ccy_to, rate) VALUES (2, 'USD', 'GEL', 3.2);
INSERT INTO public.currency (id, ccy_from, ccy_to, rate) VALUES (3, 'EUR', 'GEL', 3.8);
INSERT INTO public.currency (id, ccy_from, ccy_to, rate) VALUES (4, 'GBP', 'GEL', 4.2);