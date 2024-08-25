package com.paymedia.test.project.repository;

import com.paymedia.test.project.entity.Department;
import com.paymedia.test.project.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAllByDepartment(Long departmentId, Pageable pageable);
}
