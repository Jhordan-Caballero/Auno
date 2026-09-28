package com.auno.sistema_recordatorios.controller;

import com.auno.sistema_recordatorios.entity.Paciente;
import com.auno.sistema_recordatorios.repository.*;
import com.auno.sistema_recordatorios.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro(Model model) {
        return "registro";
    }

    @GetMapping("/inicio")
    public String inicio(Model model) {
        // 1. Extraer datos reales de la base de datos
        model.addAttribute("especialidades", especialidadRepository.findAll());
        model.addAttribute("doctores", doctorService.obtenerTodos());

        // 2. Simular el paciente logueado (Traemos al paciente con ID 1)
        // Más adelante, esto se reemplazará leyendo el token de Spring Security
        Paciente paciente = pacienteRepository.findById(1L)
                .orElse(new Paciente("Invitado", null));

        model.addAttribute("paciente", paciente);

        return "inicio";
    }

    @GetMapping("/reservar_cita")
    public String reservarCita(Model model) {
        return "reservar_cita";
    }

    @GetMapping("/mis_citas")
    public String misCitas(Model model) {
        return "mis_citas";
    }

    @GetMapping("/historial")
    public String historial(Model model) {
        return "historial";
    }
}
