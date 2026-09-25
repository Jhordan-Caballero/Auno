package com.auno.sistema_recordatorios.controller;

import com.auno.sistema_recordatorios.dto.RegistroDoctorDTO;
import com.auno.sistema_recordatorios.entity.Doctor;
import com.auno.sistema_recordatorios.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctores")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    // Endpoint para registrar un doctor
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarDoctor(@RequestBody RegistroDoctorDTO dto) {
        try {
            Doctor doctorGuardado = doctorService.registrarDoctor(dto);
            return new ResponseEntity<>(doctorGuardado, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Devuelve un error 400 Bad Request si falla alguna validación
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint para listar todos los doctores
    @GetMapping
    public ResponseEntity<List<Doctor>> listarDoctores() {
        List<Doctor> doctores = doctorService.obtenerTodos();
        return new ResponseEntity<>(doctores, HttpStatus.OK);
    }
}
