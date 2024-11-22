CREATE DATABASE Faculty;

use Faculty;

-- CREATE TABLE Employees (
--    employee_id INT AUTO_INCREMENT PRIMARY KEY,
--    first_name VARCHAR(50),           -- Employee's first name
--    last_name VARCHAR(50),            -- Employee's last name
--    email VARCHAR(100) NOT NULL UNIQUE,  -- Unique and not-null email
--    title VARCHAR(100),               -- Job title
--    photograph_path VARCHAR(255),     -- Path to the employee's photograph
--    department INT(50)                   -- Foreign key referencing a department
-- );
--
-- CREATE TABLE Courses (
--      course_id INT AUTO_INCREMENT PRIMARY KEY,  -- Unique identifier for each course
--      course_code VARCHAR(20) NOT NULL UNIQUE,   -- Unique course code, not nullable
--      name VARCHAR(100) NOT NULL,                -- Name of the course, not nullable
--      description TEXT,                          -- Description of the course
--      year INT NOT NULL,                         -- Academic year
--      term VARCHAR(20),                          -- Term (e.g., Fall, Spring)
-- --      faculty VARCHAR(100),                      -- Faculty responsible for the course
--      credits INT,                               -- Number of credits for the course
--      capacity INT                               -- Maximum number of students
-- );
--
-- CREATE TABLE Employee_Salary (
--      PK int,
--      employee_id int not null,
--      payment_date date,
--      amount decimal(10,2),
--      description text
-- );


create table employees (
                           employee_id bigint not null auto_increment,
                           department bigint,
                           email varchar(255) not null,
                           first_name varchar(255) not null,
                           job_title varchar(255) not null,
                           last_name varchar(255) not null,
                           photograph_path varchar(255) not null,
                           primary key (employee_id)
);

create table faculty_courses (
                                 pk bigint not null auto_increment,
                                 course_id bigint,
                                 faculty_id bigint,
                                 primary key (pk)
);

create table departments (
                             department_id bigint not null auto_increment,
                             capacity bigint,
                             name bigint,
                             primary key (department_id)
);

create table courses (
                         course_id bigint not null auto_increment,
                         capacity bigint not null,
                         course_code varchar(50) not null,
                         credits bigint not null,
                         description varchar(255),
                         name varchar(255) not null,
                         term varchar(255) not null,
                         year bigint not null,
                         primary key (course_id)
);




