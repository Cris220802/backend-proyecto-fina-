package com.proyecto.time.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.time.entities.TimeTracking;
import com.proyecto.time.respository.TimeTrackingRepository;
import com.proyecto.time.respository.UsuarioRepository;

@RestController
@RequestMapping("/api/v1/time-tracking")
public class TimeTrackingController {
    private final TimeTrackingRepository timeTrackingRepository;
    private final UsuarioRepository usuarioRepository;

    public TimeTrackingController(TimeTrackingRepository timeTrackingRepository, UsuarioRepository usuarioRepository) {
        this.timeTrackingRepository = timeTrackingRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<TimeTracking> obtenerTodos() {
        return timeTrackingRepository.findAll();
    }

    @PostMapping("/{usuarioId}")
    public TimeTracking registrarTimeTracking(@PathVariable Long usuarioId, @RequestBody TimeTracking timeTracking) {
        return usuarioRepository.findById(usuarioId).map(usuario -> {
            timeTracking.setUsuario(usuario);
            return timeTrackingRepository.save(timeTracking);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

     // Obtener un registro específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<TimeTracking> obtenerPorId(@PathVariable Long id) {
        return timeTrackingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar un registro existente
    @PutMapping("/{id}")
    public ResponseEntity<TimeTracking> actualizarTimeTracking(@PathVariable Long id, @RequestBody TimeTracking detallesActualizados) {
        return timeTrackingRepository.findById(id).map(timeTracking -> {
           timeTracking.setCheckInTime(detallesActualizados.getCheckInTime());
           timeTracking.setCheckOutTime(detallesActualizados.getCheckOutTime());
            TimeTracking actualizado = timeTrackingRepository.save(timeTracking);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un registro por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarTimeTracking(@PathVariable Long id) {
        return timeTrackingRepository.findById(id).map(timeTracking -> {
            timeTrackingRepository.delete(timeTracking);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // Obtener todos los registros de time tracking de un usuario específico
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TimeTracking>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return usuarioRepository.findById(usuarioId).map(usuario -> {
            List<TimeTracking> registros = timeTrackingRepository.findByUsuario(usuario);
            return ResponseEntity.ok(registros);
        }).orElse(ResponseEntity.notFound().build());
    }
}
