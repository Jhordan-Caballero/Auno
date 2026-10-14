package com.auno.sistema_recordatorios.service;

import com.auno.sistema_recordatorios.entity.*;
import com.auno.sistema_recordatorios.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final HorarioRepository horarioRepository;
    private final PacienteRepository pacienteRepository;
    private final EmailService emailService;

    public CitaService(CitaRepository citaRepository, HorarioRepository horarioRepository, PacienteRepository pacienteRepository, EmailService emailService) {
        this.citaRepository = citaRepository;
        this.horarioRepository = horarioRepository;
        this.pacienteRepository = pacienteRepository;
        this.emailService = emailService;
    }

    @Transactional
    public void registrarCita(Long horarioId, String emailPaciente) {
        // 1. Buscar al paciente logueado
        Paciente paciente = pacienteRepository.findByUsuarioEmail(emailPaciente)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        // 2. Buscar el horario y verificar que siga disponible
        Horario horario = horarioRepository.findById(horarioId)
                .orElseThrow(() -> new IllegalArgumentException("Horario no encontrado"));

        if (!"DISPONIBLE".equals(horario.getEstado())) {
            throw new IllegalStateException("El horario ya no está disponible.");
        }

        // 3. Crear la Cita (con los campos correctos basados en tu entidad)
        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setDoctor(horario.getDoctor()); // Añadimos el doctor sacándolo del horario
        cita.setHorario(horario);
        cita.setFechaReserva(LocalDateTime.now()); // Corregido: fechaReserva en lugar de fechaRegistro
        cita.setEstado("PENDIENTE"); // O "PROGRAMADA", el que prefieras como estado inicial

        // 4. Cambiar el estado del horario a RESERVADO
        horario.setEstado("RESERVADO");

        // 5. Guardar los cambios
        horarioRepository.save(horario);
        citaRepository.save(cita);

        try {
            emailService.enviarCorreoConfirmacionCita(
                    emailPaciente,
                    paciente.getUsuario().getNombres(),
                    cita
            );
        } catch (Exception e) {
            System.err.println("Error al enviar el correo de confirmación: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<Cita> obtenerHistorialPaciente(String emailPaciente) {
        return citaRepository.findByPacienteUsuarioEmailOrderByHorarioFechaDesc(emailPaciente);
    }

    @Transactional
    public void cancelarCita(Long citaId, String emailPaciente) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));

        // 1. Seguridad: Verificar que el paciente logueado sea el dueño de la cita
        if (!cita.getPaciente().getUsuario().getEmail().equals(emailPaciente)) {
            throw new SecurityException("No tienes permiso para cancelar esta cita.");
        }

        // 2. Verificar que la cita esté en un estado cancelable
        if ("CANCELADA".equals(cita.getEstado()) || "ATENDIDA".equals(cita.getEstado())) {
            throw new IllegalStateException("Esta cita ya fue cancelada o atendida.");
        }

        // 3. Cancelar la cita
        cita.setEstado("CANCELADA");

        // 4. ¡MUY IMPORTANTE! Liberar el horario para que otro paciente lo pueda tomar
        Horario horario = cita.getHorario();
        horario.setEstado("DISPONIBLE");

        // 5. Guardar los cambios
        horarioRepository.save(horario);
        citaRepository.save(cita);
    }
}
