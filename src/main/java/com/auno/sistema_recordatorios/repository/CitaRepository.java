package com.auno.sistema_recordatorios.repository;

import com.auno.sistema_recordatorios.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    // Busca las citas por el correo del paciente y las ordena por fecha
    List<Cita> findByPacienteUsuarioEmailOrderByHorarioFechaDesc(String email);

    List<Cita> findByEstadoAndHorarioFechaLessThanEqualAndHorarioHoraInicioLessThan(String estado, LocalDate fecha, LocalTime hora);
    // Busca una cita específica por su ID
    Optional<Cita> findById(Long id);

    // Busca las citas por una fecha específica y un estado específico
    List<Cita> findByHorarioFechaAndEstado(java.time.LocalDate fecha, String estado);
}
