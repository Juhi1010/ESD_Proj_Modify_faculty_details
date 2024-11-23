package com.juhi.final_project_esd.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Faculty_Courses")
public class Faculty_course {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "pk")
        private Long pk;

        @Column(name = "faculty_id", length = 50)
        private Long faculty_id;

        @Column(name = "course_id", length = 50)
        private String course_id;

        public void setCourseId(String courseId) {
                this.course_id = courseId;
        }

        public void setFacultyId(Long faculty_id) {
                this.faculty_id = faculty_id;
        }

        public String getCourseId() {
                return course_id;
        }

}



