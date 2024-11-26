package com.juhi.final_project_esd.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Courses")
public class Course {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "course_id")
        private Long courseId;

        @Column(name = "courseCode", length = 50, nullable = false, unique = true)
        private String courseCode;

        @Column(name = "name", nullable = false, length = 255)
        private String name;

        @Column(name = "description", length = 255)
        private String desc;

        @Column(name = "year", length = 50, nullable = false)
        private Long year;

        @Column(name = "term", nullable = false, length = 255)
        private String term;

        @Column(name = "credits", nullable = false, length = 255)
        private Long credits;

        @Column(name = "capacity", length = 50, nullable = false)
        private Long capcity;

        public String getCourseCode() {
                return courseCode;
        }

        public String getName() {
                return name;
        }

            public Long getId() {
                        return courseId;
            }
}



