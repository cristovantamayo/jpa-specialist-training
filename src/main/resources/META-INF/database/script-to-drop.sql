 drop table if exists testing;

alter table stock drop foreign key fk_stock_product;
alter table purchase_item drop foreign key fk_purchase_item_product;
alter table purchase_item drop foreign key fk_purchase_Item_purchase;
alter table product_attribute drop foreign key fk_product_attribute;
alter table product_category drop foreign key fk_product_category_category;
alter table product_category drop foreign key fk_product_category_product;
alter table product_tag drop foreign key fk_product_tags;
alter table category drop foreign key fk_category_parent_category;
alter table client_contact drop foreign key fk_client_contacts;
alter table client_detail drop foreign key fk_client_detail_client;
alter table invoice drop foreign key fk_invoice_purchase;
alter table payment drop foreign key fk_payment_purchase;
alter table purchase drop foreign key fk_purchase_client;

drop table if exists category;
drop table if exists client;
drop table if exists client_contact;
drop table if exists client_detail;
drop table if exists invoice;
drop table if exists payment;
drop table if exists product;
drop table if exists product_attribute;
drop table if exists product_category;
drop table if exists product_tag;
drop table if exists purchase;
drop table if exists purchase_item;
drop table if exists stock;