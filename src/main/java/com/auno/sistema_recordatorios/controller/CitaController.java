package com.auno.sistema_recordatorios.controller;

import com.auno.sistema_recordatorios.dto.ReservaCitaDTO;
import com.auno.sistema_recordatorios.entity.Cita;
import com.auno.sistema_recordatorios.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/citas")
public class CitaController {
    @Autowired
    private CitaService citaService;

    @PostMapping("/reservar")
    public ResponseEntity<?> reservar(@RequestBody ReservaCitaDTO dto) {
        try {
            Cita citaReservada = citaService.reservarCita(dto);
            return new ResponseEntity<>(citaReservada, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
