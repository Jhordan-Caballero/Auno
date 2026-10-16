package com.auno.sistema_recordatorios.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

    @GetMapping("/inicio")
    public String mostrarPortada() {
        return "inicio"; // Busca el archivo inicio.html
    }
}
