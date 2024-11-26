package com.juhi.final_project_esd.controller;

import com.juhi.final_project_esd.dto.*;
//import com.juhi.final_project_esd.dto.FacultyRegisterDTO;
import com.juhi.final_project_esd.entity.Course;
import com.juhi.final_project_esd.entity.Employee;
import com.juhi.final_project_esd.entity.Faculty_course;
import com.juhi.final_project_esd.helper.JWTHelper;
//import com.juhi.final_project_esd.service.CustomerService;
import com.juhi.final_project_esd.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/faculty")
@RequiredArgsConstructor
public class FacultyController {

    private final EmployeeService facultyService;

    private final JWTHelper jwtHelper;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(facultyService.login(request));
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(
            @RequestBody @Valid FacultyDetailsResponse updates,
            @RequestHeader("Authorization") String authHeader) {

        Employee updatedEmployee = facultyService.updateEmployeeDetails(
                authHeader,
                updates.getFirstName(),
                updates.getLastName(),
                updates.getJobTitle(),
                updates.getPhotographPath(),
                updates.getDepartment()
        );

        return ResponseEntity.ok(updatedEmployee);
    }

//    @Autowired
    @PutMapping("/assign-course")
    public ResponseEntity<String> assignCourse(@RequestHeader("Authorization") String authHeader,
                                               @RequestBody CourseUpdateRequest request) {
        // Remove Bearer prefix from token
        String token = jwtHelper.removeBearerFromToken(authHeader);

        // Call the service to assign the course
        String result = facultyService.assignCourseToFaculty(token, request);

//        System.out.println(result);

        // Return response
        if (result.equals("Course assigned to faculty successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @GetMapping("/details")
    public ResponseEntity<FacultyDetailsResponse> getEmployeeDetails(@RequestHeader("Authorization") String authHeader) {

        String token = jwtHelper.removeBearerFromToken(authHeader);

        if (!jwtHelper.validateToken(token)) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        Long employeeId = jwtHelper.extractEmployeeId(token);

        FacultyDetailsResponse employeeDetails = facultyService.getEmployeeDetails(employeeId);

        return ResponseEntity.ok(employeeDetails);
    }

    @GetMapping("/all-courses")
    public List<Course> getAllCourses() {
//        return courseRepo.findAll();
         return facultyService.getAllCourses();
    }


    @GetMapping("/registered-courses")
    public ResponseEntity<List<String>> getRegisteredCourseNames(
            @RequestHeader("Authorization") String authHeader) {
        List<String> courseNames = facultyService.getRegisteredCourseNames(authHeader);
        return ResponseEntity.ok(courseNames);
    }



//    @DeleteMapping("/delete-course")
//    public ResponseEntity<String> deleteCourseForEmployee(
//            @RequestHeader("Authorization") String authHeader,
//            @RequestBody Map<String, String> requestBody) {
//        String token = jwtHelper.removeBearerFromToken(authHeader);
//        String courseName = requestBody.get("courseName");
//
//        if (courseName == null || courseName.isEmpty()) {
//            return ResponseEntity.badRequest().body("Course name is required");
//        }
//
//        boolean isDeleted = facultyService.deleteCourseForEmployee(token, courseName);
//
//        if (isDeleted) {
//            return ResponseEntity.ok("Course deleted successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found for the employee");
//        }
//    }

//    @GetMapping("/photograph")
//    public ResponseEntity<FacultyDetailsResponse> getEmployeeDetails(@RequestHeader("Authorization") String authHeader) {
//
//        String token = jwtHelper.removeBearerFromToken(authHeader);
//
//        if (!jwtHelper.validateToken(token)) {
//            return ResponseEntity.status(401).build(); // Unauthorized
//        }
//
//        Long employeeId = jwtHelper.extractEmployeeId(token);
//
//        FacultyDetailsResponse employeeDetails = facultyService.getEmployeeDetails(employeeId);
//
//        return ResponseEntity.ok(employeeDetails);
//    }


}
