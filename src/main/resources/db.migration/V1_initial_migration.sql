-- USERS TABLE
CREATE TABLE users (
                       id       BIGSERIAL PRIMARY KEY,
                       name     VARCHAR(255) NOT NULL,
                       email    VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

-- CATEGORIES TABLE
CREATE TABLE categories (
                            id   SMALLSERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
);

-- PRODUCTS TABLE
CREATE TABLE products (
                          id            BIGSERIAL PRIMARY KEY,
                          name          VARCHAR(255)   NOT NULL,
                          price         NUMERIC(10, 2) NOT NULL,
                          description   TEXT           NOT NULL,
                          category_id   SMALLINT,
                          CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

-- PROFILES TABLE
CREATE TABLE profiles (
                          id             BIGINT PRIMARY KEY,
                          bio            TEXT,
                          phone_number   VARCHAR(15),
                          date_of_birth  DATE,
                          loyalty_points INTEGER DEFAULT 0,
                          CONSTRAINT fk_profile_user FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

-- ADDRESSES TABLE
CREATE TABLE addresses (
                           id      BIGSERIAL PRIMARY KEY,
                           street  VARCHAR(255) NOT NULL,
                           city    VARCHAR(255) NOT NULL,
                           state   VARCHAR(255) NOT NULL,
                           zip     VARCHAR(255) NOT NULL,
                           user_id BIGINT       NOT NULL,
                           CONSTRAINT fk_address_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- WISHLIST TABLE (Many-to-Many relationship between users and products)
CREATE TABLE wishlist (
                          user_id    BIGINT NOT NULL,
                          product_id BIGINT NOT NULL,
                          PRIMARY KEY (user_id, product_id),
                          CONSTRAINT fk_wishlist_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                          CONSTRAINT fk_wishlist_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Indexes for faster lookup
CREATE INDEX idx_addresses_user_id ON addresses(user_id);
CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_wishlist_user_id ON wishlist(user_id);
CREATE INDEX idx_wishlist_product_id ON wishlist(product_id);
