-- INSERT INTO employees
-- (department, email, password, first_name, job_title, last_name, photograph_path)
-- VALUES
--     (101, 'harsha@gmail.com', '$2a$10$r5b8zkqmMqgmqgVVPlYNlel7x3SerLZncR1TRwyM/uNuBoyRaDT/i', 'Harsha', 'Software Engineer', 'Vrant', '/images/employees/harsha.jpg')



-- INSERT INTO courses (capacity, course_code, credits, description, name, term, year)
-- VALUES
--     (50, 'CS101', 3, 'Introduction to Programming using Python', 'Programming Basics', 'Fall', 2024),
--     (40, 'CS102', 4, 'Data Structures and Algorithms using Java', 'Data Structures', 'Spring', 2024),
--     (35, 'CS103', 3, 'Database Systems and SQL', 'Database Management Systems', 'Fall', 2023),
--     (30, 'CS104', 3, 'Web Development with JavaScript and React', 'Web Development', 'Spring', 2024)


INSERT INTO Faculty_courses (faculty_id, course_id)
VALUES
     (2, 'CS101');


-- INSERT INTO Departments (department_id, capacity, name)
-- VALUES
--     (101, 100, 'Computer Science'),
--     (102, 50, 'Mathematics'),
--     (105, 75, 'Physics'),
--     (107, 60, 'Chemistry'),
--     (108, 40, 'Biology');
