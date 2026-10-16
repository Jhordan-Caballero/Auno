package com.auno.sistema_recordatorios.service;

import com.auno.sistema_recordatorios.dto.DoctorDTO;

import com.auno.sistema_recordatorios.entity.Doctor;
import com.auno.sistema_recordatorios.entity.Especialidad;
import com.auno.sistema_recordatorios.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {private final DoctorRepository doctorRepository;
    private final EspecialidadRepository especialidadRepository;

    // Inyectamos ambos repositorios
    public DoctorService(DoctorRepository doctorRepository, EspecialidadRepository especialidadRepository) {
        this.doctorRepository = doctorRepository;
        this.especialidadRepository = especialidadRepository;
    }

    @Transactional
    public void registrarDoctor(DoctorDTO dto) {
        // Validaciones de duplicidad
        if (doctorRepository.existsByNumeroColegiatura(dto.getNumeroColegiatura())) {
            throw new IllegalArgumentException("Ya existe un doctor registrado con ese número de colegiatura.");
        }
        if (doctorRepository.existsByCorreoContacto(dto.getCorreoContacto())) {
            throw new IllegalArgumentException("El correo de contacto ya está asignado a otro doctor.");
        }

        // Buscar la especialidad. Si no existe, lanzamos error.
        Especialidad especialidad = especialidadRepository.findById(dto.getEspecialidadId())
                .orElseThrow(() -> new IllegalArgumentException("La especialidad seleccionada no existe."));

        // Crear y mapear la entidad Doctor
        Doctor doctor = new Doctor();
        doctor.setNombres(dto.getNombres());
        doctor.setApellidos(dto.getApellidos());
        doctor.setNumeroColegiatura(dto.getNumeroColegiatura());
        doctor.setDescripcion(dto.getDescripcion());
        doctor.setCorreoContacto(dto.getCorreoContacto());
        doctor.setTelefonoContacto(dto.getTelefonoContacto());
        doctor.setEstado("Activo");
        doctor.setEspecialidad(especialidad); // Vincular la relación

        // 4. Guardar
        doctorRepository.save(doctor);
    }

    @Transactional(readOnly = true)
    public List<Doctor> listarTodos() {
        return doctorRepository.findAll();
    }
}
