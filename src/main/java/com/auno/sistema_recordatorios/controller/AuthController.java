package com.auno.sistema_recordatorios.controller;

import com.auno.sistema_recordatorios.dto.RegistroPacienteDTO;
import com.auno.sistema_recordatorios.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final PacienteService pacienteService;

    public AuthController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping("/login")
    public String mostrarVistaLogin() {
        // Spring Security usará esta ruta para mostrar el formulario
        return "login";
    }

    @GetMapping("/registro")
    public String mostrarVistaRegistro(Model model) {
        // DTO vacío a la vista para que Thymeleaf lo llene con el formulario
        model.addAttribute("pacienteDTO", new RegistroPacienteDTO());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarPaciente(@Valid @ModelAttribute("pacienteDTO") RegistroPacienteDTO dto,
                                    BindingResult result,
                                    Model model) {

        System.out.println(" Se recibió la petición POST.");
        System.out.println("Datos recibidos: " + dto.getEmail() + " - " + dto.getNombres());

        if (result.hasErrors()) {
            System.out.println("Error en la validación");
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            return "registro";
        }

        try {
            System.out.println("Intentando guardar en la base de datos");
            pacienteService.registrarPaciente(dto);
            System.out.println("Guardado exitoso.");
            return "redirect:/login?exito";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        } catch (Exception e) { // Error al guardar
            System.out.println("Error al guardar " + e.getMessage());
            e.printStackTrace();
            return "registro";
        }
    }
}
