-- 1. Inserir roles (ids explícitos para evitar erro de FK depois)
INSERT INTO tb_role (id, authority) VALUES (1, 'ROLE_ADMIN');
INSERT INTO tb_role (id, authority) VALUES (2, 'ROLE_CLIENT');

-- 2. Inserir clients
INSERT INTO tb_client (id, username, email, password, login, celular, endereco)
VALUES (1, 'Anna Karenina', 'anna@ifmg.edu.br', '$2a$12$VN08e9jTMoJ5g.IJ3ryujubBxXr4hCMwAreGTsjZQVromZqpSVNFS', 'annak', '31999999999', 'Rua das Flores, 100');

INSERT INTO tb_client (id, username, email, password, login, celular, endereco)
VALUES (2, 'João Silva', 'joao@email.com', '$2a$12$VN08e9jTMoJ5g.IJ3ryujubBxXr4hCMwAreGTsjZQVromZqpSVNFS', 'joaos', '31988888888', 'Av. Central, 200');

INSERT INTO tb_client_role (client_id, role_id) VALUES (1, 1);
INSERT INTO tb_client_role (client_id, role_id) VALUES (2, 2);

INSERT INTO tb_room (descricao, valor, imagem_url)
VALUES ('Quarto Simples com ventilador', 120.00, '/images/quarto_simples.jpg');

INSERT INTO tb_room (descricao, valor, imagem_url)
VALUES ('Suíte Master com ar-condicionado e varanda', 300.00, '/images/suite_master.jpg');
