create table passportDB (
    id serial primary key not null,
    series varchar(200),
    expired_date timestamp
);

