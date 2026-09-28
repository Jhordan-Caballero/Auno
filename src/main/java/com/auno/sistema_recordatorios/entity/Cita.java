package com.auno.sistema_recordatorios.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity // <-- Si falta esto, sale el error "Not a managed type"
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    // Relacionamos la cita directamente con el bloque de horario elegido
    @OneToOne
    @JoinColumn(name = "horario_id", nullable = false)
    private Horario horario;

    private String motivo;

    // Ej: "CONFIRMADA", "CANCELADA", "ATENDIDA"
    private String estado;

}
