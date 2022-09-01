package com.api.schoolsystem.repositories;
import com.api.schoolsystem.models.StudentModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, Long> {
    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
