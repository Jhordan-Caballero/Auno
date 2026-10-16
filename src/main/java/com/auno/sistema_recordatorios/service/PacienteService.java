package com.auno.sistema_recordatorios.service;

import com.auno.sistema_recordatorios.dto.RegistroPacienteDTO;
import com.auno.sistema_recordatorios.entity.*;
import com.auno.sistema_recordatorios.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public PacienteService(PacienteRepository pacienteRepository,
                           UsuarioRepository usuarioRepository,
                           RolRepository rolRepository,
                           PasswordEncoder passwordEncoder, EmailService emailService) {
        this.pacienteRepository = pacienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    public void registrarPaciente(RegistroPacienteDTO dto) {
        // Validar que el correo no esté en uso
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El correo ya está registrado en el sistema");
        }

        // Obtener el rol de paciente
        Rol rolPaciente = rolRepository.findByNombre("ROLE_PATIENT")
                .orElseGet(() -> rolRepository.save(new Rol("ROLE_PATIENT")));

        // Mapear datos al Usuario y encriptar contraseña
        Usuario usuario = new Usuario();
        usuario.setNombres(dto.getNombres());
        usuario.setApellidos(dto.getApellidos());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setTelefono(dto.getTelefono());
        usuario.setEstado("ACTIVO");
        usuario.agregarRol(rolPaciente);

        // 4. Mapear datos al Paciente y vincular el Usuario
        Paciente paciente = new Paciente();
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        paciente.setDireccion(dto.getDireccion());
        paciente.setUsuario(usuario);


        pacienteRepository.save(paciente);

        try {
            emailService.enviarCorreoBienvenida(dto.getEmail(), dto.getNombres());
        } catch (Exception e) {
            System.err.println("Error al enviar el correo de bienvenida: " + e.getMessage());
        }
    }

}
