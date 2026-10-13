package com.auno.sistema_recordatorios.controller;

import com.auno.sistema_recordatorios.service.HistorialClinicoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/paciente/diagnosticos")
public class PacienteHistorialController {

    private final HistorialClinicoService historialClinicoService;

    public PacienteHistorialController(HistorialClinicoService historialClinicoService) {
        this.historialClinicoService = historialClinicoService;
    }

    @GetMapping
    public String verMisDiagnosticos(Model model, Principal principal) {
        String emailPaciente = principal.getName();
        model.addAttribute("listaDiagnosticos", historialClinicoService.obtenerHistorialPaciente(emailPaciente));
        return "paciente/diagnosticos";
    }
}