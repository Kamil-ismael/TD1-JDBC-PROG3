-- Table Product
CREATE TABLE IF NOT EXISTS Product (
    product_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    creation_datetime TIMESTAMP NOT NULL DEFAULT Now()
);

-- Table Product_category
CREATE TABLE IF NOT EXISTS Product_category (
    product_category_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    product_id INT NOT NULL,
    CONSTRAINT fk_product
        FOREIGN KEY (product_id)
        REFERENCES Product(product_id)
        ON DELETE CASCADE
);

-- Droit à l'utilisateur
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO product_manager_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO product_manager_user;