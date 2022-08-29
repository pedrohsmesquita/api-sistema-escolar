package com.api.schoolsystem.repositories;

import com.api.schoolsystem.models.ProfessorModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<ProfessorModel, Long> {
    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
