package com.proyecto.time.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.time.entities.TimeTracking;
import com.proyecto.time.entities.Usuario;

public interface TimeTrackingRepository extends JpaRepository<TimeTracking, Long>{

    List<TimeTracking> findByUsuario(Usuario usuario);
}
