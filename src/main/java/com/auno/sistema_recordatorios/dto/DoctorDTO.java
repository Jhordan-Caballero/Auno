package com.auno.sistema_recordatorios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorDTO {

    private Long id;

    @NotBlank(message = "Los nombres son obligatorios")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @NotBlank(message = "El número de colegiatura es obligatorio")
    private String numeroColegiatura;

    private String descripcion;

    @NotBlank(message = "El correo de contacto es obligatorio")
    @Email(message = "Debe ser un correo válido")
    private String correoContacto;

    private String telefonoContacto;

    @NotNull(message = "Debe seleccionar una especialidad")
    private Long especialidadId; // Recibiremos el ID desde el formulario HTML
}
