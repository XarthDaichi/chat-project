CREATE DATABASE chat;
use chat;
create table Usuario (
    id varchar(30) not null,
    clave varchar(30) not null,
    nombre varchar(30) not null,
    Primary Key (id)
);

create table Message (
    sender varchar(30) not null,
    receiver varchar(30) not null,
    note varchar(280) not null,
    orderEntered int not null,
    Primary Key (note)
);
ALTER TABLE Message ADD Foreign Key (sender) REFERENCES Usuario(id);
ALTER TABLE Message ADD Foreign Key (receiver) REFERENCES Usuario(id);

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