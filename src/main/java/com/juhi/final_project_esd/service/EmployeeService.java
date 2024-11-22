package com.juhi.final_project_esd.service;

import com.juhi.final_project_esd.dto.CourseUpdateRequest;
import com.juhi.final_project_esd.dto.LoginRequest;
import com.juhi.final_project_esd.entity.Course;
import com.juhi.final_project_esd.entity.Employee;
import com.juhi.final_project_esd.entity.Faculty_course;
import com.juhi.final_project_esd.exception.CustomerNotFoundException;
import com.juhi.final_project_esd.helper.EncryptionService;
import com.juhi.final_project_esd.helper.JWTHelper;
import com.juhi.final_project_esd.repo.CourseRepo;
import com.juhi.final_project_esd.repo.FacultyCourseRepo;
import com.juhi.final_project_esd.repo.FacultyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final FacultyRepo facultyRepo;
    private final CourseRepo courseRepo;
    private final FacultyCourseRepo facultyCourseRepo;
//    private final CustomerMapper customerMapper;
    private final EncryptionService encryptionService;
    private final JWTHelper jwtHelper;

//    public String createCustomer(CustomerRequest request) {
//        Customer customer = customerMapper.toCustomer(request);
//        customer.setPassword(encryptionService.encode(customer.getPassword()));
//        customerRepo.save(customer);
//        return "Customer Created Successfully";
//    }

    public Employee getEmployee(String email) {
        return facultyRepo.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Cannot update Customer:: No customer found with the provided ID:: %s", email)
                ));
    }

//    public CustomerResponse retrieveCustomer(String email) {
//        Employee employee = getEmployee(email);
//        return customerMapper.toCustomerResponse(customer);
//    }

    public String login(LoginRequest request) {
        // Retrieve the employee record using the provided email
        Employee employee = getEmployee(request.email());

        // Validate the password
        if (!encryptionService.validates(request.password(), employee.getPassword())) {
            return "Wrong Password or Email";
        }

        // Generate a token with email and employee_id
        return jwtHelper.generateToken(request.email(), employee.getEmployeeId());
    }

//    public String login(LoginRequest request) {
//        Employee employee = getEmployee(request.email());
//        if(!encryptionService.validates(request.password(), employee.getPassword())) {
//            return "Wrong Password or Email";
//        }
//
//        return jwtHelper.generateToken(request.email());
//    }

//    public Employee updateFacultyDetails(String token, String firstName, String lastName, String password, String photographPath) {
//
//        token = jwtHelper.removeBearerFromToken(token);
//
//        if (!jwtHelper.validateToken(token)) {
//            throw new IllegalArgumentException("Invalid token");
//        }
//
//        String username = jwtHelper.extractUsername(token);
//
//        Employee employee = facultyRepo.findByEmail(username)
//                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
//
////        Employee employee = facultyRepo.findById(email)
////                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        // Only update fields if they are provided
//        if (firstName != null) {
//            employee.setFirstName(firstName);
//        }
//        if (lastName != null) {
//            employee.setLastName(lastName);
//        }
//        if (password != null) {
//            employee.setPassword(encryptionService.encode(password)); // Encrypt the password
//        }
//        if (photographPath != null) {
//            employee.setPhotoPath(photographPath);
//        }
//
//        return facultyRepo.save(employee);
//    }

    public Employee updateEmployeeDetails(String token, String firstName, String lastName,
                                          String jobTitle, String password, String photographPath, Long department) {

        token = jwtHelper.removeBearerFromToken(token);

        if (!jwtHelper.validateToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }

        String username = jwtHelper.extractUsername(token);
        Long emp_id = jwtHelper.extractEmployeeId(token);

//        Employee employee = facultyRepo.findByEmail(username)
//                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        // Fetch the employee by ID
        Employee employee = facultyRepo.findById(emp_id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Update fields only if they are provided (not null)
//        if (email != null) employee.setEmail(email);
        if (firstName != null) employee.setFirstName(firstName);
        if (lastName != null) employee.setLastName(lastName);
        if (jobTitle != null) employee.setJobTitle(jobTitle);
        if (password != null) employee.setPassword(encryptionService.encode(password));
        if (photographPath != null) employee.setPhotoPath(photographPath);
        if (department != null) employee.setDepartment(department);

        // Save and return the updated employee
        return facultyRepo.save(employee);
    }

    public String assignCourseToFaculty(String token, CourseUpdateRequest request) {
        // Step 1: Extract employee_id from JWT token
        Long employeeId = jwtHelper.extractEmployeeId(token);
        if (employeeId == null) {
            return "Invalid employee ID";
        }

        // Step 2: Retrieve course_id by course name
        System.out.print(request.getCourseName());
        Course course = courseRepo.findByName(request.getCourseName());
        if (course == null) {
            return "Course not found";
        }

        // Step 3: Create a new faculty_course mapping entry
        Faculty_course facultyCourse = new Faculty_course();
        facultyCourse.setCourseId(course.getCourseCode());
        facultyCourse.setFacultyId(employeeId);

        // Save the mapping to the faculty_courses table
        facultyCourseRepo.save(facultyCourse);

        return "Course assigned to faculty successfully";
    }


}
