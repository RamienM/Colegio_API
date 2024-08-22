package org.prueba.calificacionesh2.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.prueba.calificacionesh2.dto.UsuarioLoginDTO;
import org.prueba.calificacionesh2.dto.UsuarioRegisterDTO;
import org.prueba.calificacionesh2.entity.Usuario;
import org.prueba.calificacionesh2.security.JwtProvider;
import org.prueba.calificacionesh2.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class UsuarioController {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/auth/register")
    public Usuario registro(@RequestBody UsuarioRegisterDTO register){
        var usuario = usuarioService.registrarUsuario(register);
        return usuario;
    }


    @PostMapping("/auth/login")
    public String login(@Valid @RequestBody UsuarioLoginDTO inicio, HttpServletResponse response, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
        }
        // Autenticar al usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(inicio.getUsername(), inicio.getPassword()));

        if (authentication.isAuthenticated()) {
            // Cargar el usuario autenticado
            Usuario user = usuarioService.loadUserByUsername(inicio.getUsername());

            // Generar el token JWT
            String token = jwtProvider.generateToken(user);

            return token;
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
}
