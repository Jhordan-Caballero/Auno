package com.auno.sistema_recordatorios.controller;

import com.auno.sistema_recordatorios.dto.RegistroPacienteDTO;
import com.auno.sistema_recordatorios.dto.ReservaCitaDTO;
import com.auno.sistema_recordatorios.entity.Horario;
import com.auno.sistema_recordatorios.entity.Paciente;
import com.auno.sistema_recordatorios.repository.*;
import com.auno.sistema_recordatorios.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class WebController {
    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping("/")
    public String raiz() {
        return "redirect:/login";
    }

    // 2. Mostrar la página de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    // 3. Simulación visual del botón "Iniciar sesión"
    // Cuando el formulario del login haga POST, simplemente lo redirigimos a /inicio
    @PostMapping("/login")
    public String procesarLoginTemporal() {
        return "redirect:/inicio";
    }

    // 4. Mostrar la página de registro (Mantén el código que ya teníamos)
    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("paciente", new RegistroPacienteDTO());
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

    @Autowired
    private HorarioRepository horarioRepository;

    @GetMapping("/reservar_cita")
    public String reservarCita(Model model) {
        // 1. Preparamos el molde para recibir los datos de la reserva
        ReservaCitaDTO reserva = new ReservaCitaDTO();
        reserva.setPacienteId(1L); // Simulamos que el paciente 1 es el que está reservando

        // 2. Extraemos todos los horarios disponibles de la base de datos
        // (En un futuro, se pueden filtrar por el ID del doctor usando RequestParam)
        List<Horario> horariosDisponibles = horarioRepository.findAll().stream()
                .filter(h -> h.getEstado().equals("DISPONIBLE"))
                .toList();

        model.addAttribute("reserva", reserva);
        model.addAttribute("horarios", horariosDisponibles);

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
