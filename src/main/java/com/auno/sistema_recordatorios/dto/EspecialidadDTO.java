package com.auno.sistema_recordatorios.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EspecialidadDTO {

    private Long id;

    @NotBlank(message = "El nombre de la especialidad es obligatorio")
    private String nombre;

    private String descripcion;
}