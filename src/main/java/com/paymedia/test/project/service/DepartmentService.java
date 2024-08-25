package com.paymedia.test.project.service;


import com.paymedia.test.project.dto.DepartmentDTO;
import com.paymedia.test.project.entity.Department;
import com.paymedia.test.project.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentDTO saveDepartment(DepartmentDTO departmentDto) {
        Department department = populateDepartment(departmentDto);
        Department dep = departmentRepository.save(department);
        return populateDepartmentDTO(dep);

    }

    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Department not found with id " + id));
        return populateDepartmentDTO(department);
    }

    public DepartmentDTO updateDepartment(DepartmentDTO departmentDto) {
        Department department = populateDepartment(departmentDto);
        Department res = departmentRepository.save(department);
        return populateDepartmentDTO(res);
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Department not found for delete with id " + id));
        departmentRepository.delete(department);
    }

    public Page<Department> getAllDepartments(String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return departmentRepository.findByNameContainingIgnoreCase(search, pageable);
        }
        return departmentRepository.findAll(pageable);
    }

    public Department populateDepartment(DepartmentDTO departmentDto){
        Department department = new Department();
        department.setName(departmentDto.getName());
        return department;
    }

    public DepartmentDTO populateDepartmentDTO(Department dep){
        DepartmentDTO departmentDto = new DepartmentDTO();
        departmentDto.setId(dep.getId());
        departmentDto.setName(dep.getName());
        return departmentDto;
    }
}

