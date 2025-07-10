-- CREATE TABLE tb_client (
--        id BIGINT AUTO_INCREMENT PRIMARY KEY,
--        username VARCHAR(255),
--        email VARCHAR(255),
--        password VARCHAR(255),
--        login VARCHAR(255) UNIQUE,
--        celular VARCHAR(255),
--        endereco VARCHAR(255)
-- );
--
-- CREATE TABLE tb_password_recover (
--          id BIGINT AUTO_INCREMENT PRIMARY KEY,
--          token VARCHAR(255) NOT NULL,
--          email VARCHAR(255) NOT NULL,
--          expiration TIMESTAMP NOT NULL
-- );
--
-- CREATE TABLE tb_role (
--          id BIGINT AUTO_INCREMENT PRIMARY KEY,
--          authority VARCHAR(255)
-- );
--
-- CREATE TABLE tb_client_role (
--                                 client_id BIGINT NOT NULL,
--                                 role_id BIGINT NOT NULL,
--                                 PRIMARY KEY (client_id, role_id),
--                                 FOREIGN KEY (client_id) REFERENCES tb_client(id),
--                                 FOREIGN KEY (role_id) REFERENCES tb_role(id)
-- );
--
-- CREATE TABLE tb_room (
--          id BIGINT AUTO_INCREMENT PRIMARY KEY,
--          descricao TEXT,
--          valor DOUBLE,
--          imagemUrl VARCHAR(255)
-- );
--
-- CREATE TABLE tb_stay (
--          id BIGINT AUTO_INCREMENT PRIMARY KEY,
--          client_id BIGINT,
--          room_id BIGINT,
--          dataEntrada DATE,
--          FOREIGN KEY (client_id) REFERENCES tb_client(id),
--          FOREIGN KEY (room_id) REFERENCES tb_room(id)
-- );
--


create table tb_client (
                           id bigint not null auto_increment,
                           celular varchar(255),
                           email varchar(255),
                           endereco varchar(255),
                           login varchar(255),
                           password varchar(255),
                           username varchar(255),
                           primary key (id)
) engine=InnoDB;

create table tb_client_role (
                                client_id bigint not null,
                                role_id bigint not null,
                                primary key (client_id, role_id)
) engine=InnoDB;

create table tb_password_recover (
                                     expiration datetime(6) not null,
                                     id bigint not null auto_increment,
                                     email varchar(255) not null,
                                     token varchar(255) not null,
                                     primary key (id)
) engine=InnoDB;

create table tb_role (
                         id bigint not null auto_increment,
                         authority varchar(255),
                         primary key (id)
) engine=InnoDB;

create table tb_room (
                         valor float(53),
                         id bigint not null auto_increment,
                         descricao TEXT,
                         imagem_url varchar(255),
                         primary key (id)
) engine=InnoDB;

create table tb_stay (
                         data_entrada DATE,
                         client_id bigint,
                         id bigint not null auto_increment,
                         room_id bigint,
                         primary key (id)
) engine=InnoDB;

alter table tb_client
    add constraint UKelyk72q8sk3d936a6q7wbt04n unique (login);

alter table tb_client_role
    add constraint FKcy1gcv6wya3x467j93r061t9p
        foreign key (role_id)
            references tb_role (id);

alter table tb_client_role
    add constraint FKa4i5py4f21jvbfvxg6a1fhf62
        foreign key (client_id)
            references tb_client (id);

alter table tb_stay
    add constraint FKmm1334j8hldpocubs0g9kf5xc
        foreign key (client_id)
            references tb_client (id);

alter table tb_stay
    add constraint FK58amo6mu2g4gm7a64521keqx
        foreign key (room_id)
            references tb_room (id);
