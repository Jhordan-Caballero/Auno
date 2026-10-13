package com.auno.sistema_recordatorios.controller;

import com.auno.sistema_recordatorios.dto.EspecialidadDTO;
import com.auno.sistema_recordatorios.service.EspecialidadService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/especialidades")
public class AdminEspecialidadController {

    private final EspecialidadService especialidadService;

    public AdminEspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    // Muestra la lista de especialidades y el formulario para crear una nueva
    @GetMapping
    public String gestionarEspecialidades(Model model) {
        model.addAttribute("listaEspecialidades", especialidadService.listarTodas());
        model.addAttribute("especialidadDTO", new EspecialidadDTO());
        return "admin/especialidades"; // Buscará el archivo en templates/admin/especialidades.html
    }

    @PostMapping("/registro")
    public String registrarEspecialidad(@Valid @ModelAttribute("especialidadDTO") EspecialidadDTO dto,
                                        BindingResult result,
                                        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listaEspecialidades", especialidadService.listarTodas());
            return "admin/especialidades";
        }

        try {
            especialidadService.registrarEspecialidad(dto);
            return "redirect:/admin/especialidades?exito";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("listaEspecialidades", especialidadService.listarTodas());
            return "admin/especialidades";
        }
    }
}
