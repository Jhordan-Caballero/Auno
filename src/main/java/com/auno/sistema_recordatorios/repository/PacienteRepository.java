package com.auno.sistema_recordatorios.repository;

import com.auno.sistema_recordatorios.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    }
