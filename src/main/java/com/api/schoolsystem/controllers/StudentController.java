package com.api.schoolsystem.controllers;

import com.api.schoolsystem.dtos.StudentDto;
import com.api.schoolsystem.models.ProfessorModel;
import com.api.schoolsystem.models.StudentModel;
import com.api.schoolsystem.services.ProfessorService;
import com.api.schoolsystem.services.StudentService;
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
@RequestMapping(value = "/students")
public class StudentController {
    final StudentService studentService;
    final ProfessorService professorService;

    public StudentController(StudentService studentService, ProfessorService professorService) {
        this.studentService = studentService;
        this.professorService = professorService;
    }

    @GetMapping
    public ResponseEntity<Page<StudentModel>> getAllStudents(@PageableDefault(
            page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.findAll(pageable));
    }

    @GetMapping(value = "/student/{id}")
    public ResponseEntity<Object> getOneStudent(@PathVariable(value = "id") Long id) {
        Optional<StudentModel> optionalStudentModel = studentService.findById(id);
        if (!optionalStudentModel.isPresent()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalStudentModel.get());
    }

    @PostMapping(value = "/student")
    public ResponseEntity<Object> saveStudent(@RequestBody @Valid StudentDto studentDto) {
        if (studentService.existsByCpf(studentDto.getCpf())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conflict: CPF already registered.");
        }
        if (studentService.existsByEmail(studentDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conflict: E-mail already registered.");
        }
        Optional<ProfessorModel> optionalProfessorModel = professorService.findById(studentDto.getProfessorId());
        if (!optionalProfessorModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor not found.");
        }
        var studentModel = new StudentModel();
        BeanUtils.copyProperties(studentDto, studentModel);
        studentModel.setProfessor(optionalProfessorModel.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.save(studentModel));
    }

    @PutMapping(value = "/student/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable(value = "id") Long id,
                                                @RequestBody @Valid StudentDto studentDto) {
        Optional<StudentModel> optionalStudentModel = studentService.findById(id);
        if (!optionalStudentModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
        Optional<ProfessorModel> optionalProfessorModel = professorService.findById(studentDto.getProfessorId());
        if (!optionalProfessorModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor not found.");
        }
        var studentModel = new StudentModel();
        BeanUtils.copyProperties(studentDto, studentModel);
        studentModel.setId(optionalStudentModel.get().getId());
        studentModel.setProfessor(optionalProfessorModel.get());
        return ResponseEntity.status(HttpStatus.OK).body(studentService.save(studentModel));
    }

    @DeleteMapping(value = "/student/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable(value = "id") Long id) {
        Optional<StudentModel> optionalStudentModel = studentService.findById(id);
        if (!optionalStudentModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
        studentService.delete(optionalStudentModel.get());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student deleted successfully.");
    }
}
