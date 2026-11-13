create table if not exists tarifa (
    id serial primary key,
    nombre varchar,
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
    rol varchar not null default 'ABONADO',
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

-- 1. TARIFAS
INSERT INTO tarifa (nombre, tope_maximo, min_cortesia, precio) VALUES
    ('Básica', 60,   15, 1.50),
    ('Estándar', 120,  20, 2.00),
    ('Extendida', 240,  30, 3.50),
    ('Jornada', 480,  45, 5.00),
    ('Día', 1440, 60, 8.00);

-- 2. PLAZAS (200 en total: 28 ocupadas, 10 bloqueadas, 162 libres)
INSERT INTO plaza (numero, planta, tipo, estado) VALUES
    -- PLANTA 0
    (1, 0, 0, 0), (2, 0, 0, 0), (3, 0, 0, 0), (4, 0, 0, 0), (5, 0, 0, 0),
    (6, 0, 0, 0), (7, 0, 0, 0), (8, 0, 0, 0), (9, 0, 0, 0), (10, 0, 0, 0),
    (11, 0, 0, 0), (12, 0, 0, 0), (13, 0, 0, 0), (14, 0, 0, 0), (15, 0, 0, 0),
    (16, 0, 0, 0), (17, 0, 0, 0), (18, 0, 0, 0), (19, 0, 0, 0), (20, 0, 0, 0),
    (21, 0, 0, 0), (22, 0, 0, 0), (23, 0, 0, 0), (24, 0, 0, 0), (25, 0, 0, 0),
    (26, 0, 0, 0), (27, 0, 0, 0), (28, 0, 0, 0), (29, 0, 0, 0), (30, 0, 0, 0),
    (31, 0, 0, 0), (32, 0, 0, 0), (33, 0, 0, 0), (34, 0, 0, 0), (35, 0, 0, 0),
    (36, 0, 0, 0), (37, 0, 0, 0), (38, 0, 0, 0), (39, 0, 0, 0), (40, 0, 0, 0),
    (41, 0, 0, 1), (42, 0, 0, 1), (43, 0, 0, 1), (44, 0, 0, 1), (45, 0, 0, 1),
    (46, 0, 0, 2), (47, 0, 0, 2), (48, 0, 0, 0), (49, 0, 0, 0), (50, 0, 0, 0),
    (51, 0, 1, 0), (52, 0, 1, 0), (53, 0, 1, 0), (54, 0, 1, 0), (55, 0, 1, 0),
    (56, 0, 1, 1), (57, 0, 1, 1), (58, 0, 1, 2), (59, 0, 1, 0), (60, 0, 1, 0),
    (61, 0, 2, 0), (62, 0, 2, 0), (63, 0, 2, 0), (64, 0, 2, 0), (65, 0, 2, 0),
    (66, 0, 2, 1), (67, 0, 2, 1), (68, 0, 2, 2), (69, 0, 2, 0), (70, 0, 2, 0),

    -- PLANTA -1
    (71, -1, 0, 0), (72, -1, 0, 0), (73, -1, 0, 0), (74, -1, 0, 0), (75, -1, 0, 0),
    (76, -1, 0, 0), (77, -1, 0, 0), (78, -1, 0, 0), (79, -1, 0, 0), (80, -1, 0, 0),
    (81, -1, 0, 0), (82, -1, 0, 0), (83, -1, 0, 0), (84, -1, 0, 0), (85, -1, 0, 0),
    (86, -1, 0, 0), (87, -1, 0, 0), (88, -1, 0, 0), (89, -1, 0, 0), (90, -1, 0, 0),
    (91, -1, 0, 1), (92, -1, 0, 1), (93, -1, 0, 1), (94, -1, 0, 1), (95, -1, 0, 1),
    (96, -1, 0, 1), (97, -1, 0, 1), (98, -1, 0, 1), (99, -1, 0, 1), (100, -1, 0, 1),
    (101, -1, 0, 2), (102, -1, 0, 2), (103, -1, 0, 2), (104, -1, 0, 0), (105, -1, 0, 0),
    (106, -1, 0, 0), (107, -1, 0, 0), (108, -1, 0, 0), (109, -1, 0, 0), (110, -1, 0, 0),
    (111, -1, 0, 0), (112, -1, 0, 0), (113, -1, 0, 0), (114, -1, 0, 0), (115, -1, 0, 0),
    (116, -1, 0, 0), (117, -1, 0, 0), (118, -1, 0, 0), (119, -1, 0, 0), (120, -1, 0, 0),
    (121, -1, 1, 0), (122, -1, 1, 0), (123, -1, 1, 0), (124, -1, 1, 0), (125, -1, 1, 0),
    (126, -1, 1, 1), (127, -1, 1, 1), (128, -1, 1, 2), (129, -1, 1, 0), (130, -1, 1, 0),
    (131, -1, 2, 0), (132, -1, 2, 0), (133, -1, 2, 0), (134, -1, 2, 0), (135, -1, 2, 0),
    (136, -1, 2, 1), (137, -1, 2, 1), (138, -1, 2, 2), (139, -1, 2, 0), (140, -1, 2, 0),

    -- PLANTA -2
    (141, -2, 0, 0), (142, -2, 0, 0), (143, -2, 0, 0), (144, -2, 0, 0), (145, -2, 0, 0),
    (146, -2, 0, 0), (147, -2, 0, 0), (148, -2, 0, 0), (149, -2, 0, 0), (150, -2, 0, 0),
    (151, -2, 0, 0), (152, -2, 0, 0), (153, -2, 0, 0), (154, -2, 0, 0), (155, -2, 0, 0),
    (156, -2, 0, 0), (157, -2, 0, 0), (158, -2, 0, 0), (159, -2, 0, 0), (160, -2, 0, 0),
    (161, -2, 0, 0), (162, -2, 0, 0), (163, -2, 0, 0), (164, -2, 0, 0), (165, -2, 0, 0),
    (166, -2, 0, 1), (167, -2, 0, 1), (168, -2, 0, 1), (169, -2, 0, 1), (170, -2, 0, 1),
    (171, -2, 0, 1), (172, -2, 0, 1), (173, -2, 0, 1), (174, -2, 0, 1), (175, -2, 0, 1),
    (176, -2, 0, 2), (177, -2, 0, 2), (178, -2, 0, 2), (179, -2, 0, 0), (180, -2, 0, 0),
    (181, -2, 1, 0), (182, -2, 1, 0), (183, -2, 1, 0), (184, -2, 1, 0), (185, -2, 1, 0),
    (186, -2, 1, 0), (187, -2, 1, 0), (188, -2, 1, 0), (189, -2, 1, 0), (190, -2, 1, 0),
    (191, -2, 2, 0), (192, -2, 2, 0), (193, -2, 2, 0), (194, -2, 2, 0), (195, -2, 2, 0),
    (196, -2, 2, 0), (197, -2, 2, 0), (198, -2, 2, 0), (199, -2, 2, 2), (200, -2, 2, 2);

-- 3. ABONADOS
INSERT INTO abonado (email, nombre, matricula, fecha_inicio, fecha_fin) VALUES
    ('ana.lopez@email.com',     'Ana López',      '1234ABC', '2024-01-01 00:00:00', '2024-12-31 23:59:59'),
    ('bruno.garcia@email.com',  'Bruno García',   '5678DEF', '2024-02-01 00:00:00', '2025-01-31 23:59:59'),
    ('carla.martin@email.com',  'Carla Martín',   '9012GHI', '2024-03-15 00:00:00', '2025-03-14 23:59:59'),
    ('david.ruiz@email.com',    'David Ruiz',     '3456JKL', '2024-04-01 00:00:00', '2025-03-31 23:59:59'),
    ('elena.sanz@email.com',    'Elena Sanz',     '7890MNO', '2024-05-10 00:00:00', '2025-05-09 23:59:59'),
    ('fran.torres@email.com',   'Fran Torres',    '2345PQR', '2024-06-01 00:00:00', '2025-05-31 23:59:59'),
    ('gloria.vega@email.com',   'Gloria Vega',    '6789STU', '2024-07-01 00:00:00', '2025-06-30 23:59:59'),
    ('hugo.blanco@email.com',   'Hugo Blanco',    '0123VWX', '2024-08-01 00:00:00', '2025-07-31 23:59:59'),
    ('irene.castro@email.com',  'Irene Castro',   '4567YZA', '2024-09-01 00:00:00', '2025-08-31 23:59:59'),
    ('javier.nieto@email.com',  'Javier Nieto',   '8901BCD', '2024-10-01 00:00:00', '2025-09-30 23:59:59');

-- 4. USUARIOS
INSERT INTO usuario (nombre, contrasenya, rol) VALUES
    ('ana_lopez',      '$2a$10$8.Oq4vX2r4J5Q1l2k3N4uO5p6Q7r8S9t0U1v2W3x4Y5z6A7b8C9d0', 'ABONADO'),
    ('bruno_garcia',   '$2a$10$8.Oq4vX2r4J5Q1l2k3N4uO5p6Q7r8S9t0U1v2W3x4Y5z6A7b8C9d0', 'ABONADO'),
    ('carla_martin',   '$2a$10$8.Oq4vX2r4J5Q1l2k3N4uO5p6Q7r8S9t0U1v2W3x4Y5z6A7b8C9d0', 'ABONADO'),
    ('david_ruiz',     '$2a$10$8.Oq4vX2r4J5Q1l2k3N4uO5p6Q7r8S9t0U1v2W3x4Y5z6A7b8C9d0', 'OPERADOR'),
    ('elena_sanz',     '$2a$10$8.Oq4vX2r4J5Q1l2k3N4uO5p6Q7r8S9t0U1v2W3x4Y5z6A7b8C9d0', 'OPERADOR'),
    ('fran_torres',    '$2a$10$8.Oq4vX2r4J5Q1l2k3N4uO5p6Q7r8S9t0U1v2W3x4Y5z6A7b8C9d0', 'OPERADOR'),
    ('gloria_vega',    '$2a$10$8.Oq4vX2r4J5Q1l2k3N4uO5p6Q7r8S9t0U1v2W3x4Y5z6A7b8C9d0', 'ADMIN'),
    ('hugo_blanco',    '$2a$10$8.Oq4vX2r4J5Q1l2k3N4uO5p6Q7r8S9t0U1v2W3x4Y5z6A7b8C9d0', 'ABONADO'),
    ('irene_castro',   '$2a$10$8.Oq4vX2r4J5Q1l2k3N4uO5p6Q7r8S9t0U1v2W3x4Y5z6A7b8C9d0', 'ABONADO'),
    ('javier_nieto',   '$2a$10$8.Oq4vX2r4J5Q1l2k3N4uO5p6Q7r8S9t0U1v2W3x4Y5z6A7b8C9d0', 'ABONADO');

-- 5. TICKETS HISTÓRICOS (Ya cerrados)
-- Importante: Como es histórico, aquí sí rellenamos los campos NOT NULL (minutos, importe)
INSERT INTO ticket (hora_entrada, hora_salida, minutos_totales, estado, importe, descuento, importe_descuento, matricula, id_tarifa, id_plaza) VALUES
    ('2024-01-01 08:00:00', '2024-01-01 09:30:00',  90,  1,  3.00, 0,  3.00, '1234ABC', 1, 1),
    ('2024-01-01 09:15:00', '2024-01-01 11:15:00', 120,  1,  4.00, 10, 3.60, '5678DEF', 2, 2),
    ('2024-01-01 10:00:00', '2024-01-01 12:00:00', 120,  1,  4.00, 0,  4.00, '9012GHI', 2, 3),
    ('2024-01-01 11:30:00', '2024-01-01 14:30:00', 180,  1,  6.00, 20, 4.80, '3456JKL', 3, 4),
    ('2024-01-01 12:00:00', '2024-01-01 20:00:00', 480,  1, 10.00, 0, 10.00, '7890MNO', 4, 5);

-- 6. TICKETS ABIERTOS (Coches actualmente dentro)
-- IMPORTANTE: hora_salida es NULL, pero minutos, estado e importe NO pueden ser NULL.
-- Les ponemos 0 de momento, cuando el coche salga, tu backend actualizará estos campos.
INSERT INTO ticket (hora_entrada, hora_salida, minutos_totales, estado, importe, descuento, importe_descuento, matricula, id_tarifa, id_plaza) VALUES
    ('2026-11-01 08:00:00', NULL, 0, 0, 0.00, 0, 0.00, '1111AAA', 1, 41),
    ('2026-11-01 08:15:00', NULL, 0, 0, 0.00, 0, 0.00, '2222BBB', 1, 42),
    ('2026-11-01 08:30:00', NULL, 0, 0, 0.00, 0, 0.00, '3333CCC', 1, 43),
    ('2026-11-01 08:45:00', NULL, 0, 0, 0.00, 0, 0.00, '4444DDD', 1, 44),
    ('2026-11-01 09:00:00', NULL, 0, 0, 0.00, 0, 0.00, '5555EEE', 1, 45),
    ('2026-11-01 09:15:00', NULL, 0, 0, 0.00, 0, 0.00, '6666FFF', 1, 56),
    ('2026-11-01 09:30:00', NULL, 0, 0, 0.00, 0, 0.00, '7777GGG', 1, 57),
    ('2026-11-01 09:45:00', NULL, 0, 0, 0.00, 0, 0.00, '8888HHH', 1, 66),
    ('2026-11-01 10:00:00', NULL, 0, 0, 0.00, 0, 0.00, '9999III', 1, 67),
    ('2026-11-01 10:15:00', NULL, 0, 0, 0.00, 0, 0.00, '0000JJJ', 1, 91),
    ('2026-11-01 10:30:00', NULL, 0, 0, 0.00, 0, 0.00, '1111KKK', 1, 92),
    ('2026-11-01 10:45:00', NULL, 0, 0, 0.00, 0, 0.00, '2222LLL', 1, 93),
    ('2026-11-01 11:00:00', NULL, 0, 0, 0.00, 0, 0.00, '3333MMM', 1, 94),
    ('2026-11-01 11:15:00', NULL, 0, 0, 0.00, 0, 0.00, '4444NNN', 1, 95),
    ('2026-11-01 11:30:00', NULL, 0, 0, 0.00, 0, 0.00, '5555OOO', 1, 96),
    ('2026-11-01 11:45:00', NULL, 0, 0, 0.00, 0, 0.00, '6666PPP', 1, 97),
    ('2026-11-01 12:00:00', NULL, 0, 0, 0.00, 0, 0.00, '7777QQQ', 1, 98),
    ('2026-11-01 12:15:00', NULL, 0, 0, 0.00, 0, 0.00, '8888RRR', 1, 99),
    ('2026-11-01 12:30:00', NULL, 0, 0, 0.00, 0, 0.00, '9999SSS', 1, 100),
    ('2026-11-01 12:45:00', NULL, 0, 0, 0.00, 0, 0.00, '0000TTT', 1, 126),
    ('2026-11-01 13:00:00', NULL, 0, 0, 0.00, 0, 0.00, '1111UUU', 1, 127),
    ('2026-11-01 13:15:00', NULL, 0, 0, 0.00, 0, 0.00, '2222VVV', 1, 136),
    ('2026-11-01 13:30:00', NULL, 0, 0, 0.00, 0, 0.00, '3333WWW', 1, 137),
    ('2026-11-01 13:45:00', NULL, 0, 0, 0.00, 0, 0.00, '4444XXX', 1, 166),
    ('2026-11-01 14:00:00', NULL, 0, 0, 0.00, 0, 0.00, '5555YYY', 1, 167),
    ('2026-11-01 14:15:00', NULL, 0, 0, 0.00, 0, 0.00, '6666ZZZ', 1, 168),
    ('2026-11-01 14:30:00', NULL, 0, 0, 0.00, 0, 0.00, '7777AAA', 1, 169),
    ('2026-11-01 14:45:00', NULL, 0, 0, 0.00, 0, 0.00, '8888BBB', 1, 170);

-- 7. PROMOCIONES (Apuntan a tickets reales de la tabla)
INSERT INTO promocion (nombre, min_descuento, tipo, id_ticket) VALUES
    ('Descuento Bienvenida',      15, 0,  2),
    ('Promo Fin de Semana',       20, 1,  4),
    ('Descuento Abonado',         25, 2,  5),
    ('Promo Nocturna',            10, 0,  1),
    ('Descuento Fidelidad',       20, 2,  3);

-- 8. TICKETS DE ABONADOS
-- Relacionamos los tickets históricos con sus abonados
INSERT INTO ticket_abonado (id_ticket, id_abonado) VALUES
    ( 1,  1),  -- 1234ABC → Ana López
    ( 2,  2),  -- 5678DEF → Bruno García
    ( 3,  3),  -- 9012GHI → Carla Martín
    ( 4,  4),  -- 3456JKL → David Ruiz
    ( 5,  5);  -- 7890MNO → Elena Sanz
