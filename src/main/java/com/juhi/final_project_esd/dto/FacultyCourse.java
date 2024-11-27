package com.juhi.final_project_esd.dto;


public class FacultyCourse {

    private Long id;
    private String employeeEmail;
    private String courseId;

    public FacultyCourse() {}

    public FacultyCourse(Long id, String employeeEmail, String courseId) {
        this.id = id;
        this.employeeEmail = employeeEmail;
        this.courseId = courseId;
    }

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

