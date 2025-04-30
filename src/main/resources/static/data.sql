-- Thêm dữ liệu mẫu vào bảng categories
INSERT INTO categories (name) VALUES
                                  ('Electronics'),
                                  ('Clothing'),
                                  ('Books');

-- Thêm dữ liệu mẫu vào bảng products
INSERT INTO products (name, price, description, category_id) VALUES
                                                                 ('Smartphone', 699.99, 'Latest smartphone with AI features', 1),
                                                                 ('T-shirt', 19.99, '100% cotton T-shirt', 2),
                                                                 ('Novel', 9.99, 'Bestselling fiction novel', 3);

-- Thêm dữ liệu mẫu vào bảng users
INSERT INTO users (name, email, password) VALUES
                                              ('Alice', 'alice@example.com', 'password123'),
                                              ('Bob', 'bob@example.com', 'password123');

-- Thêm dữ liệu mẫu vào bảng addresses
INSERT INTO addresses (street, city, state, zip, user_id) VALUES
                                                              ('123 Main St', 'CityA', 'StateA', '12345', 1),
                                                              ('456 Market St', 'CityB', 'StateB', '67890', 2);

-- Thêm dữ liệu mẫu vào bảng profiles
INSERT INTO profiles (id, bio, phone_number, date_of_birth, loyalty_points) VALUES
                                                                                (1, 'I love shopping.', '1234567890', '1995-06-15', 100),
                                                                                (2, 'Avid reader and traveler.', '0987654321', '1990-12-20', 200);

-- Thêm dữ liệu mẫu vào bảng wishlist
INSERT INTO wishlist (user_id, product_id) VALUES
                                               (1, 1),
                                               (1, 3),
                                               (2, 2);
