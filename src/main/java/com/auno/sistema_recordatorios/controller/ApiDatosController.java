package com.auno.sistema_recordatorios.controller;

import com.auno.sistema_recordatorios.entity.*;
import com.auno.sistema_recordatorios.repository.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiDatosController {

    private final UsuarioRepository usuarioRepository;
    private final DoctorRepository doctorRepository;
    private final PacienteRepository pacienteRepository;
    private final EspecialidadRepository especialidadRepository;

    public ApiDatosController(UsuarioRepository usuarioRepository,
                              DoctorRepository doctorRepository,
                              PacienteRepository pacienteRepository,
                              EspecialidadRepository especialidadRepository) {
        this.usuarioRepository = usuarioRepository;
        this.doctorRepository = doctorRepository;
        this.pacienteRepository = pacienteRepository;
        this.especialidadRepository = especialidadRepository;
    }

    // Ruta: http://localhost:8081/api/usuarios
    @GetMapping("/usuarios")
    public List<Usuario> listarUsuariosJson() {
        return usuarioRepository.findAll();
    }

    // Ruta: http://localhost:8081/api/doctores
    @GetMapping("/doctores")
    public List<Doctor> listarDoctoresJson() {
        return doctorRepository.findAll();
    }

    // Ruta: http://localhost:8081/api/pacientes
    @GetMapping("/pacientes")
    public List<Paciente> listarPacientesJson() {
        return pacienteRepository.findAll();
    }

    // Ruta: http://localhost:8081/api/especialidades
    @GetMapping("/especialidades")
    public List<Especialidad> listarEspecialidadesJson() {
        return especialidadRepository.findAll();
    }
}