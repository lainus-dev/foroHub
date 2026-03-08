package com.alura.forohub.service;

import com.alura.forohub.dto.TopicoRequest;
import com.alura.forohub.dto.TopicoUpdateRequest;
import com.alura.forohub.infra.exception.DuplicateResourceException;
import com.alura.forohub.infra.exception.ResourceNotFoundException;
import com.alura.forohub.model.Topico;
import com.alura.forohub.model.Usuario;
import com.alura.forohub.repository.TopicoRepository;
import com.alura.forohub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Topico crearTopico(TopicoRequest request) {
        // Validar que no exista un tópico duplicado
        if (topicoRepository.existsByTituloAndMensaje(request.titulo(), request.mensaje())) {
            throw new DuplicateResourceException("Ya existe un tópico con el mismo título y mensaje");
        }

        // Buscar el autor
        Usuario autor = usuarioRepository.findById(request.autorId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + request.autorId()));

        // Crear el tópico
        Topico topico = new Topico();
        topico.setTitulo(request.titulo());
        topico.setMensaje(request.mensaje());
        topico.setAutor(autor);
        topico.setCurso(request.curso());

        return topicoRepository.save(topico);
    }

    public Page<Topico> listarTopicos(Pageable pageable) {
        return topicoRepository.findAllByOrderByFechaCreacionAsc(pageable);
    }

    public List<Topico> listarPrimeros10Topicos() {
        return topicoRepository.findTop10ByOrderByFechaCreacionAsc();
    }

    public Topico obtenerTopicoPorId(Long id) {
        return topicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado con ID: " + id));
    }

    @Transactional
    public Topico actualizarTopico(Long id, TopicoUpdateRequest request) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado con ID: " + id));

        // Validar que no exista otro tópico con el mismo título y mensaje
        if (topicoRepository.existsByTituloAndMensaje(request.titulo(), request.mensaje())) {
            Topico existente = topicoRepository.findAll().stream()
                    .filter(t -> t.getTitulo().equals(request.titulo()) && t.getMensaje().equals(request.mensaje()))
                    .findFirst()
                    .orElse(null);
            if (existente != null && !existente.getId().equals(id)) {
                throw new DuplicateResourceException("Ya existe otro tópico con el mismo título y mensaje");
            }
        }

        topico.setTitulo(request.titulo());
        topico.setMensaje(request.mensaje());
        topico.setCurso(request.curso());
        if (request.status() != null) {
            topico.setStatus(request.status());
        }

        return topicoRepository.save(topico);
    }

    @Transactional
    public void eliminarTopico(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tópico no encontrado con ID: " + id);
        }
        topicoRepository.deleteById(id);
    }

    public List<Topico> listarTopicosPorCurso(String curso) {
        return topicoRepository.findByCurso(curso);
    }
}
