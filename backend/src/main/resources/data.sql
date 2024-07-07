-- Insert users if they do not already exist
INSERT INTO users (first_name, last_name, vat, username, email, password)
SELECT * FROM (VALUES
    ('Johnny', 'Depp', '075369553', 'johnny', 'johnny@hua.gr', '$2a$10$1rNHS..xCJv6dyz8qWNNs.ors.8LZXNZExLScSw1VfaBEtdW/GFFa'),
    ('Leonardo', 'DiCaprio', '089218786', 'leo', 'leo@hua.gr', '$2a$10$m7kKXcFLiehVJrh0yzK4E.cYHCNElkaaUBEPmroG9iR4YaT.6rXx2'),
    ('Brad', 'Pitt', '049211582 ', 'achilles', 'achilles@hua.gr', '$2a$10$NrxQDadEACPES/fzUrA5Gupv6MCr/p8N7pHgoiOxnxoCOcZFeYhfu')
) AS data (first_name, last_name, vat, username, email, password)
WHERE NOT EXISTS (
    SELECT 1 FROM users u
    WHERE u.username = data.username
);

-- Insert cooperatives if they do not already exist
INSERT INTO cooperatives (name, vat, status, notes, employee_id)
SELECT * FROM (VALUES
    ('Heroes', '061248142', 'processing', null, null),
    ('Villains', '007601674', 'approved', 'well done', 2),
    ('Anti-Villains', '029273149', 'rejected', 'invalid vat', 2)
) AS data (name, vat, status, notes, employee_id)
WHERE NOT EXISTS (
    SELECT 1 FROM cooperatives c
    WHERE c.name = data.name
);

-- Insert products if they do not already exist
INSERT INTO products (name, category, price)
SELECT * FROM (VALUES
    ('Feta', 'Cheese', 2.99),
    ('Apple', 'Fruit', 0.99),
    ('Orange', 'Fruit', 0.49)
) AS data (name, category, price)
WHERE NOT EXISTS (
    SELECT 1 FROM products p
    WHERE p.name = data.name
);

-- Insert cultivation locations if they do not already exist
INSERT INTO cultivation_locations (address, area, zip_code)
SELECT * FROM (VALUES
    ('Thiseos 70', 'Kallithea', '176 76'),
    ('Omirou 9', 'Tavros', '177 78'),
    ('Agiou Spiridonos 28', 'Egaleo', '122 43')
) AS data (address, area, zip_code)
WHERE NOT EXISTS (
    SELECT 1 FROM cultivation_locations cl
    WHERE cl.address = data.address
);

-- Associate farmers with their cooperatives if not already associated
INSERT INTO cooperative_farmers (cooperative_id, farmer_id)
SELECT * FROM (VALUES
    (1, 1),
    (2, 1),
    (3, 3)
) AS data (cooperative_id, farmer_id)
WHERE NOT EXISTS (
    SELECT 1 FROM cooperative_farmers cf
    WHERE cf.cooperative_id = data.cooperative_id
    AND cf.farmer_id = data.farmer_id
);

-- Associate products with their cooperatives if not already associated
INSERT INTO cooperative_products (cooperative_id, product_id)
SELECT * FROM (VALUES
    (1, 1),
    (2, 2),
    (3, 3)
) AS data (cooperative_id, product_id)
WHERE NOT EXISTS (
    SELECT 1 FROM cooperative_products cp
    WHERE cp.cooperative_id = data.cooperative_id
    AND cp.product_id = data.product_id
);

-- Associate cultivation locations with their cooperatives if not already associated
INSERT INTO cooperative_locations (cooperative_id, location_id)
SELECT * FROM (VALUES
    (1, 1),
    (2, 2),
    (3, 3)
) AS data (cooperative_id, location_id)
WHERE NOT EXISTS (
    SELECT 1 FROM cooperative_locations cl
    WHERE cl.cooperative_id = data.cooperative_id
    AND cl.location_id = data.location_id
);

-- Add roles to users if not already assigned
INSERT INTO user_roles (user_id, role_id)
SELECT * FROM (VALUES
    (1, 1),
    (2, 2),
    (3, 3)
) AS data (user_id, role_id)
WHERE NOT EXISTS (
    SELECT 1 FROM user_roles ur
    WHERE ur.user_id = data.user_id
    AND ur.role_id = data.role_id
);
