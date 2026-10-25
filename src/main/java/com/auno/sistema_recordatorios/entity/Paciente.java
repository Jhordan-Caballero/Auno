package com.auno.sistema_recordatorios.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_nacimiento")
    @Past(message = "debe de poner una fecha válida")
    private LocalDate fechaNacimiento;

    @Column(name = "direccion")
    @NonNull
    private String direccion;

    // Relación 1 a 1 con Usuario.
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;

    public Paciente(String nombre, Usuario usuario) {

        this.usuario = usuario;

    }
}
