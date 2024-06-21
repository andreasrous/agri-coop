-- Insert users
INSERT INTO users (first_name, last_name, vat, username, email, password) VALUES
    ('Johnny', 'Depp', '075369553', 'johnny', 'johnny@hua.gr', '$2a$10$1rNHS..xCJv6dyz8qWNNs.ors.8LZXNZExLScSw1VfaBEtdW/GFFa'),
    ('Leonardo', 'DiCaprio', '089218786', 'leo', 'leo@hua.gr', '$2a$10$m7kKXcFLiehVJrh0yzK4E.cYHCNElkaaUBEPmroG9iR4YaT.6rXx2'),
    ('Brad', 'Pitt', '049211582 ', 'achilles', 'achilles@hua.gr', '$2a$10$NrxQDadEACPES/fzUrA5Gupv6MCr/p8N7pHgoiOxnxoCOcZFeYhfu');

-- Insert cooperatives
INSERT INTO cooperatives (name, vat, status, notes, employee_id) VALUES
    ('Heroes', '061248142', 'processing', null, null),
    ('Villains', '007601674', 'approved', 'well done', 2),
    ('Anti-Villains', '029273149', 'rejected', 'invalid vat', 2);

-- Insert products
INSERT INTO products (name, category, price) VALUES
    ('Feta', 'Cheese', 2.99),
    ('Apple', 'Fruit', 0.99),
    ('Orange', 'Fruit', 0.49);

-- Insert cultivation locations
INSERT INTO cultivation_locations (address, area, zip_code) VALUES
    ('Thiseos 70', 'Kallithea', '176 76'),
    ('Omirou 9', 'Tavros', '177 78'),
    ('Agiou Spiridonos 28', 'Egaleo', '122 43');

-- Associate farmers with their cooperatives
INSERT INTO cooperative_farmers (cooperative_id, farmer_id) VALUES
    (1, 1),
    (2, 1),
    (3, 3);

-- Associate products with their cooperatives
INSERT INTO cooperative_products (cooperative_id, product_id) VALUES
    (1, 1),
    (2, 2),
    (3, 3);

-- Associate cultivation locations with their cooperatives
INSERT INTO cooperative_locations (cooperative_id, location_id) VALUES
    (1, 1),
    (2, 2),
    (3, 3);

-- Add roles to users
INSERT INTO user_roles (user_id, role_id) VALUES
    (1, 1),
    (2, 2),
    (3, 3);
