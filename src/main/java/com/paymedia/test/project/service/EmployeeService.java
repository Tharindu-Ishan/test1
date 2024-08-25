package com.paymedia.test.project.service;

import com.paymedia.test.project.entity.Department;
import com.paymedia.test.project.entity.Employee;
import com.paymedia.test.project.repository.DepartmentRepository;
import com.paymedia.test.project.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + id));
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = getEmployeeById(id);
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPhone(employeeDetails.getPhone());

        Department department = departmentRepository.findByName(employeeDetails.getDepartment().getName()).orElseThrow(() -> new EntityNotFoundException("Department not found"));
        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }

    public Page<Employee> getAllEmployees(String departmentName, Pageable pageable) {
        if (departmentName != null) {
            Department department = departmentRepository.findByName(departmentName).orElseThrow(() -> new EntityNotFoundException("Department not found"));
            return employeeRepository.findAllByDepartment(department, pageable);
        }
        return employeeRepository.findAll(pageable);
    }
}
