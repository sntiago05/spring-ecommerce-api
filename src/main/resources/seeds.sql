DELETE FROM cart_items;
DELETE FROM order_item;
DELETE FROM orders;
DELETE FROM carts;
DELETE FROM products;

INSERT INTO products (name, description, price, stock, active)
VALUES
    ('Laptop Lenovo', 'Laptop para pruebas', 2500000.00, 10, true),
    ('Mouse Logitech', 'Mouse inalámbrico', 120000.00, 20, true),
    ('Teclado Mecánico', 'Teclado mecánico RGB', 250000.00, 5, true),
    ('Monitor Samsung', 'Monitor de 24 pulgadas', 800000.00, 3, true),
    ('Producto Sin Stock', 'Producto para probar stock insuficiente', 50000.00, 0, true);