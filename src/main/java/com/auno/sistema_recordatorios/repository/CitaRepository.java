package com.auno.sistema_recordatorios.repository;

import com.auno.sistema_recordatorios.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
}
