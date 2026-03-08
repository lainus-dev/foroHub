package com.alura.forohub.repository;

import com.alura.forohub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    UserDetails findByEmail(String email);
    
    Optional<Usuario> findByEmailAndActivoTrue(String email);
    
    boolean existsByEmail(String email);
}
