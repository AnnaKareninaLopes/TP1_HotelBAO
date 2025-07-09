INSERT INTO tb_client (id, username, email, password, login, celular, endereco)
VALUES (12, 'HigorGabriel', 'HigorGabriel@email.com', '$2a$10$73IvhV0xGnEqje.KCOS8KOODc7zTeDORdOLROaTu76hBAbt9JKX4a', 'HigorGabriel', '31988488888', 'Av. Central, 300');

INSERT INTO tb_client_role (client_id, role_id) VALUES (12, 1);