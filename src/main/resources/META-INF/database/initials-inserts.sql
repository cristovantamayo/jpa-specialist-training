insert into product (id, name, price, description, created_at, version, active) values (1, 'Kindle', 799.0, 'Conheça o novo Kindle', date_sub(sysdate(), interval 1 day), 0, 'YES');
insert into product (id, name, price, description, created_at, version, active) values (3, 'Câmera GoPro Hero 7', 1400.0, 'Desempenho 2x melhor.', date_sub(sysdate(), interval 1 day), 0, 'YES');
insert into product (id, name, price, description, created_at, version, active) values (4, 'Câmera Canon 80D', 3500.0, 'O melhor ajuste de foco.', date_sub(sysdate(), interval 2 day), 0, 'YES');
insert into product (id, name, price, description, created_at, version, active) values (5, 'Lapel Microphone', 50.0, 'Top Product', date_sub(sysdate(), interval 2 day), 0, "NO");

insert into client (id, name, cpf, version) values (1, 'Fernando Medeiros', '111111111', 0);
insert into client (id, name, cpf, version) values (2, 'Marcos Mariano', '222222222', 0);

insert into client_detail (client_id, gender, birth_date) values (1, 'MAN', date_sub(sysdate(), interval 27 year));
insert into client_detail (client_id, gender, birth_date) values (2, 'MAN', date_sub(sysdate(), interval 30 year));

insert into purchase (id, client_id, purchase_date, total, version, status) values (1, 1, date_sub(sysdate(), interval 32 day), 2398.0, 0, 'PAID_OUT');
insert into purchase (id, client_id, purchase_date, total, version, status) values (2, 1, date_sub(sysdate(), interval 5 day), 499.0, 0, 'WAITING');
insert into purchase (id, client_id, purchase_date, total, version, status) values (3, 1, date_sub(sysdate(), interval 4 day), 3500.0, 0, 'PAID_OUT');
insert into purchase (id, client_id, purchase_date, total, version, status) values (4, 2, date_sub(sysdate(), interval 2 day), 499.0, 0, 'PAID_OUT');
insert into purchase (id, client_id, purchase_date, total, version, status) values (5, 1, date_sub(sysdate(), interval 2 day), 799.0, 0, 'PAID_OUT');
insert into purchase (id, client_id, purchase_date, total, version, status) values (6, 2, sysdate(), 799.0, 'WAITING');

insert into purchase_item (purchase_id, product_id, product_price, quantity, version) values (1, 1, 499, 2, 0);
insert into purchase_item (purchase_id, product_id, product_price, quantity, version) values (1, 3, 1400, 1, 0);
insert into purchase_item (purchase_id, product_id, product_price, quantity, version) values (2, 1, 499, 1, 0);
insert into purchase_item (purchase_id, product_id, product_price, quantity, version) values (3, 4, 3500, 1, 0);
insert into purchase_item (purchase_id, product_id, product_price, quantity, version) values (4, 1, 499, 1, 0);
insert into purchase_item (purchase_id, product_id, product_price, quantity, version) values (5, 1, 799, 1, 0);
insert into purchase_item (purchase_id, product_id, product_price, quantity, version) values (6, 1, 799, 1, 0);

insert into payment (purchase_id, version, payment_status, payment_type, card_number, bar_code) values (1, 0,'RECEIVED', 'CredCard', '0123', null);
insert into payment (purchase_id, version, payment_status, payment_type, card_number, bar_code) values (2, 0, 'IN_PROCESS', 'CredCard', '4567', null);
insert into payment (purchase_id, version, payment_status, payment_type, card_number, bar_code, due_date) values (3, 0, 'RECEIVED', 'BankSlip', null, '8910', date_sub(sysdate(), interval 2 day));
insert into payment (purchase_id, version, payment_status, payment_type, card_number, bar_code) values (4, 0, 'IN_PROCESS', 'CredCard', '1112', null);
insert into payment (purchase_id, version, payment_status, payment_type, card_number, bar_code, due_date) values (6, 0, 'IN_PROCESS', 'BankSlip', null, '456', date_add(sysdate(), interval 2 day));

insert into invoice (purchase_id, version, xml, issue_date) VALUES (2, 0, "<?xml></xml>", date_sub(sysdate(), interval 1 day));
insert into invoice (purchase_id, version, xml, issue_date) VALUES (1, 0, "<?xml></xml>", date_sub(sysdate(), interval 1 day));

insert into category (name, version) values ("Electronics", 0);
insert into category (name, version) values ("Books", 0);
insert into category (name, version) values ("Sports", 0);
insert into category (name, version) values ("Education", 0);
insert into category (name, version) values ("Health", 0);
insert into category (name, version) values ("Cleaner", 0);
insert into category (name, version) values ("Video Games", 0);
insert into category (name, version) values ('Câmeras', 0);

insert into product_category (product_id, category_id) values (3, 2);
insert into product_category (product_id, category_id) values (1, 1);
insert into product_category (product_id, category_id) values (4, 8);

insert into store_product (id, name, price, created_at, description) values (101, 'Kindle', 799.0, date_sub(sysdate(), interval 1 day), 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into store_product (id, name, price, created_at, description) values (103, 'Câmera GoPro Hero 7', 1500.0, date_sub(sysdate(), interval 1 day), 'Desempenho 2x melhor.');
insert into store_product (id, name, price, created_at, description) values (104, 'Câmera Canon 80D', 3500.0, sysdate(), 'O melhor ajuste de foco.');
insert into store_product (id, name, price, created_at, description) values (105, 'Microfone de Lapela', 50.0, sysdate(), 'Produto massa');

insert into product_ecm (prd_id, prd_name, prd_price, prd_created_at, prd_description) values (201, 'Kindle', 799.0, date_sub(sysdate(), interval 1 day), 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into product_ecm (prd_id, prd_name, prd_price, prd_created_at, prd_description) values (203, 'Câmera GoPro Hero 7', 1500.0, date_sub(sysdate(), interval 1 day), 'Desempenho 2x melhor.');
insert into product_ecm (prd_id, prd_name, prd_price, prd_created_at, prd_description) values (204, 'Câmera Canon 80D', 3500.0, sysdate(), 'O melhor ajuste de foco.');
insert into product_ecm (prd_id, prd_name, prd_price, prd_created_at, prd_description) values (205, 'Microfone de Lapela', 50.0, sysdate(), 'Produto massa');

insert into product_erp (id, name, price, description) values (301, 'Kindle', 799.0, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into product_erp (id, name, price, description) values (303, 'Câmera GoPro Hero 7', 1500.0, 'Desempenho 2x melhor.');
insert into product_erp (id, name, price, description) values (304, 'Câmera Canon 80D', 3500.0, 'O melhor ajuste de foco.');
insert into product_erp (id, name, price, description) values (305, 'Microfone de Lapela', 50.0, 'Produto massa');

insert into ecm_category (cat_id, cat_name) values (201, 'Eletrodomésticos');
insert into ecm_category (cat_id, cat_name) values (202, 'Livros');
insert into ecm_category (cat_id, cat_name) values (203, 'Esportes');
insert into ecm_category (cat_id, cat_name) values (204, 'Futebol');
insert into ecm_category (cat_id, cat_name) values (205, 'Natação');
insert into ecm_category (cat_id, cat_name) values (206, 'Notebooks');
insert into ecm_category (cat_id, cat_name) values (207, 'Smartphones');
insert into ecm_category (cat_id, cat_name) values (208, 'Câmeras');