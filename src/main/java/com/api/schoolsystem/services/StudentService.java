package com.api.schoolsystem.services;
import com.api.schoolsystem.models.StudentModel;
import com.api.schoolsystem.repositories.StudentRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Page<StudentModel> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }


    public Optional<StudentModel> findById(Long id) {
        return studentRepository.findById(id);
    }

    public StudentModel save(StudentModel studentModel) {
        return studentRepository.save(studentModel);
    }

    public boolean existsByCpf(String cpf) {
        return studentRepository.existsByCpf(cpf);
    }

    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    public void delete(StudentModel studentModel) {
        studentRepository.delete(studentModel);
    }
}
