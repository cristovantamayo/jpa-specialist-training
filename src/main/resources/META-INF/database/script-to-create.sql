create table testing (id integer not null auto_increment, primary key (id)) engine=InnoDB;
create function above_media_billing(myValue double) returns boolean reads sql data return myValue > (select avg(total) from purchase);
SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));