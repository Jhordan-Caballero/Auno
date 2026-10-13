package com.auno.sistema_recordatorios.controller;

import com.auno.sistema_recordatorios.service.CitaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/paciente/mis-citas")
public class PacienteCitaController {

    private final CitaService citaService;

    public PacienteCitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public String verHistorial(Model model, Principal principal) {
        String emailPaciente = principal.getName();
        model.addAttribute("listaCitas", citaService.obtenerHistorialPaciente(emailPaciente));
        return "paciente/historial_citas";
    }

    @PostMapping("/cancelar")
    public String cancelarCita(@RequestParam("citaId") Long citaId, Principal principal) {
        try {
            String emailPaciente = principal.getName();
            citaService.cancelarCita(citaId, emailPaciente);
            return "redirect:/paciente/mis-citas?cancelada=true";
        } catch (Exception e) {
            return "redirect:/paciente/mis-citas?error=" + e.getMessage();
        }
    }
}
