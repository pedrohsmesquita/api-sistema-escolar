package com.api.schoolsystem.controllers;

import com.api.schoolsystem.dtos.ProfessorDto;
import com.api.schoolsystem.models.ProfessorModel;
import com.api.schoolsystem.services.ProfessorService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(value = "/professors")
public class ProfessorController {
    final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public ResponseEntity<Page<ProfessorModel>> getAllProfessors(@PageableDefault(
            page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(professorService.findAll(pageable));
    }

    @GetMapping(value = "/professor/{id}")
    public ResponseEntity<Object> getOneProfessor(@PathVariable(value = "id") Long id) {
        Optional<ProfessorModel> optionalProfessorModel = professorService.findById(id);
        if (!optionalProfessorModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalProfessorModel.get());
    }

    @PostMapping(value = "/professor")
    public ResponseEntity<Object> saveProfessor(@RequestBody @Valid ProfessorDto professorDto) {
        if (professorService.existsByCpf(professorDto.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: CPF already registered.");
        }
        if (professorService.existsByEmail(professorDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: E-mail already registered.");
        }
        var professorModel =  new ProfessorModel();
        BeanUtils.copyProperties(professorDto, professorModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(professorService.save(professorModel));
    }

    @PutMapping(value = "/professor/{id}")
    public ResponseEntity<Object> updateProfessor(@PathVariable(value = "id") Long id,
                                                  @RequestBody @Valid ProfessorDto professorDto) {
        Optional<ProfessorModel> optionalProfessorModel = professorService.findById(id);
        if (!optionalProfessorModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor not found.");
        }
        var professorModel = new ProfessorModel();
        BeanUtils.copyProperties(professorDto, professorModel);
        professorModel.setId(optionalProfessorModel.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(professorService.save(professorModel));
    }

    @DeleteMapping(value = "/professor/{id}")
    public ResponseEntity<Object> deleteProfessor(@PathVariable(value = "id") Long id) {
        Optional<ProfessorModel> optionalProfessorModel = professorService.findById(id);
        if (!optionalProfessorModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor not found.");
        }
        professorService.delete(optionalProfessorModel.get());
        return ResponseEntity.status(HttpStatus.OK).body("Professor deleted successfully.");
    }
}
