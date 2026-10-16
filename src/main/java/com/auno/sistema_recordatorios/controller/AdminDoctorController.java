package com.auno.sistema_recordatorios.controller;

import com.auno.sistema_recordatorios.dto.DoctorDTO;

import com.auno.sistema_recordatorios.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin/doctores")
public class AdminDoctorController {

    private final DoctorService doctorService;
    private final EspecialidadService especialidadService;

    public AdminDoctorController(DoctorService doctorService, EspecialidadService especialidadService) {
        this.doctorService = doctorService;
        this.especialidadService = especialidadService;
    }

    @GetMapping
    public String gestionarDoctores(Model model) {
        // Enviamos la lista de doctores para la tabla
        model.addAttribute("listaDoctores", doctorService.listarTodos());
        // Enviamos la lista de especialidades para llenar el <select>
        model.addAttribute("listaEspecialidades", especialidadService.listarTodas());
        // Enviamos el DTO vacío para el formulario
        model.addAttribute("doctorDTO", new DoctorDTO());

        return "admin/doctores"; // La vista en doctores.html
    }

    @PostMapping("/registro")
    public String registrarDoctor(@Valid @ModelAttribute("doctorDTO") DoctorDTO dto,
                                  BindingResult result,
                                  Model model) {

        if (result.hasErrors()) {
            model.addAttribute("listaDoctores", doctorService.listarTodos());
            model.addAttribute("listaEspecialidades", especialidadService.listarTodas());
            return "admin/doctores";
        }

        try {
            doctorService.registrarDoctor(dto);
            return "redirect:/admin/doctores?exito";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("listaDoctores", doctorService.listarTodos());
            model.addAttribute("listaEspecialidades", especialidadService.listarTodas());
            return "admin/doctores";
        }
    }
}
