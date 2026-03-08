package com.alura.forohub.dto;

import com.alura.forohub.model.StatusTopico;
import com.alura.forohub.model.Topico;

import java.time.LocalDateTime;

public record TopicoResponse(
    Long id,
    String titulo,
    String mensaje,
    LocalDateTime fechaCreacion,
    StatusTopico status,
    String autorNombre,
    String autorEmail,
    String curso
) {
    public TopicoResponse(Topico topico) {
        this(
            topico.getId(),
            topico.getTitulo(),
            topico.getMensaje(),
            topico.getFechaCreacion(),
            topico.getStatus(),
            topico.getAutor().getNombre(),
            topico.getAutor().getEmail(),
            topico.getCurso()
        );
    }
}
