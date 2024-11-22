package com.juhi.final_project_esd.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Departments")
public class Department {

//        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        @Column(name = "department_id", nullable = false, unique = true)
        private Long departmentId;

        @Column(name = "name", length = 50)
        private String name;

        @Column(name = "capacity", length = 50)
        private Long capacity;

}



