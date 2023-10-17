insert into product (id, name, price, description, created_at) values (1, 'Kindle', 799.0, 'Conheça o novo Kindle', date_sub(sysdate(), interval 1 day));
insert into product (id, name, price, description, created_at) values (3, 'Câmera GoPro Hero 7', 1400.0, 'Desempenho 2x melhor.', date_sub(sysdate(), interval 1 day));
insert into product (id, name, price, description, created_at) values (4, 'Câmera Canon 80D', 3500.0, 'O melhor ajuste de foco.', date_sub(sysdate(), interval 2 day));

insert into client (id, name, cpf) values (1, 'Fernando Medeiros', '111111111');
insert into client (id, name, cpf) values (2, 'Marcos Mariano', '222222222');

insert into client_detail (client_id, gender, birth_date) values (1, 'MAN', date_sub(sysdate(), interval 27 year));
insert into client_detail (client_id, gender, birth_date) values (2, 'MAN', date_sub(sysdate(), interval 30 year));

insert into purchase (id, client_id, purchase_date, total, status) values (1, 1, date_sub(sysdate(), interval 32 day), 2398.0, 'PAID_OUT');
insert into purchase (id, client_id, purchase_date, total, status) values (2, 1, date_sub(sysdate(), interval 5 day), 499.0, 'WAITING');
insert into purchase (id, client_id, purchase_date, total, status) values (3, 1, date_sub(sysdate(), interval 4 day), 3500.0, 'PAID_OUT');
insert into purchase (id, client_id, purchase_date, total, status) values (4, 2, date_sub(sysdate(), interval 2 day), 499.0, 'PAID_OUT');
insert into purchase (id, client_id, purchase_date, total, status) values (5, 1, date_sub(sysdate(), interval 2 day), 799.0, 'PAID_OUT');

insert into purchase_item (purchase_id, product_id, product_price, quantity) values (1, 1, 499, 2);
insert into purchase_item (purchase_id, product_id, product_price, quantity) values (1, 3, 1400, 1);
insert into purchase_item (purchase_id, product_id, product_price, quantity) values (2, 1, 499, 1);
insert into purchase_item (purchase_id, product_id, product_price, quantity) values (3, 4, 3500, 1);
insert into purchase_item (purchase_id, product_id, product_price, quantity) values (4, 1, 499, 1);
insert into purchase_item (purchase_id, product_id, product_price, quantity) values (5, 1, 799, 1);

insert into payment (purchase_id, payment_status, payment_type, card_number, bar_code) values (1, 'RECEIVED', 'CredCard', '0123', null);
insert into payment (purchase_id, payment_status, payment_type, card_number, bar_code) values (2, 'IN_PROCESS', 'CredCard', '4567', null);
insert into payment (purchase_id, payment_status, payment_type, card_number, bar_code) values (3, 'RECEIVED', 'Ticket', null, '8910');
insert into payment (purchase_id, payment_status, payment_type, card_number, bar_code) values (4, 'IN_PROCESS', 'CredCard', '1112', null);

insert into invoice (purchase_id, xml, issue_date) VALUES (2, "<?xml></xml>", date_sub(sysdate(), interval 1 day));

insert into category (name) values ("Electronics");
insert into category (name) values ("Books");
insert into category (name) values ("Sports");
insert into category (name) values ("Education");
insert into category (name) values ("Health");
insert into category (name) values ("Cleaner");
insert into category (name) values ("Video Games");
insert into category (name) values ('Câmeras');

insert into product_category (product_id, category_id) values (3, 1);
insert into product_category (product_id, category_id) values (1, 1);
insert into product_category (product_id, category_id) values (4, 8);