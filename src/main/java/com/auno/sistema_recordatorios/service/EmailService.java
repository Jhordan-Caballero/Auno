package com.auno.sistema_recordatorios.service;

import com.auno.sistema_recordatorios.entity.Cita;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // 1. Método genérico (usado por el Scheduler para recordatorios)
    public void enviarCorreo(String destinatario, String asunto, String mensaje) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("panconquesodeatun@gmail.com"); // Cambia esto por tu correo real
        email.setTo(destinatario);
        email.setSubject(asunto);
        email.setText(mensaje);

        mailSender.send(email);
    }
    // 2. Mensaje de Bienvenida al registrarse
    public void enviarCorreoBienvenida(String destinatario, String nombrePaciente) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("panconquesodeatun@gmail.com"); // Debe ser el mismo del application.properties
        email.setTo(destinatario);
        email.setSubject("¡Bienvenido a la Clínica Auno! 🏥");
        email.setText("Hola " + nombrePaciente + ",\n\n" +
                "¡Gracias por registrarte en el sistema de citas de la Clínica Auno!\n" +
                "Ya puedes iniciar sesión en nuestra plataforma para buscar a nuestros especialistas y reservar tus citas médicas de forma rápida y segura.\n\n" +
                "Saludos cordiales,\nEl equipo de Auno.");

        mailSender.send(email);
    }

    // 3. Mensaje de Confirmación al agendar un horario
    public void enviarCorreoConfirmacionCita(String destinatario, String nombrePaciente, Cita cita) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("panconquesodeatun@gmail.com");
        email.setTo(destinatario);
        email.setSubject("Confirmación de Cita Médica - Auno ✅");

        // Armamos el cuerpo del mensaje con los datos dinámicos de la Cita
        String cuerpo = "Hola " + nombrePaciente + ",\n\n" +
                "Tu cita ha sido confirmada exitosamente. Aquí tienes los detalles de tu reserva:\n\n" +
                "👨‍⚕️ Médico: Dr. " + cita.getDoctor().getNombres() + " " + cita.getDoctor().getApellidos() + "\n" +
                "🩺 Especialidad: " + cita.getDoctor().getEspecialidad().getNombre() + "\n" +
                "📅 Fecha: " + cita.getHorario().getFecha() + "\n" +
                "⏰ Hora: " + cita.getHorario().getHoraInicio() + " a " + cita.getHorario().getHoraFin() + "\n\n" +
                "Por favor, acércate a recepción 10 minutos antes de tu turno.\n\n" +
                "¡Te esperamos!\nClínica Auno.";

        email.setText(cuerpo);
        mailSender.send(email);
    }
}
