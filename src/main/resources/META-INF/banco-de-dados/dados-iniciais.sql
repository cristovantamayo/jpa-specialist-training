insert into product (id, name, price, description, created_at) values (1, 'Kindle', 499.0, 'Conheça o novo Kindle', date_sub(sysdate(), interval 1 day));
insert into product (id, name, price, description, created_at) values (3, 'Câmera GoPro Hero 7', 1400.0, 'Desempenho 2x melhor.', date_sub(sysdate(), interval 1 day));

insert into client (id, name, gender) values (1, 'Fernando Medeiros', 'MAN');
insert into client (id, name, gender) values (2, 'Marcos Mariano', 'MAN');

insert into purchase (id, client_id, purchase_date, total, status) values (1, 1, sysdate(), 100.0, 'WAITING');

insert into purchase_item (id, purchase_id, product_id, product_price, quantity) values (1, 1, 1, 5.0, 2);

insert into category (name) values ("Electronics");