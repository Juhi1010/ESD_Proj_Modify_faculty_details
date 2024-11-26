package com.juhi.final_project_esd.dto;

public class CourseDTO {
        private String courseCode;
        private String name;

        public CourseDTO(String courseCode, String name) {
                this.courseCode = courseCode;
                this.name = name;
        }

        // Getters and Setters
        public String getCourseCode() {
                return courseCode;
        }

        public void setCourseCode(String courseCode) {
                this.courseCode = courseCode;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }
}


