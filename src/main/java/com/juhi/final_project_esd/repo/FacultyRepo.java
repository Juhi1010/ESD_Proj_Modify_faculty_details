package com.juhi.final_project_esd.repo;

import com.juhi.final_project_esd.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacultyRepo extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    Optional<Employee>findById(Long employeeId);
}
