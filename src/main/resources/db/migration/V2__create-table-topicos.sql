CREATE TABLE topicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    autor_id BIGINT NOT NULL,
    curso VARCHAR(255) NOT NULL,
    CONSTRAINT fk_topicos_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    INDEX idx_titulo (titulo),
    INDEX idx_curso (curso),
    INDEX idx_fecha_creacion (fecha_creacion),
    INDEX idx_status (status)
);
