package com.juhi.final_project_esd.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Employees")
public class Employee {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "employee_id")
        private Long employeeId;

        @Setter
        @Getter
        @Column(name = "first_name", nullable = false, length = 255)
        private String firstName;

        @Getter
        @Setter
        @Column(name = "last_name", nullable = false, length = 255)
        private String lastName;

        @Getter
        @Setter
        @Column(name = "email", unique = true, nullable = false)
        private String email;

        @Setter
        @Getter
        @Column(name="password", nullable = false)
        private String password;

        @Getter
        @Setter
        @Column(name = "job_title", nullable = false, length = 255)
        private String jobTitle;

        @Getter
        @Setter
        @Column(name = "photograph_path", nullable = false, length = 255)
        private String photoPath;

        @Getter
        @Setter
        @Column(name = "department", length = 50)
        private Long department;

        public Long getEmployeeId() {
                return employeeId;
        }


}



