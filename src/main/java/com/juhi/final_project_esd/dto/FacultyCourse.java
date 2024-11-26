package com.juhi.final_project_esd.dto;

import com.juhi.final_project_esd.entity.Course;

public class FacultyCourse {

    private Long id;
    private String employeeEmail;
    private String courseId;

    // Default constructor
    public FacultyCourse() {}

    // Constructor
    public FacultyCourse(Long id, String employeeEmail, String courseId) {
        this.id = id;
        this.employeeEmail = employeeEmail;
        this.courseId = courseId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "FacultyCourseDTO{" +
                "id=" + id +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", courseId=" + courseId +
                '}';
    }


    public String getCourse() {
        return courseId;
    }


}

