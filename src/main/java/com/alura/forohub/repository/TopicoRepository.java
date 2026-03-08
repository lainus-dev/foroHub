package com.alura.forohub.repository;

import com.alura.forohub.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
    
    Page<Topico> findAllByOrderByFechaCreacionAsc(Pageable pageable);
    
    List<Topico> findTop10ByOrderByFechaCreacionAsc();
    
    List<Topico> findByCurso(String curso);
    
    Page<Topico> findByCurso(String curso, Pageable pageable);
}
