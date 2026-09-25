package com.auno.sistema_recordatorios.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistroDoctorDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Long especialidadId;
}
