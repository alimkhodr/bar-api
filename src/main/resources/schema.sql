CREATE TABLE IF NOT EXISTS customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS tabs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_number INT NOT NULL,
    status VARCHAR(10) NOT NULL,
    waiter VARCHAR(100),
    customer_id BIGINT NOT NULL REFERENCES customers (id)
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS menu_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    price DECIMAL(10, 2) NOT NULL,
    category_id BIGINT NOT NULL REFERENCES categories (id)
);

CREATE TABLE IF NOT EXISTS promotions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    discount_percent INT NOT NULL,
    active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS tab_items (
    tab_id BIGINT NOT NULL REFERENCES tabs (id) ON DELETE CASCADE,
    item_id BIGINT NOT NULL REFERENCES menu_items (id),
    quantity INT NOT NULL,
    PRIMARY KEY (tab_id, item_id)
);

CREATE TABLE IF NOT EXISTS promotion_items (
    promotion_id BIGINT NOT NULL REFERENCES promotions (id) ON DELETE CASCADE,
    item_id BIGINT NOT NULL REFERENCES menu_items (id) ON DELETE CASCADE,
    PRIMARY KEY (promotion_id, item_id)
);
