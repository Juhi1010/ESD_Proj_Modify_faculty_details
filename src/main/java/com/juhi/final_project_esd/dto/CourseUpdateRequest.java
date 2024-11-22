package com.juhi.final_project_esd.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public class CourseUpdateRequest {
        private String courseName;  // The name of the course the employee should be mapped to

        // Getters and setters
        public String getCourseName() {
                return courseName;
        }

        public void setCourseName(String courseName) {
                this.courseName = courseName;
        }
}

