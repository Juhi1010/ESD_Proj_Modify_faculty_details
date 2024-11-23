package com.juhi.final_project_esd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FacultyDetailsResponse {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String jobTitle;
    private String photographPath;
    private Long department;
}


