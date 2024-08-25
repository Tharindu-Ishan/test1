package com.paymedia.test.project.controller;

import com.paymedia.test.project.dto.EmployeeDTO;
import com.paymedia.test.project.entity.Employee;
import com.paymedia.test.project.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //Create a new employee
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Validated EmployeeDTO employeeDto, BindingResult result) {
        EmployeeDTO createdDepartmentDTO = employeeService.saveEmployee(employeeDto);
        return ResponseEntity.ok(createdDepartmentDTO);
    }

    //Retrieve an employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employeeDto = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeDto);
    }

    //Update an existing employee
    @PutMapping
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody @Validated EmployeeDTO employeeDto, BindingResult result) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(employeeDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    //Delete an employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(id + " deleted successfully");
    }

    //Retrieve all employees
    @GetMapping
    public ResponseEntity<Page<Employee>> getAllEmployees(@RequestParam(required = false) String departmentName,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "id,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort[0]).ascending());

        if (sort.length > 1 && sort[1].equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort[0]).descending());
        }

        Page<Employee> employees = employeeService.getAllEmployees(departmentName, pageable);
        return ResponseEntity.ok(employees);
    }
}
