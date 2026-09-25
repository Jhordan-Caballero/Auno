package com.auno.sistema_recordatorios.service;


import com.auno.sistema_recordatorios.dto.RegistroDoctorDTO;
import com.auno.sistema_recordatorios.entity.*;
import com.auno.sistema_recordatorios.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private EspecialidadRepository especialidadRepository;

    public Doctor registrarDoctor(RegistroDoctorDTO dto) {
        // 1. Validar email
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // 2. Buscar la especialidad
        Especialidad especialidad = especialidadRepository.findById(dto.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        // 3. Buscar el rol
        Rol rolDoctor = rolRepository.findByNombre("ROLE_DOCTOR")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // 4. Crear usuario y asignar rol
        Usuario nuevoUsuario = new Usuario(dto.getEmail(), dto.getPassword());
        nuevoUsuario.agregarRol(rolDoctor);

        // 5. Crear doctor
        Doctor nuevoDoctor = new Doctor();
        nuevoDoctor.setNombre(dto.getNombre());
        nuevoDoctor.setApellido(dto.getApellido());
        nuevoDoctor.setEspecialidad(especialidad);
        nuevoDoctor.setUsuario(nuevoUsuario);

        return doctorRepository.save(nuevoDoctor);
    }

    public List<Doctor> obtenerTodos() {
        return doctorRepository.findAll();
    }
}
