insert into product (id, name, price, description, created_at) values (1, 'Kindle', 499.0, 'Conheça o novo Kindle', date_sub(sysdate(), interval 1 day));
insert into product (id, name, price, description, created_at) values (3, 'Câmera GoPro Hero 7', 1400.0, 'Desempenho 2x melhor.', date_sub(sysdate(), interval 1 day));

insert into client (id, name, cpf) values (1, 'Fernando Medeiros', '111111111');
insert into client (id, name, cpf) values (2, 'Marcos Mariano', '222222222');

insert into client_detail(client_id, gender, birth_date) values (1, 'MAN', date_sub(sysdate(), interval 27 year));
insert into client_detail(client_id, gender, birth_date) values (2, 'MAN', date_sub(sysdate(), interval 30 year));

insert into purchase (id, client_id, purchase_date, total, status) values (1, 1, sysdate(), 998.0, 'WAITING');
insert into purchase (id, client_id, purchase_date, total, status) values (2, 1, sysdate(), 499.0, 'WAITING');

insert into purchase_item (purchase_id, product_id, product_price, quantity) values (1, 1, 499, 2);
insert into purchase_item (purchase_id, product_id, product_price, quantity) values (2, 1, 499, 1);

insert into payment (purchase_id, payment_status, card_number, payment_type) values (2, 'IN_PROCESS', '123', 'CredCard');
--insert into payment_by_credcard (purchase_id, card_number) values (2, '123')

insert into category (name) values ("Electronics");