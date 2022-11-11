CREATE DATABASE chat;
use chat;
create table Usuario (
    id varchar(30) not null,
    clave varchar(30) not null,
    nombre varchar(30) not null,
    Primary Key (id)
);

insert into usuario
    (id, clave, nombre)
    values('001', '1111','Diego');
insert into usuario
    (id, clave, nombre)
    values('002', '2222', 'Jorge');
insert into usuario
    (id, clave, nombre)
    values('003', '3333', 'Sofia');
insert into usuario
    (id, clave, nombre)
    values('004', '4444', 'Susanne');