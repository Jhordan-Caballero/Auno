package com.auno.sistema_recordatorios.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.awt.Desktop;
import java.net.URI;

@Component
public class NavegadorStartup {

    // Inicializador de la página del proyecto
    private final String URL_INICIO = "http://localhost:8081/inicio";

    @EventListener(ApplicationReadyEvent.class)
    public void abrirNavegadorAlIniciar() {
        try {
            // Verifica si el sistema operativo permite abrir el navegador
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                System.out.println("Abriendo la página ...");
                Desktop.getDesktop().browse(new URI(URL_INICIO));
            } else {
                System.out.println("El sistema no soporta apertura automática. Visita manualmente: " + URL_INICIO);
            }
        } catch (Exception e) {
            System.out.println("️ No se pudo abrir el navegador: " + e.getMessage());
        }
    }
}
