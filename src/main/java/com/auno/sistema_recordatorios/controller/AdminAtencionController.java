package com.auno.sistema_recordatorios.controller;

import com.auno.sistema_recordatorios.dto.HistorialClinicoDTO;
import com.auno.sistema_recordatorios.entity.Cita;
import com.auno.sistema_recordatorios.repository.CitaRepository;
import com.auno.sistema_recordatorios.service.HistorialClinicoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/citas")
public class AdminAtencionController {

    private final CitaRepository citaRepository;
    private final HistorialClinicoService historialClinicoService;

    public AdminAtencionController(CitaRepository citaRepository, HistorialClinicoService historialClinicoService) {
        this.citaRepository = citaRepository;
        this.historialClinicoService = historialClinicoService;
    }

    // 1. Mostrar todas las citas al administrador
    @GetMapping
    public String listarCitas(Model model) {
        // Obtenemos todas las citas de la base de datos
        List<Cita> listaCitas = citaRepository.findAll();
        model.addAttribute("listaCitas", listaCitas);
        return "admin/citas"; // Vista con la tabla
    }

    // 2. Mostrar el formulario para atender a un paciente específico
    @GetMapping("/atender")
    public String mostrarFormularioAtencion(@RequestParam("citaId") Long citaId, Model model) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));

        // Solo se pueden atender citas que estén pendientes
        if (!"PENDIENTE".equals(cita.getEstado()) && !"PROGRAMADA".equals(cita.getEstado())) {
            return "redirect:/admin/citas?error=Esta cita no está en un estado válido para ser atendida.";
        }

        // Preparamos el DTO y le inyectamos el ID de la cita
        HistorialClinicoDTO dto = new HistorialClinicoDTO();
        dto.setCitaId(cita.getId());

        model.addAttribute("cita", cita);
        model.addAttribute("historialDTO", dto);

        return "admin/atencion"; // Vista del formulario clínico
    }

    // 3. Procesar los datos y guardar el historial
    @PostMapping("/atender")
    public String registrarAtencion(@Valid @ModelAttribute("historialDTO") HistorialClinicoDTO dto,
                                    BindingResult result,
                                    Model model) {
        if (result.hasErrors()) {
            Cita cita = citaRepository.findById(dto.getCitaId()).orElse(null);
            model.addAttribute("cita", cita);
            return "admin/atencion";
        }

        try {
            historialClinicoService.registrarAtencion(dto);
            return "redirect:/admin/citas?exito=Atención médica registrada y finalizada correctamente.";
        } catch (Exception e) {
            return "redirect:/admin/citas?error=" + e.getMessage();
        }
    }
}
