CREATE DATABASE IF NOT EXISTS plazoleta_restaurantes
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE plazoleta_restaurantes;

CREATE TABLE cargo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

INSERT INTO cargo (nombre) VALUES
    ('CHEF'),
    ('MESERO'),
    ('DOMICILIARIO');

CREATE TABLE restaurante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    nit VARCHAR(20) NOT NULL UNIQUE,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(13) NOT NULL,
    url_logo VARCHAR(500) NOT NULL,
    id_propietario BIGINT NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE plato (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio INT NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    url_imagen VARCHAR(500) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    id_restaurante BIGINT NOT NULL,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_plato_restaurante FOREIGN KEY (id_restaurante) REFERENCES restaurante(id),
    UNIQUE INDEX uq_plato_restaurante (nombre, id_restaurante)
) ENGINE=InnoDB;

CREATE TABLE empleado_restaurante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_empleado BIGINT NOT NULL,
    id_restaurante BIGINT NOT NULL,
    id_cargo BIGINT NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_asignacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_empleado_restaurante_restaurante FOREIGN KEY (id_restaurante) REFERENCES restaurante(id),
    CONSTRAINT fk_empleado_restaurante_cargo FOREIGN KEY (id_cargo) REFERENCES cargo(id)
) ENGINE=InnoDB;
