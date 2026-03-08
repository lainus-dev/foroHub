package com.alura.forohub.controller;

import com.alura.forohub.dto.TopicoListResponse;
import com.alura.forohub.dto.TopicoRequest;
import com.alura.forohub.dto.TopicoResponse;
import com.alura.forohub.dto.TopicoUpdateRequest;
import com.alura.forohub.model.Topico;
import com.alura.forohub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoResponse> crearTopico(
            @RequestBody @Valid TopicoRequest request,
            UriComponentsBuilder uriBuilder) {
        
        Topico topico = topicoService.crearTopico(request);
        TopicoResponse response = new TopicoResponse(topico);
        
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<TopicoListResponse>> listarTopicos(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable pageable) {
        
        Page<Topico> topicos = topicoService.listarTopicos(pageable);
        Page<TopicoListResponse> response = topicos.map(TopicoListResponse::new);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/primeros10")
    public ResponseEntity<List<TopicoListResponse>> listarPrimeros10Topicos() {
        List<Topico> topicos = topicoService.listarPrimeros10Topicos();
        List<TopicoListResponse> response = topicos.stream()
                .map(TopicoListResponse::new)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponse> obtenerTopico(@PathVariable Long id) {
        Topico topico = topicoService.obtenerTopicoPorId(id);
        TopicoResponse response = new TopicoResponse(topico);
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoResponse> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid TopicoUpdateRequest request) {
        
        Topico topico = topicoService.actualizarTopico(id, request);
        TopicoResponse response = new TopicoResponse(topico);
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        topicoService.eliminarTopico(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/curso/{nombreCurso}")
    public ResponseEntity<List<TopicoListResponse>> listarTopicosPorCurso(@PathVariable String nombreCurso) {
        List<Topico> topicos = topicoService.listarTopicosPorCurso(nombreCurso);
        List<TopicoListResponse> response = topicos.stream()
                .map(TopicoListResponse::new)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
}
