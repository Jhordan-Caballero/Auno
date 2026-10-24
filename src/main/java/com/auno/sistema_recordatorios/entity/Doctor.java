package com.auno.sistema_recordatorios.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.jspecify.annotations.NonNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctores")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String nombres;

    @Column(nullable = false)
    @NonNull
    private String apellidos;

    @Column(name = "numero_colegiatura", unique = true)
    @Length(min=5, max=6)
    private String numeroColegiatura;

    @Column(columnDefinition = "TEXT")
    @NonNull
    private String descripcion;

    @Column(name = "correo_contacto")
    @Email
    private String correoContacto;

    @Column(name = "telefono_contacto")
    @PositiveOrZero
    @Length(min=9, max=9)
    private String telefonoContacto;

    @Column(nullable = false)
    @NonNull
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidad_id", nullable = false)
    private Especialidad especialidad;

}
