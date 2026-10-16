package com.auno.sistema_recordatorios.repository;


import com.auno.sistema_recordatorios.entity.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {
    // Evita duplicar el mismo bloque de horario para un doctor
    boolean existsByDoctorIdAndFechaAndHoraInicio(Long doctorId, LocalDate fecha, LocalTime horaInicio);

    // El paciente solo verá los horarios disponibles
    List<Horario> findByDoctorIdAndEstadoAndFechaGreaterThanEqualOrderByFechaAscHoraInicioAsc(
            Long doctorId, String estado, LocalDate fechaHoy);
}