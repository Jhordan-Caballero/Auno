package com.auno.sistema_recordatorios.config;

import com.auno.sistema_recordatorios.entity.*;
import com.auno.sistema_recordatorios.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeedConfig {
    @Bean
    public CommandLineRunner initData(RolRepository rolRepository, EspecialidadRepository especialidadRepository) {
        return args -> {
            // 1. Inicializar Roles
            // Para asegurarnos de no duplicarlos cada vez que reinicias el servidor
            if (rolRepository.count() == 0) {
                rolRepository.save(new Rol("ROLE_PACIENTE"));
                rolRepository.save(new Rol("ROLE_ADMIN"));
                rolRepository.save(new Rol("ROLE_DOCTOR"));
                System.out.println("✅ Roles base creados exitosamente en la base de datos.");
            }

            // 2. Inicializar Especialidades (Para que el paciente tenga de dónde elegir)
            if (especialidadRepository.count() == 0) {
                Especialidad medicinaGeneral = new Especialidad();
                medicinaGeneral.setNombre("Medicina General");
                medicinaGeneral.setDescripcion("Atención primaria y prevención.");

                Especialidad cardiologia = new Especialidad();
                cardiologia.setNombre("Cardiología");
                cardiologia.setDescripcion("Especialista en el corazón y sistema circulatorio.");

                Especialidad odontologia = new Especialidad();
                odontologia.setNombre("Odontología");
                odontologia.setDescripcion("Salud bucal y cuidado dental.");

                especialidadRepository.saveAll(List.of(medicinaGeneral, cardiologia, odontologia));
                System.out.println("✅ Especialidades base creadas exitosamente.");
            }
        };
    }
}
