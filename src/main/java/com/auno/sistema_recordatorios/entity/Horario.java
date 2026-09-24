package com.auno.sistema_recordatorios.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "horarios")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha; // Ej: 2026-10-15

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio; // Ej: 09:00

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin; // Ej: 09:30

    // Ej: "DISPONIBLE", "RESERVADO", "CANCELADO"
    @Column(nullable = false)
    private String estado;

    // Relacionamos este bloque de horario con un Doctor específico
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
}
