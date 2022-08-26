package com.api.schoolsystem.services;

import com.api.schoolsystem.repositories.ProfessorRepository;

public class ProfessorService {
    final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }
}
