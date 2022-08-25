package com.api.schoolsystem.services;
import com.api.schoolsystem.repositories.StudentRepository;

import org.springframework.stereotype.Service;

@Service
public class StudentService {
    final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
}
