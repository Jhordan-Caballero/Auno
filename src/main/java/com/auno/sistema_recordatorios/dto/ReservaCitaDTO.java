package com.auno.sistema_recordatorios.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ReservaCitaDTO {
    private Long pacienteId;
    private Long horarioId;
    private String motivo;
}
