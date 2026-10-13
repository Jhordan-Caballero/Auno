package com.auno.sistema_recordatorios.service;

import com.auno.sistema_recordatorios.dto.EspecialidadDTO;
import com.auno.sistema_recordatorios.entity.Especialidad;
import com.auno.sistema_recordatorios.repository.EspecialidadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    public EspecialidadService(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    @Transactional
    public void registrarEspecialidad(EspecialidadDTO dto) {
        if (especialidadRepository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("La especialidad ya se encuentra registrada.");
        }

        Especialidad especialidad = new Especialidad();
        especialidad.setNombre(dto.getNombre());
        especialidad.setDescripcion(dto.getDescripcion());

        especialidadRepository.save(especialidad);
    }

    @Transactional(readOnly = true)
    public List<Especialidad> listarTodas() {
        return especialidadRepository.findAll();
    }
}
