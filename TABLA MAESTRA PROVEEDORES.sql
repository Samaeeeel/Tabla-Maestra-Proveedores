CREATE DATABASE COMERCIAL2;
USE COMERCIAL2;

create table PROVEEDORES (
   PRVCODIGO            CHAR(7)              not null,
   PRVNOMBRE            CHAR(30)             not null,
   PRVIDENTIFICACION    CHAR(10)             not null,
   PRVDIRECCION         CHAR(60)             not null,
   PRVTELEFONO          CHAR(12)             null,
   PRVCELULAR           CHAR(10)             null,
   PRVEMAIL             CHAR(60)             null,
   PRVTIPO              CHAR(3)              not null,
   PRVSTATUS            CHAR(3)              not null,
   FOTO                 LONGBLOB                 null,
   primary key (PRVCODIGO),
   unique (PRVIDENTIFICACION),
   check (PRVTIPO in ('NAT', 'JUR')),
   check (PRVSTATUS in ('ACT', 'INA')),
   check (PRVEMAIL like '%@%.%'),
   check (PRVNOMBRE REGEXP '^[A-Za-zÁÉÍÓÚáéíóúÑñ. ]+$'),
   check (PRVCELULAR REGEXP '^09[0-9]{8}$'),
   check (PRVIDENTIFICACION REGEXP '^[0-9]+$'),
   check (PRVDIRECCION REGEXP '^[A-Za-z0-9ÁÉÍÓÚáéíóúÑñ\\-./, ]+$'),
   check (PRVTELEFONO REGEXP '^0[1-9]-[0-9]{7}$')
);

insert into PROVEEDORES (PRVCODIGO, PRVNOMBRE, PRVIDENTIFICACION, PRVDIRECCION, PRVTELEFONO, PRVCELULAR, PRVEMAIL, PRVTIPO, PRVSTATUS) 
values 
('PRV-010', 'CORPORACION FAVORITA C.A.', '1702996501', 'Sangolqui Av. 6 de Diciembre y Julio Moreno Quito - Ecuador', '02-2996500', '0992996500', 'ventas@favorita.com.ec', 'JUR', 'ACT');
insert into PROVEEDORES (PRVCODIGO, PRVNOMBRE, PRVIDENTIFICACION, PRVDIRECCION, PRVTELEFONO, PRVCELULAR, PRVEMAIL, PRVTIPO, PRVSTATUS) 
values 
('PRV-020', 'CORPORACION EL ROSADO S.A.', '0702996502', 'Centro Av. 9 de Octubre 729 y Boyaca Guayaquil - Ecuador', '02-2980980', '0992996500', 'ventas@elrosado.com.ec', 'JUR', 'ACT');
insert into PROVEEDORES (PRVCODIGO, PRVNOMBRE, PRVIDENTIFICACION, PRVDIRECCION, PRVTELEFONO, PRVCELULAR, PRVEMAIL, PRVTIPO, PRVSTATUS) 
values 
('PRV-030', 'INDUSTRIAL PESQUERA SANTA', '1402996503', 'Via Daule Km 5 1/2 y Calle Septima Guayaquil - Ecuador', '04-2322000', '0992996500', 'ventas@santa_priscila.com.ec', 'JUR', 'ACT');
insert into PROVEEDORES (PRVCODIGO, PRVNOMBRE, PRVIDENTIFICACION, PRVDIRECCION, PRVTELEFONO, PRVCELULAR, PRVEMAIL, PRVTIPO, PRVSTATUS) 
values 
('PRV-040', 'ECUACORRIENTE S.A.', '0602996504', 'Norte Km. 16 1/2, via a Daule, calle Cobre por Pascuales', '04-6005238', '0992996500', 'ventas@ecuacorriente.ec', 'JUR', 'ACT'),
('PRV-050', 'DINADEC S.A.', '1902996505', 'Norte Km. 16 1/2, via a Daule, calle Cobre por Pascuales', '04-5004040', '0992996500', 'ventas@danec.ec', 'JUR', 'ACT'),
('PRV-060', 'DISTRIBUIDORA FARMACEUTICA', '2102996506', 'Cdla. Santa Leonor, Mz. 6, solar 17 Guayaquil - Ecuador', '04-5004040', '0992996500', 'ventas@difare.com.ec', 'JUR', 'ACT'),
('PRV-070', 'COMERCIAL ESTRADA', '1502996507', 'Av. de la Prensa y Occidental Quito - Ecuador', '02-2796500', '0992796500', 'ventas@estrada.com.ec', 'JUR', 'ACT'),
('PRV-080', 'FERRETERIA CENTRO', '1602996508', 'Calle 12 de Octubre y Tarqui Cuenca - Ecuador', '07-2796500', '0992796500', 'ventas@ferrecentr.com.ec', 'JUR', 'ACT'),
('PRV-090', 'IMPORTADORA ANDINA', '1702996509', 'Av. 10 de Agosto y Colon Quito - Ecuador', '02-2696500', '0992696500', 'ventas@importandina.com.ec', 'JUR', 'ACT'),
('PRV-100', 'CONSTRUCCIONES MODERNAS', '1802996510', 'Av. America y Naciones Unidas Quito - Ecuador', '02-2596500', '0992596500', 'ventas@construmod.com.ec', 'JUR', 'ACT');
