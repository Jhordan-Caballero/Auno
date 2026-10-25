package com.auno.sistema_recordatorios.config;

import com.auno.sistema_recordatorios.entity.*;
import com.auno.sistema_recordatorios.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataSeedConfig {

    @Bean
    @Transactional
    public CommandLineRunner initData(UsuarioRepository usuarioRepository,
                                      RolRepository rolRepository,
                                      EspecialidadRepository especialidadRepository,
                                      DoctorRepository doctorRepository,
                                      PacienteRepository pacienteRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {


            Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN")
                    .orElseGet(() -> rolRepository.save(new Rol("ROLE_ADMIN")));

            Rol rolPaciente = rolRepository.findByNombre("ROLE_PATIENT")
                    .orElseGet(() -> rolRepository.save(new Rol("ROLE_PATIENT")));

            if (!usuarioRepository.existsByEmail("admin@sistema.com")) {
                Usuario admin = new Usuario();
                admin.setNombres("Administrador");
                admin.setApellidos("Principal");
                admin.setEmail("admin@sistema.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setTelefono("999999999");
                admin.setEstado("ACTIVO");

                admin.agregarRol(rolAdmin);
                usuarioRepository.save(admin);
            }

            crearPacienteSiNoExiste(usuarioRepository, pacienteRepository, rolPaciente, passwordEncoder,
                    "Juan", "Pérez", "paciente@sistema.com", "987654321", LocalDate.of(1990, 5, 15), "Av. Principal 123");

            crearPacienteSiNoExiste(usuarioRepository, pacienteRepository, rolPaciente, passwordEncoder,
                    "María", "Gómez", "maria@sistema.com", "912345678", LocalDate.of(1985, 8, 22), "Calle Las Flores 456");

            crearPacienteSiNoExiste(usuarioRepository, pacienteRepository, rolPaciente, passwordEncoder,
                    "Luis", "Fernández", "luis@sistema.com", "923456789", LocalDate.of(1992, 11, 10), "Urb. Los Pinos 789");

            crearPacienteSiNoExiste(usuarioRepository, pacienteRepository, rolPaciente, passwordEncoder,
                    "Ana", "Torres", "ana@sistema.com", "934567890", LocalDate.of(1978, 3, 5), "Av. El Sol 321");

            crearPacienteSiNoExiste(usuarioRepository, pacienteRepository, rolPaciente, passwordEncoder,
                    "Jorge", "Salinas", "jorge@sistema.com", "945678901", LocalDate.of(1995, 12, 30), "Pasaje Libertad 654");

            crearPacienteSiNoExiste(usuarioRepository, pacienteRepository, rolPaciente, passwordEncoder,
                    "Lucía", "Vargas", "lucia@sistema.com", "956789012", LocalDate.of(2000, 7, 18), "Residencial San Marcos");

            if (especialidadRepository.count() == 0) {
                especialidadRepository.save(crearEspecialidad("Cardiología", "Estudio, diagnóstico y tratamiento de las enfermedades del corazón."));
                especialidadRepository.save(crearEspecialidad("Medicina General", "Primer nivel de atención médica para patologías comunes."));
                especialidadRepository.save(crearEspecialidad("Pediatría", "Atención médica integral de bebés, niños y adolescentes."));
                especialidadRepository.save(crearEspecialidad("Dermatología", "Diagnóstico y tratamiento de enfermedades de la piel, cabello y uñas."));
                especialidadRepository.save(crearEspecialidad("Traumatología", "Especialidad enfocada en lesiones del sistema locomotor y muscular."));
                especialidadRepository.save(crearEspecialidad("Neurología", "Estudio y tratamiento de los trastornos del sistema nervioso."));
            }

            if (doctorRepository.count() == 0) {
                List<Especialidad> especialidades = especialidadRepository.findAll();
                if (especialidades.size() >= 6) {
                    doctorRepository.save(crearDoctor("Carlos", "Mendoza", "12345", "cmendoza@auno.com", "999888777",
                            "Especialista con más de 15 años de experiencia clínica.", especialidades.get(0)));

                    doctorRepository.save(crearDoctor("Laura", "Valdez", "54321", "lvaldez@auno.com", "988777666",
                            "Pediatra dedicada al cuidado integral infantil.", especialidades.get(2)));

                    doctorRepository.save(crearDoctor("Roberto", "Sánchez", "13579", "rsanchez@auno.com", "977666555",
                            "Experto en rehabilitación deportiva y lesiones óseas.", especialidades.get(4)));

                    doctorRepository.save(crearDoctor("Elena", "Campos", "97531", "ecampos@auno.com", "966555444",
                            "Especialista en dermatología clínica y estética.", especialidades.get(3)));
                }
            }


            System.out.println("Admin: admin@sistema.com / admin123");
            System.out.println("Pacientes (6 generados): clave 'paciente123' para todos.");

        };
    }

    private void crearPacienteSiNoExiste(UsuarioRepository usuarioRepo, PacienteRepository pacienteRepo, Rol rol,
                                         PasswordEncoder encoder, String nom, String ape, String email,
                                         String tel, LocalDate fechaNac, String dir) {
        if (!usuarioRepo.existsByEmail(email)) {
            Usuario user = new Usuario();
            user.setNombres(nom);
            user.setApellidos(ape);
            user.setEmail(email);
            user.setPassword(encoder.encode("paciente123"));
            user.setTelefono(tel);
            user.setEstado("ACTIVO");
            user.agregarRol(rol);

            Paciente paciente = new Paciente();
            paciente.setUsuario(user);
            paciente.setFechaNacimiento(fechaNac);
            paciente.setDireccion(dir);
            pacienteRepo.save(paciente);
        }
    }

    private Especialidad crearEspecialidad(String nombre, String descripcion) {
        Especialidad esp = new Especialidad();
        esp.setNombre(nombre);
        esp.setDescripcion(descripcion);
        return esp;
    }

    private Doctor crearDoctor(String nombres, String apellidos, String cmp, String correo,
                               String telefono, String desc, Especialidad esp) {
        Doctor doc = new Doctor();
        doc.setNombres(nombres);
        doc.setApellidos(apellidos);
        doc.setNumeroColegiatura(cmp);
        doc.setCorreoContacto(correo);
        doc.setTelefonoContacto(telefono);
        doc.setDescripcion(desc);
        doc.setEstado("Activo");
        doc.setEspecialidad(esp);
        return doc;
    }
}