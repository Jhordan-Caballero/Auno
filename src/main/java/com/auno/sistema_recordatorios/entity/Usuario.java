package com.auno.sistema_recordatorios.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String nombres;

    @Column(nullable = false)
    @NonNull
    private String apellidos;

    @Column(unique = true, nullable = false)
    @Email(message = "Debe de poner un correo electrónico válido")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Pattern(regexp = "^[0-9]{9}$", message = "Tiene que escribir un número válido")
    private String telefono;

    // Ej: "ACTIVO", "INACTIVO"
    private String estado;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>();

    public void agregarRol(Rol rol) {
        this.roles.add(rol);
    }
}
