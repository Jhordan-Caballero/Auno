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

    // FUNCIÓN 1: Se ejecuta SOLAMENTE UNA VEZ justo cuando el servidor termina de arrancar
    @EventListener(ApplicationReadyEvent.class)
    @Transactional(readOnly = true)
    public void enviarRecordatoriosAlIniciar() {
        System.out.println("⏳ Ejecutando revisión de recordatorios al arrancar el sistema...");
        LocalDate manana = LocalDate.now().plusDays(1);

        List<Cita> citasManana = citaRepository.findByHorarioFechaAndEstado(manana, "PENDIENTE");

        for (Cita cita : citasManana) {
            String destinatario = cita.getPaciente().getUsuario().getEmail();
            String mensaje = String.format(
                    "Hola %s, te recordamos tu cita de mañana a las %s con el Dr. %s.",
                    cita.getPaciente().getUsuario().getNombres(),
                    cita.getHorario().getHoraInicio(),
                    cita.getDoctor().getApellidos()
            );
            emailService.enviarCorreo(destinatario, "Recordatorio de Cita - Auno", mensaje);
            System.out.println("✅ Correo de recordatorio enviado a: " + destinatario);
        }
    }

    // FUNCIÓN 2: Se ejecuta de fondo CADA 60 SEGUNDOS (60000 milisegundos)
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void limpiarCitasVencidas() {
        LocalDate hoy = LocalDate.now();
        LocalTime ahora = LocalTime.now();

        // Busca citas PENDIENTES cuya fecha es hoy o antes, y cuya hora ya pasó
        List<Cita> citasVencidas = citaRepository.findByEstadoAndHorarioFechaLessThanEqualAndHorarioHoraInicioLessThan(
                "PENDIENTE", hoy, ahora);

        if (!citasVencidas.isEmpty()) {
            System.out.println("🧹 Limpiando " + citasVencidas.size() + " citas vencidas...");

            for (Cita cita : citasVencidas) {
                // Cambiar estado de la cita
                cita.setEstado("NO ASISTIÓ");

                // Liberar el horario
                Horario horario = cita.getHorario();
                horario.setEstado("DISPONIBLE");

                horarioRepository.save(horario);
                citaRepository.save(cita);
                System.out.println("🚫 Cita ID " + cita.getId() + " marcada como NO ASISTIÓ y horario liberado.");
            }
        }
    }
}
