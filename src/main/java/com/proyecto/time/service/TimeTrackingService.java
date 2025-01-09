package com.proyecto.time.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.time.entities.TimeTracking;
import com.proyecto.time.entities.Usuario;
import com.proyecto.time.records.TimeRequest;
import com.proyecto.time.respository.TimeTrackingRepository;

@Service
public class TimeTrackingService {

    @Autowired
    private TimeTrackingRepository timeTrackingRepository;
    public TimeTracking saveTimeTracking(TimeRequest request, Usuario user) {
        var time = TimeTracking.builder()
            .checkInTime(request.checkInTime())
            .checkOutTime(request.checkOutTime())
            .usuario(user)
            .build();
        timeTrackingRepository.save(time);
        return time;
    }
}
