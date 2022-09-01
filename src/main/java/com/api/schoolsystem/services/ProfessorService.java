package com.api.schoolsystem.services;

import com.api.schoolsystem.models.ProfessorModel;
import com.api.schoolsystem.repositories.ProfessorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProfessorService {
    final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Transactional
    public ProfessorModel save(ProfessorModel professorModel) {
        return professorRepository.save(professorModel);
    }

    public Page<ProfessorModel> findAll(Pageable pageable) {
        return professorRepository.findAll(pageable);
    }

    public boolean existsByCpf(String cpf) {
        return professorRepository.existsByCpf(cpf);
    }

    public boolean existsByEmail(String email) {
        return professorRepository.existsByEmail(email);
    }

    public Optional<ProfessorModel> findById(Long id) {
        return professorRepository.findById(id);
    }

    @Transactional
    public void delete(ProfessorModel professorModel) {
        professorRepository.delete(professorModel);
    }
}
