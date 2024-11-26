package com.juhi.final_project_esd.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Faculty_Courses")
public class Faculty_course {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "pk")
        private Long pk;

        @Column(name = "facultyId", length = 50)
        private Long facultyId;

        @Column(name = "course_id", length = 50)
        private String courseId;

        public void setCourseId(String courseId) {
                this.courseId = courseId;
        }

        public void setFacultyId(Long facultyId) {
                this.facultyId = facultyId;
        }

        public String getCourseId() {
                return this.courseId;
        }

        public Long getFacultyId() {
                return this.facultyId;
        }
}



