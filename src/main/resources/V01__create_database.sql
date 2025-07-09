CREATE TABLE tb_client (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       username VARCHAR(255),
       email VARCHAR(255),
       password VARCHAR(255),
       login VARCHAR(255) UNIQUE,
       celular VARCHAR(255),
       endereco VARCHAR(255)
);
CREATE TABLE tb_client_role (
        client_id BIGINT NOT NULL,
        role_id BIGINT NOT NULL,
        PRIMARY KEY (client_id, role_id),
        FOREIGN KEY (client_id) REFERENCES tb_client(id),
        FOREIGN KEY (role_id) REFERENCES tb_role(id)
);

CREATE TABLE tb_password_recover (
         id BIGINT AUTO_INCREMENT PRIMARY KEY,
         token VARCHAR(255) NOT NULL,
         email VARCHAR(255) NOT NULL,
         expiration TIMESTAMP NOT NULL
);

CREATE TABLE tb_role (
         id BIGINT AUTO_INCREMENT PRIMARY KEY,
         authority VARCHAR(255)
);

CREATE TABLE tb_room (
         id BIGINT AUTO_INCREMENT PRIMARY KEY,
         descricao TEXT,
         valor DOUBLE,
         imagemUrl VARCHAR(255)
);

CREATE TABLE tb_stay (
         id BIGINT AUTO_INCREMENT PRIMARY KEY,
         client_id BIGINT,
         room_id BIGINT,
         dataEntrada DATE,
         FOREIGN KEY (client_id) REFERENCES tb_client(id),
         FOREIGN KEY (room_id) REFERENCES tb_room(id)
);

