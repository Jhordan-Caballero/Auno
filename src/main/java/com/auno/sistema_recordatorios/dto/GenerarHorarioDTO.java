package com.auno.sistema_recordatorios.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GenerarHorarioDTO {
    private Long doctorId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
