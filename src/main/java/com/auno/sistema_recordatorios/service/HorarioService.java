package com.auno.sistema_recordatorios.service;


import com.auno.sistema_recordatorios.dto.HorarioDTO;
import com.auno.sistema_recordatorios.entity.*;
import com.auno.sistema_recordatorios.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HorarioService {private final HorarioRepository horarioRepository;
    private final DoctorRepository doctorRepository;

    public HorarioService(HorarioRepository horarioRepository, DoctorRepository doctorRepository) {
        this.horarioRepository = horarioRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public void registrarHorario(HorarioDTO dto) {
        // 1. Validar lógica de horas
        if (dto.getHoraInicio().isAfter(dto.getHoraFin()) || dto.getHoraInicio().equals(dto.getHoraFin())) {
            throw new IllegalArgumentException("La hora de inicio debe ser estrictamente anterior a la hora de fin.");
        }

        // 2. Validar duplicidad
        if (horarioRepository.existsByDoctorIdAndFechaAndHoraInicio(dto.getDoctorId(), dto.getFecha(), dto.getHoraInicio())) {
            throw new IllegalArgumentException("El doctor ya tiene un turno registrado en esa fecha y hora exacta.");
        }

        // 3. Buscar al doctor
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("El médico seleccionado no existe en el sistema."));

        // 4. Crear y guardar
        Horario horario = new Horario();
        horario.setDoctor(doctor);
        horario.setFecha(dto.getFecha());
        horario.setHoraInicio(dto.getHoraInicio());
        horario.setHoraFin(dto.getHoraFin());
        horario.setEstado("DISPONIBLE");

        horarioRepository.save(horario);
    }

    @Transactional(readOnly = true)
    public List<Horario> listarTodos() {
        return horarioRepository.findAll();
    }
}
