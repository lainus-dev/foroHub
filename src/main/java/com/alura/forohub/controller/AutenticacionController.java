package com.alura.forohub.controller;

import com.alura.forohub.dto.LoginRequest;
import com.alura.forohub.dto.TokenResponse;
import com.alura.forohub.model.Usuario;
import com.alura.forohub.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.email(),
                loginRequest.contrasena()
        );
        
        Authentication usuarioAutenticado = authenticationManager.authenticate(authToken);
        Usuario usuario = (Usuario) usuarioAutenticado.getPrincipal();
        String jwtToken = tokenService.generarToken(usuario);
        
        return ResponseEntity.ok(new TokenResponse(jwtToken, usuario.getEmail()));
    }
}
