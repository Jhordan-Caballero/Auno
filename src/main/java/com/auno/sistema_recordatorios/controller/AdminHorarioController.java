package com.auno.sistema_recordatorios.controller;

import com.auno.sistema_recordatorios.dto.HorarioDTO;
import com.auno.sistema_recordatorios.service.DoctorService;
import com.auno.sistema_recordatorios.service.HorarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/horarios")
public class AdminHorarioController {

    private final HorarioService horarioService;
    private final DoctorService doctorService;

    // Inyectamos ambos servicios porque necesitamos la lista de doctores para el formulario
    public AdminHorarioController(HorarioService horarioService, DoctorService doctorService) {
        this.horarioService = horarioService;
        this.doctorService = doctorService;
    }

    @GetMapping
    public String gestionarHorarios(Model model) {
        model.addAttribute("listaHorarios", horarioService.listarTodos());
        model.addAttribute("listaDoctores", doctorService.listarTodos());
        model.addAttribute("horarioDTO", new HorarioDTO());
        return "admin/horarios"; // Renderizará la vista en templates/admin/horarios.html
    }

    @PostMapping("/registro")
    public String registrarHorario(@Valid @ModelAttribute("horarioDTO") HorarioDTO dto,
                                   BindingResult result,
                                   Model model) {

        if (result.hasErrors()) {
            model.addAttribute("listaHorarios", horarioService.listarTodos());
            model.addAttribute("listaDoctores", doctorService.listarTodos());
            return "admin/horarios";
        }

        try {
            horarioService.registrarHorario(dto);
            return "redirect:/admin/horarios?exito";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("listaHorarios", horarioService.listarTodos());
            model.addAttribute("listaDoctores", doctorService.listarTodos());
            return "admin/horarios";
        }
    }
}
