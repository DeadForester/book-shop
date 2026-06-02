BEGIN;

-- 1. PUBLISHERS
INSERT INTO PUBLISHERS (PUBLISHER_ID, NAME, DESCRIPTION, PHONE, ADDRESS)
VALUES (1, 'Издательство АСТ', 'Крупное российское издательство художественной и учебной литературы', '+74951234567',
        'Москва, ул. Примерная, д. 1'),
       (2, 'Эксмо', 'Ведущее издательство современной прозы и классики', '+74959876543', 'Москва, ул. Книжная, д. 10');

-- 2. AUTHORS
INSERT INTO AUTHORS (AUTHOR_ID, FIRSTNAME, SURNAME, DESCRIPTION)
VALUES (1, 'Лев', 'Толстой', 'Классик русской литературы, автор эпопеи "Война и мир"'),
       (2, 'Федор', 'Достоевский', 'Выдающийся русский писатель и философ'),
       (3, 'Джордж', 'Оруэлл', 'Английский писатель и публицист, автор антиутопий');

-- 3. BOOKS (BINDING исправлен на HARDCOVER / SOFTCOVER)
INSERT INTO BOOKS (BOOK_ID, PUBLISHER_ID, TITLE, GENRE, CREATION_YEAR, PAGES, DESCRIPTION, AMOUNT, BINDING, CREATED_AT,
                   MODIFIED_AT)
VALUES (1, 1, 'Война и мир', 'Роман-эпопея', 1869, 1225,
        'Масштабное произведение о русском обществе в эпоху наполеоновских войн', 1500.00, 'HARDCOVER',
        '2023-01-10 10:00:00', NULL),
       (2, 1, 'Преступление и наказание', 'Психологический роман', 1866, 672,
        'Глубокое исследование морали и совести через историю Раскольникова', 800.00, 'SOFTCOVER',
        '2023-02-15 12:00:00', NULL),
       (3, 2, '1984', 'Дистопия', 1949, 320, 'Культовый роман о тоталитарном обществе и контроле над сознанием', 600.00,
        'HARDCOVER', '2023-03-20 14:00:00', NULL);

-- 4. AUTHORS_BOOKS (У книги 1 два автора, у книги 3 два автора)
INSERT INTO AUTHORS_BOOKS (AUTHOR_ID, BOOK_ID)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (1, 3),
       (3, 3);

-- 5. IMAGES
INSERT INTO IMAGES (IMAGE_ID, BOOK_ID, URL, CREATED_AT, MODIFIED_AT)
VALUES (1, 1, 'https://cdn.example.com/covers/war_peace_front.jpg', '2023-01-10 10:05:00', NULL),
       (2, 1, 'https://cdn.example.com/covers/war_peace_back.jpg', '2023-01-10 10:06:00', NULL),
       (3, 2, 'https://cdn.example.com/covers/crime_cover.jpg', '2023-02-15 12:05:00', NULL),
       (4, 3, 'https://cdn.example.com/covers/1984_cover.jpg', '2023-03-20 14:05:00', NULL);

-- 6. USRS
INSERT INTO USRS (USER_ID, EMAIL, PASSWORD, ROLE)
VALUES (1, 'ivan.petrov@example.com', '$2a$10$hashed_password_1', 'USER'),
       (2, 'anna.sidorova@example.com', '$2a$10$hashed_password_2', 'USER'),
       (3, 'admin@example.com', '$2a$10$hashed_password_3', 'ADMIN');

-- 7. WAREHOUSES
INSERT INTO WAREHOUSES (WAREHOUSE_ID, ADDRESS)
VALUES (1, 'Москва, ул. Складская, д. 5'),
       (2, 'Санкт-Петербург, пр. Логистический, д. 12');

-- 8. WAREHOUSES_BOOKS
INSERT INTO WAREHOUSES_BOOKS (BOOK_ID, WAREHOUSE_ID, QUANTITY, CREATED_AT, MODIFIED_AT)
VALUES (1, 1, 50, '2023-01-11 09:00:00', NULL),
       (1, 2, 30, '2023-01-11 09:00:00', NULL),
       (2, 1, 20, '2023-02-16 09:00:00', NULL),
       (3, 2, 45, '2023-03-21 09:00:00', NULL);

-- 9. PROVIDERS
INSERT INTO PROVIDERS (PROVIDER_ID, TITLE, ADDRESS, CONTACT_NUMBER)
VALUES (1, 'ООО "Книжный Мир"', 'Нижний Новгород, ул. Печатная, д. 3', '+78311234567'),
       (2, 'ИП "ГлобалБук"', 'Казань, ул. Издательская, д. 8', '+78439876543');

-- 10. PURCHASES
INSERT INTO PURCHASES (PURCHASE_ID, BOOK_ID, PROVIDER_ID, QUANTITY, TOTAL_SUM, CREATED_AT, ARRIVED_AT)
VALUES (1, 1, 1, 100, 85000.00, '2023-01-05 10:00:00', '2023-01-08 14:00:00'),
       (2, 2, 2, 50, 32000.00, '2023-02-10 11:00:00', NULL),
       (3, 3, 1, 60, 27000.00, '2023-03-15 09:00:00', '2023-03-18 16:00:00');

-- 11. ORDERS (STATUS исправлен на CREATING, IN_PROGRESS, DONE)
-- Пользователь 1 имеет два заказа: №1001 и №1002
INSERT INTO ORDERS (ORDER_ID, USER_ID, ORDER_NUMBER, STATUS, TOTAL_PRICE, CREATED_AT, MODIFIED_AT)
VALUES (1, 1, '1001', 'DONE', 2700.00, '2023-04-01 15:00:00', '2023-04-05 10:00:00'),
       (2, 1, '1002', 'CREATING', 1400.00, '2023-04-10 16:00:00', NULL),
       (3, 2, '1003', 'IN_PROGRESS', 1500.00, '2023-04-12 17:00:00', '2023-04-13 09:00:00');

-- 12. ORDER_ITEMS
-- Заказ 1: Книга 1 (1 шт) + Книга 3 (2 шт) = 2700
-- Заказ 2: Книга 2 (1 шт) + Книга 3 (1 шт) = 1400
-- Заказ 3: Книга 1 (1 шт) = 1500
INSERT INTO ORDER_ITEMS (ORDER_ITEM_ID, BOOK_ID, ORDER_ID, QUANTITY, CREATED_AT, MODIFIED_AT)
VALUES (1, 1, 1, 1, '2023-04-01 15:00:00', NULL),
       (2, 3, 1, 2, '2023-04-01 15:00:00', NULL),
       (3, 2, 2, 1, '2023-04-10 16:00:00', NULL),
       (4, 3, 2, 1, '2023-04-10 16:00:00', NULL),
       (5, 1, 3, 1, '2023-04-12 17:00:00', NULL);

COMMIT;