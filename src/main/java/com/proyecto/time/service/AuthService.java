package com.proyecto.time.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.time.entities.Usuario;
import com.proyecto.time.entities.Token;
import com.proyecto.time.response.LoginRequest;
import com.proyecto.time.response.RegisterRequest;
import com.proyecto.time.response.TokenResponse;
import com.proyecto.time.respository.TokenRepository;
import com.proyecto.time.respository.UsuarioRepository;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse register(RegisterRequest request) {
        var user = Usuario.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();
        var savedUser = usuarioRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()));
        var user = usuarioRepository.findByUsername(request.username())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return new TokenResponse(jwtToken, refreshToken);
        // revokeAllUserTokens(user);

    }

    public void saveUserToken(Usuario user, String jwtToken) {
        var token = Token.builder()
                .usuario(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public TokenResponse refreshToken(String authHeader) {
        System.out.println("Este es el auth:" + authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token invalido 1");
        }

        String refreshToken = authHeader.substring(7);
        String username = jwtService.extractUsername(refreshToken);

        if (username != null) {
            throw new IllegalArgumentException("Token de refresco invalido");
        }

        Usuario user = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new IllegalArgumentException("Token de refresco expirado");
        }

        String accessToken = jwtService.generateToken(user);
        return new TokenResponse(accessToken, refreshToken);

    }

    
}
