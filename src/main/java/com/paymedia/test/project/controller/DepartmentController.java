package com.paymedia.test.project.controller;


import com.paymedia.test.project.dto.DepartmentDTO;
import com.paymedia.test.project.entity.Department;
import com.paymedia.test.project.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping()
    public ResponseEntity<?> createDepartment(@RequestBody @Validated DepartmentDTO departmentDto, BindingResult result) {
        if (result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            result.getAllErrors().forEach(error -> {
                String fieldName = ((org.springframework.validation.FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        DepartmentDTO createdDepartmentDTO = departmentService.saveDepartment(departmentDto);
        return ResponseEntity.ok(createdDepartmentDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        DepartmentDTO department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    @PutMapping
    public ResponseEntity<?> updateDepartment(@RequestBody @Validated DepartmentDTO departmentDto, BindingResult result) {
        if (result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            result.getAllErrors().forEach(error -> {
                String fieldName = ((org.springframework.validation.FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        DepartmentDTO createdDepartmentDTO = departmentService.updateDepartment(departmentDto);
        return ResponseEntity.ok(createdDepartmentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok(id + " Deleted successfully");
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(defaultValue = "id,asc") String[] sort,
                                                              @RequestParam(required = false) String search) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort[0]).ascending());

        if (sort.length > 1 && sort[1].equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort[0]).descending());
        }

        Page<Department> departments = departmentService.getAllDepartments(search, pageable);
        return ResponseEntity.ok(departments.getContent());
    }

}
