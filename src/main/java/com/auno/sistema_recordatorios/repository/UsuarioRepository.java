package com.auno.sistema_recordatorios.repository;

import com.auno.sistema_recordatorios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Spring crea automáticamente este método por el nombre:
    boolean existsByEmail(String email);
}
