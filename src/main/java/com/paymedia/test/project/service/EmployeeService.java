package com.paymedia.test.project.service;

import com.paymedia.test.project.dto.EmployeeDTO;
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

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDto) {
        Employee employee = populateEmployee(employeeDto);
        Employee emp = employeeRepository.save(employee);
        return populateEmployeeDTO(emp);
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + id));
        return populateEmployeeDTO(employee);
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employeeDto) {
        Employee employee = populateEmployee(employeeDto);
        Employee res = employeeRepository.save(employee);
        return populateEmployeeDTO(res);
    }

    public void deleteEmployee(Long id) {
        EmployeeDTO employeeDto = getEmployeeById(id);
        Employee employee = populateEmployee(employeeDto);
        employeeRepository.delete(employee);
    }

    public Page<Employee> getAllEmployees(String departmentName, Pageable pageable) {
        if (departmentName != null) {
            Department department = departmentRepository.findByName(departmentName).orElseThrow(() -> new EntityNotFoundException("Department not found"));
            return employeeRepository.findAllByDepartment(department.getId(), pageable);
        }
        return employeeRepository.findAll(pageable);
    }

    public Employee populateEmployee(EmployeeDTO employeeDto){
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setPhone(employeeDto.getPhone());
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartment(employeeDto.getDepartmentId());
        return employee;
    }

    public EmployeeDTO populateEmployeeDTO(Employee emp){
        EmployeeDTO empDTO = new EmployeeDTO();
        empDTO.setId(emp.getId());
        empDTO.setFirstName(emp.getFirstName());
        empDTO.setLastName(emp.getLastName());
        empDTO.setEmail(emp.getEmail());
        empDTO.setPhone(emp.getPhone());
        empDTO.setDepartmentId(emp.getDepartment());
        return empDTO;
    }


}
