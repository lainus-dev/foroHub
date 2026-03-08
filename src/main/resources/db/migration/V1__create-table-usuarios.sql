CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    INDEX idx_email (email)
);

-- Insertar un usuario de prueba (contraseña: admin123)
-- La contraseña está hasheada con BCrypt
INSERT INTO usuarios (nombre, email, contrasena, activo) VALUES
('Administrador', 'admin@forohub.com', '$2a$10$xqGzTfNDZHPfXdILLJ0hJO5E7Hqo2YhPVlYFMZVwzYm5.TmHwLUUi', TRUE),
('Usuario Demo', 'demo@forohub.com', '$2a$10$xqGzTfNDZHPfXdILLJ0hJO5E7Hqo2YhPVlYFMZVwzYm5.TmHwLUUi', TRUE);
