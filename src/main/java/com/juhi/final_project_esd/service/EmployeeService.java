package com.juhi.final_project_esd.service;

import com.juhi.final_project_esd.dto.CourseUpdateRequest;
import com.juhi.final_project_esd.dto.FacultyDetailsResponse;
import com.juhi.final_project_esd.dto.LoginRequest;
import com.juhi.final_project_esd.entity.Course;
import com.juhi.final_project_esd.entity.Employee;
import com.juhi.final_project_esd.entity.Faculty_course;
import com.juhi.final_project_esd.exception.CustomerNotFoundException;
import com.juhi.final_project_esd.helper.EncryptionService;
import com.juhi.final_project_esd.helper.JWTHelper;
import com.juhi.final_project_esd.mapper.EmployeeMapper;
import com.juhi.final_project_esd.repo.CourseRepo;
import com.juhi.final_project_esd.repo.FacultyCourseRepo;
import com.juhi.final_project_esd.repo.FacultyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final FacultyRepo facultyRepo;
    private final CourseRepo courseRepo;
    private final FacultyCourseRepo facultyCourseRepo;
    private final EncryptionService encryptionService;
    private final JWTHelper jwtHelper;
    private final EmployeeMapper employeeMapper;


    public Employee getEmployee(String email) {
        return facultyRepo.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Cannot update Customer:: No customer found with the provided ID:: %s", email)
                ));
    }


    public String login(LoginRequest request) {

        Employee employee = getEmployee(request.email());

        if (!encryptionService.validates(request.password(), employee.getPassword())) {
            return "Wrong Password or Email";
        }

        return jwtHelper.generateToken(request.email(), employee.getEmployeeId());
    }


    public Employee updateEmployeeDetails(String token, String firstName, String lastName,
                                          String jobTitle, String photographPath, Long department) {

        token = jwtHelper.removeBearerFromToken(token);

        if (!jwtHelper.validateToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }

        String username = jwtHelper.extractUsername(token);
        Long emp_id = jwtHelper.extractEmployeeId(token);

        Employee employee = facultyRepo.findById(emp_id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (firstName != null) employee.setFirstName(firstName);
        if (lastName != null) employee.setLastName(lastName);
        if (jobTitle != null) employee.setJobTitle(jobTitle);
        if (photographPath != null) employee.setPhotoPath(photographPath);
        if (department != null) employee.setDepartment(department);

        return facultyRepo.save(employee);
    }

//    public String assignCourseToFaculty(String token, CourseUpdateRequest request) {
//        Long employeeId = jwtHelper.extractEmployeeId(token);
//        if (employeeId == null) {
//            return "Invalid employee ID";
//        }
//
//        System.out.print(request.getCourseName());
//        Course course = courseRepo.findByName(request.getCourseName());
//        if (course == null) {
//            return "Course not found";
//        }
//
//        Faculty_course facultyCourse = new Faculty_course();
//        facultyCourse.setCourseId(course.getCourseCode());
//        facultyCourse.setFacultyId(employeeId);
//
//        facultyCourseRepo.save(facultyCourse);
//
//        return "Course assigned to faculty successfully";
//    }

    public FacultyDetailsResponse getEmployeeDetails(Long employeeId) {

        Employee employee = facultyRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));


        return employeeMapper.toFacultyDetailsResponse(employee);
    }


}
