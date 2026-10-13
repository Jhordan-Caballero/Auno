package com.auno.sistema_recordatorios.repository;

import com.auno.sistema_recordatorios.entity.Historial_Clinico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface HistorialClinicoRepository extends JpaRepository<Historial_Clinico, Long> {
    Optional<Historial_Clinico> findByCitaId(Long citaId);
    List<Historial_Clinico> findByPacienteIdOrderByFechaDesc(Long pacienteId);
}