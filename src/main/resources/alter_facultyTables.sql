ALTER TABLE Employees
    ADD CONSTRAINT fk_employees_department
        FOREIGN KEY (department)
            REFERENCES departments(department_id)
            ON DELETE SET NULL; -- Optional: Adjust based on desired behavior

ALTER TABLE Faculty_courses
    ADD CONSTRAINT fk_faculty_courses_faculty
        FOREIGN KEY (faculty_id)
            REFERENCES employees(employee_id)
            ON DELETE CASCADE; -- Optional: Adjust based on desired behavior

ALTER TABLE faculty_courses
    ADD CONSTRAINT fk_faculty_courses_course
        FOREIGN KEY (course_id)
            REFERENCES courses(course_id)
            ON DELETE CASCADE; -- Optional: Adjust based on desired behavior



