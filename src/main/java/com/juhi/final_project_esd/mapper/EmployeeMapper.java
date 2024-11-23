package com.juhi.final_project_esd.mapper;

import com.juhi.final_project_esd.dto.FacultyDetailsResponse;
import com.juhi.final_project_esd.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public FacultyDetailsResponse toFacultyDetailsResponse(Employee employee) {
        return new FacultyDetailsResponse(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getJobTitle(),
                employee.getPhotoPath(),
                employee.getDepartment()
        );
    }
}

