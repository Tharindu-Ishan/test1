package com.paymedia.test.project.service;

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

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Department not found with id " + id));
    }

    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = getDepartmentById(id);
        department.setName(departmentDetails.getName());
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        Department department = getDepartmentById(id);
        departmentRepository.delete(department);
    }

    public Page<Department> getAllDepartments(String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return departmentRepository.findByNameContainingIgnoreCase(search, pageable);
        }
        return departmentRepository.findAll(pageable);
    }
}
