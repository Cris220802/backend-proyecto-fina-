package com.proyecto.time.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.time.entities.Usuario;
import com.proyecto.time.respository.UsuarioRepository;
import com.proyecto.time.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    // @PostMapping("/register")
    // public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
    //     System.out.println("Datos recibidos en el body: " + usuario);
    //     try {
    //         usuarioService.registerUser(usuario);
    //         return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.CREATED);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>("Error al registrar usuario: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    //     }
    // }

    // @PostMapping("/login")
    // public ResponseEntity<String> loginUsuario(@RequestBody Usuario usuario) {
    //     System.out.println("Datos recibidos en el body: " + usuario);
    //     try {
    //         return usuarioService.loguearUsuario(usuario);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>("Error al registrar usuario: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    //     }
    // }
}