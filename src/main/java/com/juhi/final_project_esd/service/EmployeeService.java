package com.juhi.final_project_esd.service;

import com.juhi.final_project_esd.dto.*;
//import com.juhi.final_project_esd.dto.FacultyRegisterDTO;
import com.juhi.final_project_esd.entity.Course;
import com.juhi.final_project_esd.entity.Employee;
import com.juhi.final_project_esd.entity.Faculty_course;
import com.juhi.final_project_esd.exception.CustomerNotFoundException;
import com.juhi.final_project_esd.helper.EncryptionService;
import com.juhi.final_project_esd.helper.JWTHelper;
import com.juhi.final_project_esd.mapper.EmployeeMapper;
//import com.juhi.final_project_esd.mapper.RegisterFacultyMapper;
import com.juhi.final_project_esd.repo.CourseRepo;
import com.juhi.final_project_esd.repo.FacultyCourseRepo;
import com.juhi.final_project_esd.repo.FacultyRepo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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
//    private final RegisterFacultyMapper registerFacultyMapper;


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

    public String assignCourseToFaculty(String token, CourseUpdateRequest request) {

        Long employeeId = jwtHelper.extractEmployeeId(token);
        System.out.print(employeeId);
        if (employeeId == null) {
            return "Invalid employee ID";
        }

        System.out.print(request.getCourseName());
        Course course = courseRepo.findByName(request.getCourseName());
        if (course == null) {
            return "Course not found";
        }

        Faculty_course facultyCourse = new Faculty_course();
        facultyCourse.setCourseId(course.getCourseCode());
        facultyCourse.setFacultyId(employeeId);

        facultyCourseRepo.save(facultyCourse);

        return "Course assigned to faculty successfully";
    }

    public FacultyDetailsResponse getEmployeeDetails(Long employeeId) {

        Employee employee = facultyRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));


        return employeeMapper.toFacultyDetailsResponse(employee);
    }

    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    public List<String> getRegisteredCourseNames(String token) {
        // Validate the token and extract employee ID
        token = jwtHelper.removeBearerFromToken(token);
        if (!jwtHelper.validateToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }

        Long facultyId = jwtHelper.extractEmployeeId(token);

        // Fetch the course IDs from faculty_courses
        List<Faculty_course> facultyCourses = facultyCourseRepo.findByFacultyId(facultyId);
        List<String> courseIds = facultyCourses.stream()
                .map(Faculty_course::getCourseId)
                .collect(Collectors.toList());

        // Fetch the course names from the courses table
        List<Course> courses = courseRepo.findByCourseCodeIn(courseIds);
        return courses.stream()
                .map(Course::getName)
                .collect(Collectors.toList());
    }



//    public List<Map<String, String>> getRegisteredCourseNames(String token) {
//        token = jwtHelper.removeBearerFromToken(token);
//        if (!jwtHelper.validateToken(token)) {
//            throw new IllegalArgumentException("Invalid token");
//        }
//
//        Long facultyId = jwtHelper.extractEmployeeId(token);
//
//        // Fetch faculty courses
//        List<Faculty_course> facultyCourses = facultyCourseRepo.findByFacultyId(facultyId);
//
//        // Fetch course details
//        List<String> courseIds = facultyCourses.stream()
//                .map(Faculty_course::getCourseId)
//                .collect(Collectors.toList());
//        List<Course> courses = courseRepo.findByCourseCodeIn(courseIds);
//
//        // Map courseCode and name
//        return courses.stream()
//                .map(course -> Map.of("courseCode", course.getCourseCode(), "name", course.getName()))
//                .collect(Collectors.toList());
//    }
//

}
