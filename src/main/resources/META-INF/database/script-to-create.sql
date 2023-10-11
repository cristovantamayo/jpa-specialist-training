create table testing (id integer not null auto_increment, primary key (id)) engine=InnoDB;

create table category (id integer not null auto_increment, name varchar(100) not null, parent_category_id integer, primary key (id)) engine=InnoDB;
create table client (id integer not null auto_increment, cpf varchar(14) not null, name varchar(100) not null, primary key (id)) engine=InnoDB;
create table client_contact (client_id integer not null, description varchar(255), type varchar(255) not null, primary key (client_id, type)) engine=InnoDB;
create table client_detail (birth_date date, gender varchar(255) not null, client_id integer not null, primary key (client_id) ) engine=InnoDB;
create table invoice (purchase_id integer not null, issue_date datetime(6) not null, xml longblob not null, primary key (purchase_id) ) engine=InnoDB;
create table payment (payment_type varchar(31) not null, purchase_id integer not null, payment_status varchar(20) not null, bar_code varchar(100), card_number varchar(50) not null, primary key (purchase_id) ) engine=InnoDB;
create table product (id integer not null auto_increment, created_at datetime(6) not null, description longtext, name varchar(100) not null, photo blob, price decimal(19,2), updated_at datetime(6), primary key (id) ) engine=InnoDB;
create table product_attribute (product_id integer not null, name varchar(100) not null, value varchar(255)) engine=InnoDB;
create table product_category (product_id integer not null,category_id integer not null) engine=InnoDB;
create table product_tag (product_id integer not null, tag varchar(50) not null ) engine=InnoDB;
create table purchase (id integer not null auto_increment, cep varchar(9), city varchar(50), complement varchar(50), neighborhood varchar(50), number varchar(10), state varchar(2), street varchar(100),  purchase_date datetime(6) not null, purchase_due_date datetime(6), status varchar(20) not null, total decimal(19,2) not null, updated_at datetime(6), client_id integer not null, primary key (id)) engine=InnoDB;
create table purchase_item (product_id integer not null, purchase_id integer not null, product_price decimal(19,2) not null, quantity integer not null, primary key (product_id, purchase_id) ) engine=InnoDB;
create table stock (id integer not null auto_increment, quantity integer, product_id integer not null, primary key (id)) engine=InnoDB;

create index idx_category_name on category (name);
create index idx_name on client (name);
create index idx_product_name on product (name);

alter table category add constraint unq_category_name unique (name);
alter table client add constraint unq_cpf unique (cpf);
alter table product add constraint unq_product_name unique (name);
alter table stock add constraint unq_stock_product unique (product_id);
alter table category  add constraint fk_category_parent_category foreign key (parent_category_id) references category (id);
alter table client_contact add constraint fk_client_contacts foreign key (client_id) references client (id);
alter table client_detail add constraint fk_client_detail_client foreign key (client_id) references client (id);
alter table invoice add constraint fk_invoice_purchase foreign key (purchase_id) references purchase (id);
alter table payment add constraint fk_payment_purchase foreign key (purchase_id)references purchase (id);
alter table product_attribute add constraint fk_product_attribute foreign key (product_id)references product (id);
alter table product_category add constraint fk_product_category_category foreign key (category_id) references category (id);
alter table product_category add constraint fk_product_category_product foreign key (product_id) references product (id);
alter table product_tag add constraint fk_product_tags foreign key (product_id) references product (id);
alter table purchase add constraint fk_purchase_client foreign key (client_id) references client (id);
alter table purchase_item add constraint fk_purchase_item_product foreign key (product_id) references product (id);
alter table purchase_item add constraint fk_purchase_Item_purchase foreign key (purchase_id) references purchase (id);
alter table stock add constraint fk_stock_product foreign key (product_id) references product (id);