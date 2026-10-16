package com.auno.sistema_recordatorios.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redireccionarSegunRol(Authentication authentication) {
        if (authentication == null) {
            return "redirect:/inicio";
        }

        // Si es admin, se ira a ver las citas del día directamente
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                return "redirect:/admin/citas"; // Usa tu citas.html
            }
        }

        // Si es paciente, lo mandamos directo al inicio
        return "redirect:/paciente/reservar";
    }
}
