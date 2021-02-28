insert into categories (id, name) values (1, 'Comida');
insert into categories (id, name) values (2, 'Bebida');
insert into categories (id, name) values (3, 'Complementos');

insert into products (id, name, price, category_id, image) values (1, 'Juice - Orange, Concentrate', 91, 2, 'http://dummyimage.com/139x103.bmp/5fa2dd/ffffff');
insert into products (id, name, price, category_id, image) values (2, 'Beef - Ground, Extra Lean, Fresh', 87, 1, 'http://dummyimage.com/206x125.bmp/cc0000/ffffff');
insert into products (id, name, price, category_id, image) values (3, 'Cheese - Parmesan Grated', 39, 1, 'http://dummyimage.com/133x134.bmp/dddddd/000000');
insert into products (id, name, price, category_id, image) values (4, 'Cups 10oz Trans', 67, 1, 'http://dummyimage.com/245x246.jpg/dddddd/000000');
insert into products (id, name, price, category_id, image) values (5, 'Wine - Beringer Founders Estate', 27, 2, 'http://dummyimage.com/139x103.bmp/5fa2dd/ffffff');
insert into products (id, name, price, category_id, image) values (6, 'Bread - Wheat Baguette', 82, 2, 'http://dummyimage.com/206x125.bmp/cc0000/ffffff');
insert into products (id, name, price, category_id, image) values (7, 'Quail - Eggs, Fresh', 3, 2, 'http://dummyimage.com/133x134.bmp/dddddd/000000');
insert into products (id, name, price, category_id, image) values (8, 'Cheese - Mascarpone', 97, 2, 'http://dummyimage.com/245x246.jpg/dddddd/000000');
insert into products (id, name, price, category_id, image) values (9, 'Mace', 25, 2, 'http://dummyimage.com/139x103.bmp/5fa2dd/ffffff');
insert into products (id, name, price, category_id, image) values (10, 'Oil - Shortening - All - Purpose', 63, 2, 'http://dummyimage.com/206x125.bmp/cc0000/ffffff');
insert into products (id, name, price, category_id, image) values (11, 'Marjoram - Fresh', 60, 2, 'http://dummyimage.com/133x134.bmp/dddddd/000000');
insert into products (id, name, price, category_id, image) values (12, 'Turnip - White', 74, 2, 'http://dummyimage.com/245x246.jpg/dddddd/000000');
insert into products (id, name, price, category_id, image) values (13, 'Pork Salted Bellies', 38, 2, 'http://dummyimage.com/139x103.bmp/5fa2dd/ffffff');
insert into products (id, name, price, category_id, image) values (14, 'Longos - Greek Salad', 15, 2, 'http://dummyimage.com/206x125.bmp/cc0000/ffffff');
insert into products (id, name, price, category_id, image) values (15, 'Amaretto', 85, 2, 'http://dummyimage.com/133x134.bmp/dddddd/000000');
insert into products (id, name, price, category_id, image) values (16, 'Godiva White Chocolate', 97, 2, 'http://dummyimage.com/245x246.jpg/dddddd/000000');
insert into products (id, name, price, category_id, image) values (17, 'Tomatoes - Roma', 61, 2, 'http://dummyimage.com/139x103.bmp/5fa2dd/ffffff');
insert into products (id, name, price, category_id, image) values (18, 'Oven Mitt - 13 Inch', 1, 3, 'http://dummyimage.com/206x125.bmp/cc0000/ffffff');
insert into products (id, name, price, category_id, image) values (19, 'Vermouth - White, Cinzano', 72, 2, 'http://dummyimage.com/133x134.bmp/dddddd/000000');
insert into products (id, name, price, category_id, image) values (20, 'Club Soda - Schweppes, 355 Ml', 38, 2, 'http://dummyimage.com/245x246.jpg/dddddd/000000');
insert into products (id, name, price, category_id, image) values (21, 'Fenngreek Seed', 1, 1, 'http://dummyimage.com/139x103.bmp/5fa2dd/ffffff');
insert into products (id, name, price, category_id, image) values (22, 'Dill Weed - Dry', 72, 1, 'http://dummyimage.com/206x125.bmp/cc0000/ffffff');
insert into products (id, name, price, category_id, image) values (23, 'Pepper - Green', 56, 1, 'http://dummyimage.com/133x134.bmp/dddddd/000000');
insert into products (id, name, price, category_id, image) values (24, 'Bacardi Breezer - Tropical', 35, 2, 'http://dummyimage.com/245x246.jpg/dddddd/000000');
insert into products (id, name, price, category_id, image) values (25, 'Wine - Merlot Vina Carmen', 14, 2, 'http://dummyimage.com/139x103.bmp/5fa2dd/ffffff');
insert into products (id, name, price, category_id, image) values (26, 'Sauce - Black Current, Dry Mix', 9, 1, 'http://dummyimage.com/206x125.bmp/cc0000/ffffff');
insert into products (id, name, price, category_id, image) values (27, 'Crab - Soft Shell', 17, 1, 'http://dummyimage.com/133x134.bmp/dddddd/000000');
insert into products (id, name, price, category_id, image) values (28, 'Jameson Irish Whiskey', 19, 2, 'http://dummyimage.com/245x246.jpg/dddddd/000000');
insert into products (id, name, price, category_id, image) values (29, 'Muffin Chocolate Individual Wrap', 77, 1, 'http://dummyimage.com/139x103.bmp/5fa2dd/ffffff');
insert into products (id, name, price, category_id, image) values (30, 'Mussels - Frozen', 95, 1, 'http://dummyimage.com/206x125.bmp/cc0000/ffffff');


insert into orders (id, name, date_purchase) values (1, 'María López', CURRENT_TIMESTAMP);
insert into order_lines (id, product_id, price, quantity, order_id) values (1, 1, 91, 1, 1);
insert into order_lines (id, product_id, price, quantity, order_id) values (2, 2, 87, 3, 1);
insert into order_lines (id, product_id, price, quantity, order_id) values (3, 29, 77, 2, 1);


insert into orders (id, name, date_purchase) values (2, 'Ángel Martínez', CURRENT_TIMESTAMP);
insert into order_lines (id, product_id, price, quantity, order_id) values (4, 19, 72, 3, 2);
insert into order_lines (id, product_id, price, quantity, order_id) values (5, 28, 19, 2, 2);

insert into orders (id, name, date_purchase) values (3, 'Ana Jiménez', CURRENT_TIMESTAMP);
insert into order_lines (id, product_id, price, quantity, order_id) values (6, 9, 25, 1, 3);
insert into order_lines (id, product_id, price, quantity, order_id) values (7, 13, 38, 5, 3);
insert into order_lines (id, product_id, price, quantity, order_id) values (8, 22, 72, 1, 3);;

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');