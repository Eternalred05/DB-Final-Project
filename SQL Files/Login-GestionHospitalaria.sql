CREATE TABLE Usuario (
    idUsuario   SERIAL       NOT NULL,
    nombre      VARCHAR(100) NOT NULL,
    usuario     VARCHAR(50)  NOT NULL UNIQUE,
    contrasena  VARCHAR(100) NOT NULL,
    admin       BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT pk_usuario PRIMARY KEY (idUsuario)
);

-- Insertar un usuario administrador de prueba (cámbialo luego)
INSERT INTO Usuario (nombre, usuario, contrasena, admin)
VALUES ('Alexandro', 'alex', '150905', TRUE);

-- Insertar un usuario normal de prueba
INSERT INTO Usuario (nombre, usuario, contrasena, admin)
VALUES ('Juan Médico General', 'Juan', '123', FALSE);