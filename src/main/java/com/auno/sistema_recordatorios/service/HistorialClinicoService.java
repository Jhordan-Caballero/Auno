package com.auno.sistema_recordatorios.service;

import com.auno.sistema_recordatorios.dto.HistorialClinicoDTO;
import com.auno.sistema_recordatorios.entity.Cita;
import com.auno.sistema_recordatorios.entity.Historial_Clinico;
import com.auno.sistema_recordatorios.entity.Paciente;
import com.auno.sistema_recordatorios.repository.CitaRepository;
import com.auno.sistema_recordatorios.repository.HistorialClinicoRepository;
import com.auno.sistema_recordatorios.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
public class HistorialClinicoService {

    private final HistorialClinicoRepository historialRepository;
    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;

    public HistorialClinicoService(HistorialClinicoRepository historialRepository, CitaRepository citaRepository, PacienteRepository pacienteRepository) {
        this.historialRepository = historialRepository;
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public void registrarAtencion(HistorialClinicoDTO dto) {
        // 1. Buscar la cita
        Cita cita = citaRepository.findById(dto.getCitaId())
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));

        // 2. Validar estados
        if ("CANCELADA".equals(cita.getEstado())) {
            throw new IllegalStateException("No se puede registrar un historial para una cita cancelada.");
        }
        if ("ATENDIDA".equals(cita.getEstado())) {
            throw new IllegalStateException("Esta cita ya fue marcada como atendida.");
        }

        // 3. Crear el Historial Clínico usando tu entidad
        Historial_Clinico historial = new Historial_Clinico();
        historial.setCita(cita);
        historial.setPaciente(cita.getPaciente());
        // No seteamos el doctor porque está manejado a través de la Cita
        historial.setMotivoConsulta(dto.getMotivoConsulta());
        historial.setDiagnostico(dto.getDiagnostico());
        historial.setRecomendaciones(dto.getRecomendaciones()); // Reemplaza a tratamiento
        historial.setObservaciones(dto.getObservaciones());
        historial.setFecha(LocalDate.now()); // Usa LocalDate y tu atributo 'fecha'

        // 4. Cambiar el estado de la cita a ATENDIDA
        cita.setEstado("ATENDIDA");

        // 5. Guardar cambios
        citaRepository.save(cita);
        historialRepository.save(historial);
    }
    @Transactional(readOnly = true)
    public List<Historial_Clinico> obtenerHistorialPaciente(String emailPaciente) {
        Paciente paciente = pacienteRepository.findByUsuarioEmail(emailPaciente)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
        return historialRepository.findByPacienteIdOrderByFechaDesc(paciente.getId());
    }
}
