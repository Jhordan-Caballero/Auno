package com.auno.sistema_recordatorios.controller;

import com.auno.sistema_recordatorios.entity.Doctor;
import com.auno.sistema_recordatorios.entity.Horario;
import com.auno.sistema_recordatorios.repository.HorarioRepository;
import com.auno.sistema_recordatorios.service.CitaService;
import com.auno.sistema_recordatorios.service.DoctorService;
import com.auno.sistema_recordatorios.service.EspecialidadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/paciente/reservar")
public class PacienteReservaController {

    private final EspecialidadService especialidadService;
    private final DoctorService doctorService;
    private final HorarioRepository horarioRepository;
    private final CitaService citaService;

    public PacienteReservaController(EspecialidadService especialidadService,
                                     DoctorService doctorService,
                                     HorarioRepository horarioRepository, CitaService citaService) {
        this.especialidadService = especialidadService;
        this.doctorService = doctorService;
        this.horarioRepository = horarioRepository;
        this.citaService = citaService;
    }

    // Muestra buscador (Especialidades y Doctores)
    @GetMapping
    public String mostrarBuscador(
            @RequestParam(value = "especialidadId", required = false) Long especialidadId,
            Model model) {

        // Cargamos todas las especialidades para el filtro
        model.addAttribute("listaEspecialidades", especialidadService.listarTodas());

        List<Doctor> doctoresDisponibles;
        if (especialidadId != null) {
            // Si el paciente filtró por especialidad, usamos Java "Streams" para filtrar la lista
            doctoresDisponibles = doctorService.listarTodos().stream()
                    .filter(d -> d.getEspecialidad().getId().equals(especialidadId))
                    .collect(Collectors.toList());
            model.addAttribute("especialidadSeleccionada", especialidadId);
        } else {
            // Mostramos todos
            doctoresDisponibles = doctorService.listarTodos();
        }

        model.addAttribute("listaDoctores", doctoresDisponibles);
        return "paciente/buscar_doctor";
    }

    // Mostrar horarios disponibles del doctor seleccionado
    @GetMapping("/horarios")
    public String verHorariosDisponibles(@RequestParam("doctorId") Long doctorId, Model model) {
        // Buscamos solo los horarios disponibles de hoy en adelante
        List<Horario> horariosLibres = horarioRepository
                .findByDoctorIdAndEstadoAndFechaGreaterThanEqualOrderByFechaAscHoraInicioAsc(
                        doctorId, "DISPONIBLE", LocalDate.now());

        model.addAttribute("horariosDisponibles", horariosLibres);
        model.addAttribute("doctorId", doctorId);

        return "paciente/seleccionar_horario";
    }

    @PostMapping("/confirmar")
    public String confirmarReserva(@RequestParam("horarioId") Long horarioId, Principal principal) {
        // Principal contiene los datos de la sesión actual de Spring Security (el correo del usuario)
        String emailPaciente = principal.getName();
        citaService.registrarCita(horarioId, emailPaciente);

        // Redirigimos al inicio
        return "redirect:/paciente/reservar?reservaExito";
    }


    @GetMapping("/paciente/inicio")
    public String inicioPaciente() {
        return "paciente/inicio";
    }
}