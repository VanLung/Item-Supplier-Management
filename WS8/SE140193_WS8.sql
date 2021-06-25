create database ManagingItems;


create table Suppliers(
			supCode nvarchar(5) not null primary key,
            supName nvarchar(30) ,
            address nvarchar(30) ,
			colloborating bit 
)

create table Items(
			itemCode nvarchar(5) not null primary key,
            itemName nvarchar(50) ,
            supCode nvarchar(5) ,
			unit nvarchar(10),
			price int ,
			supplying  bit
)

use ManagingItems
go
insert into Suppliers(supCode,supName,address,colloborating) values
(01,'HT-Hoang Tuan Co.','20 Quang Trung District 12','true'),
(02,'Coca Viet Nam Co.','10 Vo Van Ngan District 9','true'),
(03,'SamSung Viet Nam Co.','D1 High Tech Park District 9','true'),
(04,'Dien Quang Viet Nam','25 Dao Duy Tu, Phu Nhuan','true')

go 
insert Items( itemCode,itemName,supCode,unit,price,supplying) values
('E0001','Mouse PT','MT','block 10','30','true'),
('E0002','Keyboard PT','MT','block 10','40','true'),
('E0003','LCD','MT','1-unit','90','true'),
('E0004','Main Asus','HT','1-unit','78','true'),
('E0005','Main Gigabyte','HT','1-unit','67','false'),
('E0006','Laptop Core-i5','HT','1-unit','620','true'),
('E0007','Blank DVD','TA','block 100','43','true'),
('E0008','Blank CD','TA','block 100','15','true'),
('E0009','USB 2.0','TA','unit-1','10','false'),
('E011','USB ADA','TA','cái','12','false')