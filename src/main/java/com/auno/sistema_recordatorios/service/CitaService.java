package com.auno.sistema_recordatorios.service;

import com.auno.sistema_recordatorios.dto.ReservaCitaDTO;
import com.auno.sistema_recordatorios.entity.*;
import com.auno.sistema_recordatorios.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitaService {
    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private HorarioRepository horarioRepository;

    @Transactional
    public Cita reservarCita(ReservaCitaDTO dto) {
        // 1. Validar que el paciente exista
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        // 2. Validar que el horario exista
        Horario horario = horarioRepository.findById(dto.getHorarioId())
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        // 3. Validar que el horario esté disponible
        if (!horario.getEstado().equals("DISPONIBLE")) {
            throw new RuntimeException("Este horario ya no está disponible");
        }

        // 4. Cambiar el estado del horario para que nadie más lo tome
        horario.setEstado("RESERVADO");
        horarioRepository.save(horario);

        // 5. Crear la cita
        Cita nuevaCita = new Cita();
        nuevaCita.setPaciente(paciente);
        nuevaCita.setHorario(horario);
        nuevaCita.setMotivo(dto.getMotivo());
        nuevaCita.setEstado("CONFIRMADA");

        return citaRepository.save(nuevaCita);
    }
}
