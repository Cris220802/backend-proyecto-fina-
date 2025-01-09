package com.proyecto.time.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "time_tracking")
public class TimeTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackingId;

    @Column(nullable = false)
    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    @ManyToOne
    @JoinColumn(name = "employe_id", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    // Campo calculado
    @JsonProperty("employe_Id")
    public Long getEmployeId() {
        return usuario != null ? usuario.getId() : null;
    }
}