create table cars
(
    id    serial primary key,
    brand varchar(20) not null,
    model varchar(20) not null,
    price integer     not null
);

create table persons
(
    id            serial primary key,
    name          varchar(20) not null,
    age           integer     not null,
    driverLicense boolean     not null,
    car_id        integer references cars (id)
);

insert into cars(brand, model, price)
values ('lada', '2109', 50000),
       ('uaz', 'patriot', 100000),
       ('lada', 'vesta', 85000);

insert into persons(name, age, driverLicense, car_id)
values ('Andrei Vladimirovich', '38', true, 1),
       ('Vasili Petrovich', '42', true, 2),
       ('Sergei Dmitrievich', '27', true, 3);
       ('Petr Andreevich', '31', true, 3);


