package com.alura.forohub.dto;

import com.alura.forohub.model.StatusTopico;
import com.alura.forohub.model.Topico;

import java.time.LocalDateTime;

public record TopicoListResponse(
    Long id,
    String titulo,
    LocalDateTime fechaCreacion,
    StatusTopico status,
    String autorNombre,
    String curso
) {
    public TopicoListResponse(Topico topico) {
        this(
            topico.getId(),
            topico.getTitulo(),
            topico.getFechaCreacion(),
            topico.getStatus(),
            topico.getAutor().getNombre(),
            topico.getCurso()
        );
    }
}
