package com.auno.sistema_recordatorios.service;

import com.auno.sistema_recordatorios.dto.GenerarHorarioDTO;
import com.auno.sistema_recordatorios.entity.*;
import com.auno.sistema_recordatorios.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HorarioService {
    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private DoctorRepository doctorRepository; // Asegúrate de tener esta interfaz en tus repositories

    public List<Horario> generarHorariosAutomaticos(GenerarHorarioDTO dto) {
        // 1. Buscar al doctor en la base de datos
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado con el ID: " + dto.getDoctorId()));

        List<Horario> nuevosHorarios = new ArrayList<>();

        // 2. Bucle principal: Recorrer desde la fecha de inicio hasta la fecha de fin
        for (LocalDate fecha = dto.getFechaInicio(); !fecha.isAfter(dto.getFechaFin()); fecha = fecha.plusDays(1)) {

            // Opcional: Omitir sábados (6) y domingos (7)
            if (fecha.getDayOfWeek().getValue() >= 6) {
                continue;
            }

            // 3. Bucle interno: Crear turnos desde las 08:00 hasta las 16:00
            LocalTime horaActual = LocalTime.of(8, 0); // 8:00 AM
            LocalTime horaFinJornada = LocalTime.of(16, 0); // 4:00 PM

            while (horaActual.isBefore(horaFinJornada)) {
                Horario horario = new Horario();
                horario.setFecha(fecha);
                horario.setHoraInicio(horaActual);
                horario.setHoraFin(horaActual.plusMinutes(30)); // Citas de 30 minutos
                horario.setEstado("DISPONIBLE");
                horario.setDoctor(doctor);

                nuevosHorarios.add(horario);

                // Avanzar el reloj 30 minutos para el siguiente bloque
                horaActual = horaActual.plusMinutes(30);
            }
        }

        // 4. Guardar todos los bloques generados en un solo movimiento a la base de datos
        return horarioRepository.saveAll(nuevosHorarios);
    }
}
