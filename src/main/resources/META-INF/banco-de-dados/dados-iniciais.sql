insert into product (id, name, price, description, created_at) values (1, 'Kindle', 499.0, 'Conheça o novo Kindle', date_sub(sysdate(), interval 1 day));
insert into product (id, name, price, description, created_at) values (3, 'Câmera GoPro Hero 7', 1400.0, 'Desempenho 2x melhor.', date_sub(sysdate(), interval 1 day));

insert into client (id, name) values (1, 'Fernando Medeiros');
insert into client (id, name) values (2, 'Marcos Mariano');

insert into purchase (id, client_id, purchase_date, total, status) values (1, 1, sysdate(), 998.0, 'WAITING');
insert into purchase (id, client_id, purchase_date, total, status) values (2, 1, sysdate(), 499.0, 'WAITING');

insert into purchase_item (purchase_id, product_id, product_price, quantity) values (1, 1, 499, 2);
insert into purchase_item (purchase_id, product_id, product_price, quantity) values (2, 1, 499, 1);

insert into payment (purchase_id, payment_status, card_number, payment_type) values (2, 'IN_PROCESS', '123', 'CredCard');

insert into category (name) values ("Electronics");