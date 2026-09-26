package com.auno.sistema_recordatorios.controller;

import com.auno.sistema_recordatorios.dto.GenerarHorarioDTO;
import com.auno.sistema_recordatorios.entity.Horario;
import com.auno.sistema_recordatorios.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/horarios")
public class HorarioController {
    @Autowired
    private HorarioService horarioService;

    // Endpoint: POST http://localhost:8080/api/horarios/generar
    @PostMapping("/generar")
    public ResponseEntity<?> generarHorarios(@RequestBody GenerarHorarioDTO dto) {
        try {
            List<Horario> horariosGenerados = horarioService.generarHorariosAutomaticos(dto);
            return new ResponseEntity<>(horariosGenerados.size() + " horarios generados exitosamente.", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
