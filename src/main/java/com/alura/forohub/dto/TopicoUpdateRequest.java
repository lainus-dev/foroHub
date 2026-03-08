package com.alura.forohub.dto;

import com.alura.forohub.model.StatusTopico;
import jakarta.validation.constraints.NotBlank;

public record TopicoUpdateRequest(
    @NotBlank(message = "El título es obligatorio")
    String titulo,
    
    @NotBlank(message = "El mensaje es obligatorio")
    String mensaje,
    
    StatusTopico status,
    
    @NotBlank(message = "El curso es obligatorio")
    String curso
) {}
