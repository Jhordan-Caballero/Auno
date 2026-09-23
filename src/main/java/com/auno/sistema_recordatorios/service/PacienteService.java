package com.auno.sistema_recordatorios.service;

import com.auno.sistema_recordatorios.dto.RegistroPacienteDTO;
import com.auno.sistema_recordatorios.entity.*;
import com.auno.sistema_recordatorios.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Agregamos el repositorio de roles
    @Autowired
    private RolRepository rolRepository;

    // Lógica para registrar un paciente
    public Paciente registrarPaciente(RegistroPacienteDTO dto) {
        // 1. Validar que el email no exista ya
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // 2. Buscar el rol en la base de datos
        // Nota: Asegúrate de que "ROLE_PACIENTE" exista previamente en la tabla de roles
        Rol rolPaciente = rolRepository.findByNombre("ROLE_PACIENTE")
                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));

        // 3. Crear el Usuario usando el constructor de 2 argumentos y agregarle el rol
        Usuario nuevoUsuario = new Usuario(dto.getEmail(), dto.getPassword());
        nuevoUsuario.agregarRol(rolPaciente);

        // 4. Crear el Paciente y asignarle el Usuario
        Paciente nuevoPaciente = new Paciente(dto.getNombre(), nuevoUsuario);

        // 5. Guardar en la base de datos
        return pacienteRepository.save(nuevoPaciente);
    }

    // Lógica para obtener todos los pacientes
    public List<Paciente> obtenerTodos() {
        return pacienteRepository.findAll();
    }
}
