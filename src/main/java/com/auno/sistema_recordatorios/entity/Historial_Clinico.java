package com.auno.sistema_recordatorios.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "historiales_clinicos")
public class Historial_Clinico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Past(message = "Solo pueden haber fecha pasadas a la cita ")
    private LocalDate fecha;

    @Column(name = "motivo_consulta", columnDefinition = "TEXT")
    @NonNull
    private String motivoConsulta;

    @Column(columnDefinition = "TEXT")
    @NonNull
    private String diagnostico;

    @Column(columnDefinition = "TEXT")
    private String recomendaciones;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    // Relación 1 a 1 con Cita
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id", nullable = false, unique = true)
    private Cita cita;

}