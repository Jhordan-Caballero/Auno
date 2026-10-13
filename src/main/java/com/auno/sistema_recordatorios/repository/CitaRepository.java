package com.auno.sistema_recordatorios.repository;

import com.auno.sistema_recordatorios.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    // Busca las citas por el correo del paciente y las ordena por fecha
    List<Cita> findByPacienteUsuarioEmailOrderByHorarioFechaDesc(String email);

    // Busca una cita específica por su ID
    Optional<Cita> findById(Long id);
}
