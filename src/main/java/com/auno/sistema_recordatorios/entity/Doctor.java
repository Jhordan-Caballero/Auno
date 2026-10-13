package com.auno.sistema_recordatorios.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    // Al quitarle la relación con Usuario, el Doctor necesita sus propios datos
    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(name = "numero_colegiatura", unique = true)
    private String numeroColegiatura;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "correo_contacto")
    private String correoContacto;

    @Column(name = "telefono_contacto")
    private String telefonoContacto;

    // Ej: "ACTIVO", "INACTIVO" (Para saber si puede recibir citas)
    @Column(nullable = false)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidad_id", nullable = false)
    private Especialidad especialidad;

    // ELIMINADA la relación @OneToOne con Usuario.
}
