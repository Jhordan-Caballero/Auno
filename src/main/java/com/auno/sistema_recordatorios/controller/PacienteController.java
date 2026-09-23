package com.auno.sistema_recordatorios.controller;

import com.auno.sistema_recordatorios.dto.RegistroPacienteDTO;
import com.auno.sistema_recordatorios.entity.Paciente;
import com.auno.sistema_recordatorios.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    // Endpoint para registrar: POST http://localhost:8080/api/pacientes/registrar
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarPaciente(@RequestBody RegistroPacienteDTO dto) {
        try {
            Paciente pacienteGuardado = pacienteService.registrarPaciente(dto);
            return new ResponseEntity<>(pacienteGuardado, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Si el correo ya existe, mandamos un error 400
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint para listar: GET http://localhost:8080/api/pacientes
    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        List<Paciente> pacientes = pacienteService.obtenerTodos();
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }
}
