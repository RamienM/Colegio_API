package org.prueba.calificacionesh2.service;


import org.prueba.calificacionesh2.dto.UsuarioRegisterDTO;
import org.prueba.calificacionesh2.entity.Usuario;
import org.prueba.calificacionesh2.repository.UsuarioRepository;
import org.prueba.calificacionesh2.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        return usuario.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    public Usuario registrarUsuario(UsuarioRegisterDTO usuario) {
        Usuario user = new Usuario();
        user.setNombre(usuario.getNombre());
        user.setApellido(usuario.getApellido());
        user.setCorreo(usuario.getCorreo());
        user.setTelefono(usuario.getTelefono());
        user.setUsername(usuario.getUsername());
        user.setPassword(passwordEncoder.encode(usuario.getPassword()));
        user.setRol(usuario.getRol());
        return usuarioRepository.save(user);
    }
}
