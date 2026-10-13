package com.auno.sistema_recordatorios.config;

import com.auno.sistema_recordatorios.entity.*;
import com.auno.sistema_recordatorios.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class DataSeedConfig {
    @Bean
    public CommandLineRunner initData(UsuarioRepository usuarioRepository,
                                      RolRepository rolRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Verificamos si el rol ADMIN existe, si no, lo creamos
            Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN")
                    .orElseGet(() -> rolRepository.save(new Rol("ROLE_ADMIN")));

            // 2. Verificamos si ya existe el usuario administrador
            if (!usuarioRepository.existsByEmail("admin@sistema.com")) {
                Usuario admin = new Usuario();
                admin.setNombres("Administrador");
                admin.setApellidos("Principal");
                admin.setEmail("admin@sistema.com");
                // Encriptamos la contraseña "admin123"
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setTelefono("000000000");
                admin.setEstado("ACTIVO");

                // Le asignamos el rol de administrador
                admin.agregarRol(rolAdmin);

                // Lo guardamos en la base de datos
                usuarioRepository.save(admin);

                System.out.println("✅ Administrador por defecto creado exitosamente:");
                System.out.println("   Correo: admin@sistema.com");
                System.out.println("   Clave: admin123");
            }
        };
    }
}
