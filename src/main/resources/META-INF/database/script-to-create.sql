create table testing (id integer not null auto_increment, primary key (id)) engine=InnoDB;
create table store_product (id integer not null auto_increment, name varchar(100), description longtext, price decimal(19, 2), created_at datetime(6), updated_at datetime(6), photo longblob, primary key (id)) engine=InnoDB;
create table product_ecm (prd_id integer not null auto_increment, prd_name varchar(100), prd_description longtext, prd_price decimal(19, 2), prd_created_at datetime(6), prd_updated_at datetime(6), prd_photo longblob, primary key (prd_id)) engine=InnoDB;
create table product_erp (id integer not null auto_increment, name varchar(100), description longtext, price decimal(19, 2), primary key (id)) engine=InnoDB;
create table ecm_category (cat_id integer not null auto_increment, cat_name varchar(100), cat_category_parent_id integer, primary key (cat_id)) engine=InnoDB;

create function above_media_billing(myValue double) returns boolean reads sql data return myValue > (select avg(total) from purchase);
SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));

create procedure search_name_product(in product_id int, out product_name varchar(255))
begin
    select name into product_name from product where id = product_id;
end

create procedure sold_up-from_media(in ano integer)
begin
    select cli.* from client cli
        join purchase pur on pur.client_id = cli.id
    where ped.status = 'PAID_OUT'
        and year(pur.purchase_date) = pYear
    group by pur.client_id
    having sum(pur.total) >= (
        select avg(total_per_client.sum_total)
            from (
                select sum(pur2.total) sum_total
                    from purchase ped2
                    where pur.status = 'PAID_OUT'
                        and year(pur2.purchase_date) = pYear
                    group by pur2.client_id)
                        as total_per_client);
end

create procedure adjust_price_product(in product_id int, in perceptual_adjust double, out price_adjusted double)
begin
    declare product_price double;
    select price into product_price
        from product
            where id = product_id;
     set price_adjusted = product_price + (product_price * perceptual_adjust);
     update product set price = price_adjusted
        where id = product_id;
end