create table if not exists tarifa (
    id serial primary key,
    tope_maximo int,
    fecha timestamp not null default current_timestamp,
    min_cortesia int not null,
    precio decimal not null
);

create table if not exists plaza (
    id serial primary key,
    numero int not null,
    planta int not null,
    tipo int not null,
    estado int not null
);

create table if not exists ticket (
    id serial primary key,
    hora_entrada timestamp not null default current_timestamp,
    hora_salida timestamp,
    minutos_totales int not null,
    estado int not null,
    importe decimal not null,
    descuento int,
    importe_descuento decimal,
    matricula varchar not null,
    id_tarifa int,
    id_plaza int,
    constraint fk_ticket_tarifa foreign key (id_tarifa) references tarifa(id),
    constraint fk_ticket_plaza foreign key (id_plaza) references plaza(id)
);

create table if not exists promocion (
    id serial primary key,
    nombre varchar not null,
    min_descuento int not null,
    tipo int not null,
    id_ticket int,
    constraint fk_promocion_ticket foreign key (id_ticket) references ticket(id)
);

create table if not exists abonado (
    id serial primary key,
    email varchar not null,
    nombre varchar not null,
    matricula varchar not null,
    fecha_inicio timestamp not null,
    fecha_fin timestamp not null
);

create table if not exists usuario (
    id serial primary key,
    nombre varchar not null,
    contrasenya varchar not null,
    id_abonado int,
    constraint fk_usuario_abonado foreign key (id_abonado) references abonado(id)
);

create table if not exists ticket_abonado (
    id_ticket int not null,
    id_abonado int not null,
    primary key (id_ticket, id_abonado),
    constraint fk_ticket_abonado_ticket foreign key (id_ticket) references ticket(id),
    constraint fk_ticket_abonado_abonado foreign key (id_abonado) references abonado(id)
);