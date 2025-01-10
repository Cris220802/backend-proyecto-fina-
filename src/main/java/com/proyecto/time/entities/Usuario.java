package com.proyecto.time.entities;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeTracking> timeTrackings;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Token> tokens;

    public void setTimeTrackings(List<TimeTracking> timeTrackings) {
        if (timeTrackings != null) {
            timeTrackings.forEach(timeTracking -> timeTracking.setUsuario(this));
        }
        this.timeTrackings = timeTrackings;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                // Evitar incluir la lista de tokens para prevenir recursión
                '}';
    }
}