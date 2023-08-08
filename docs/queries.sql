create database vehicledb;

use vehicledb;

show tables;

create table vehicle(
 vehicle_name varchar(100),
 vehicle_type varchar(100) null,
 cc varchar(100) null,
 tyre_brand varchar(100) null,
 constraint pk_vehicle primary key(vehicle_name)
);

insert into vehicle (vehicle_name, vehicle_type, cc, tyre_brand) values
('KTM Duke 250','Motorcycle','250','MRF'),
('KTM RC 390','Motorcycle','373','Metzelller'),
('Kawasaki z900','Motorcycle','900','Bridgestone'),
('Yamaha mt09','Motorcycle','900','Pirelli');

select vehicle.* from vehicle;

update vehicle set vehicle_type = 'Old byke' where vehicle_name = 'Royal Enfield Bullet 350';