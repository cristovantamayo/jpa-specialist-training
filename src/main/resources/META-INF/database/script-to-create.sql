create table testing (id integer not null auto_increment, primary key (id)) engine=InnoDB;
create table store_product (id integer not null auto_increment, name varchar(100), description longtext, price decimal(19, 2), created_at datetime(6), updated_at datetime(6), photo longblob, primary key (id)) engine=InnoDB;
create table product_ecm (prd_id integer not null auto_increment, prd_name varchar(100), prd_description longtext, prd_price decimal(19, 2), prd_created_at datetime(6), prd_updated_at datetime(6), prd_photo longblob, primary key (prd_id)) engine=InnoDB;
create table product_erp (id integer not null auto_increment, name varchar(100), description longtext, price decimal(19, 2), primary key (id)) engine=InnoDB;
create function above_media_billing(myValue double) returns boolean reads sql data return myValue > (select avg(total) from purchase);
SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));