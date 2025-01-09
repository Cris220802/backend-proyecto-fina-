package com.proyecto.time.records;

import java.time.LocalDateTime;

import com.proyecto.time.entities.Usuario;

public record TimeRequest(
    LocalDateTime checkInTime,
    LocalDateTime checkOutTime
) {

}
