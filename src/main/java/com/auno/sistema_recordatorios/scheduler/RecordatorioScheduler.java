package com.auno.sistema_recordatorios.scheduler;

import com.auno.sistema_recordatorios.entity.Cita;
import com.auno.sistema_recordatorios.entity.Horario;
import com.auno.sistema_recordatorios.repository.CitaRepository;
import com.auno.sistema_recordatorios.repository.HorarioRepository;
import com.auno.sistema_recordatorios.service.EmailService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class RecordatorioScheduler {

    private final CitaRepository citaRepository;
    private final HorarioRepository horarioRepository;
    private final EmailService emailService;

    public RecordatorioScheduler(CitaRepository citaRepository, HorarioRepository horarioRepository, EmailService emailService) {
        this.citaRepository = citaRepository;
        this.horarioRepository = horarioRepository;
        this.emailService = emailService;
    }

    // Se ejecuta justo cuando el servidor termina de arrancar solo una vez
    @EventListener(ApplicationReadyEvent.class)
    @Transactional(readOnly = true)
    public void enviarRecordatoriosAlIniciar() {
        System.out.println("revisión de recordatorios al arrancar el sistema");
        LocalDate manana = LocalDate.now().plusDays(1);

        List<Cita> citasManana = citaRepository.findByHorarioFechaAndEstado(manana, "pendiente");

        for (Cita cita : citasManana) {
            String destinatario = cita.getPaciente().getUsuario().getEmail();
            String mensaje = String.format(
                    "Hola %s, te recordamos tu cita de mañana a las %s con el Dr. %s.",
                    cita.getPaciente().getUsuario().getNombres(),
                    cita.getHorario().getHoraInicio(),
                    cita.getDoctor().getApellidos()
            );
            emailService.enviarCorreo(destinatario, "Recordatorio de Cita - Auno", mensaje);
            System.out.println("Correo de recordatorio enviado a: " + destinatario);
        }
    }

    // Se ejecuta de fondo cada 10 minutos
    @Scheduled(fixedRate = 300000)
    @Transactional
    public void limpiarCitasVencidas() {
        LocalDate hoy = LocalDate.now();
        LocalTime ahora = LocalTime.now();

        // Busca citas pendientes cuya fecha es hoy o antes
        List<Cita> citasVencidas = citaRepository.findByEstadoAndHorarioFechaLessThanEqualAndHorarioHoraInicioLessThan(
                "pendiente", hoy, ahora);

        if (!citasVencidas.isEmpty()) {
            System.out.println("Limpiando " + citasVencidas.size() + " citas vencidas...");

            for (Cita cita : citasVencidas) {
                // Cambiar estado de la cita
                cita.setEstado("No asistio");

                // Liberar el horario
                Horario horario = cita.getHorario();
                horario.setEstado("disponible");

                horarioRepository.save(horario);
                citaRepository.save(cita);
                System.out.println("Cita ID " + cita.getId() + " marcada como no asistio y horario liberado.");
            }
        }
    }
}
