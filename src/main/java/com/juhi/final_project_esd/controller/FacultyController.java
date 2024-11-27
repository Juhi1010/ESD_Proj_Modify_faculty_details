package com.juhi.final_project_esd.controller;

import com.juhi.final_project_esd.dto.*;
import com.juhi.final_project_esd.entity.Course;
import com.juhi.final_project_esd.entity.Employee;
import com.juhi.final_project_esd.helper.JWTHelper;
import com.juhi.final_project_esd.service.EmployeeService;
import org.springframework.core.io.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/me")
    public ResponseEntity<?> getLoggedInUserDetails(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = jwtHelper.removeBearerFromToken(authHeader);

            if (!jwtHelper.validateToken(token)) {
                return ResponseEntity.status(401).build();
            }

            Long employeeId = jwtHelper.extractEmployeeId(token);

            return ResponseEntity.ok(Map.of("employeeId", employeeId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
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

    @PutMapping("/assign-course")
    public ResponseEntity<String> assignCourse(@RequestHeader("Authorization") String authHeader,
                                               @RequestBody CourseUpdateRequest request) {

        String token = jwtHelper.removeBearerFromToken(authHeader);

        String result = facultyService.assignCourseToFaculty(token, request);

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
            return ResponseEntity.status(401).build();
        }

        Long employeeId = jwtHelper.extractEmployeeId(token);

        FacultyDetailsResponse employeeDetails = facultyService.getEmployeeDetails(employeeId);

        return ResponseEntity.ok(employeeDetails);
    }

    @GetMapping("/all-courses")
    public List<Course> getAllCourses() {
         return facultyService.getAllCourses();
    }


    @GetMapping("/registered-courses")
    public ResponseEntity<List<String>> getRegisteredCourseNames(
            @RequestHeader("Authorization") String authHeader) {
        List<String> courseNames = facultyService.getRegisteredCourseNames(authHeader);
        return ResponseEntity.ok(courseNames);
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<?> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            Employee updatedEmployee = facultyService.saveImage(id, file);
            return ResponseEntity.ok(updatedEmployee);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to upload image: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("images/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        try {
            Resource resource = facultyService.getImage(fileName);
            return ResponseEntity.ok()
                    .contentType(facultyService.getImageContentType(fileName))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/remove-course")
    public ResponseEntity<String> removeCourse(@RequestBody Map<String, String> request,
                                               @RequestHeader("Authorization") String token) {
        try {
            String courseName = request.get("courseName");
            facultyService.removeCourseByEmployeeAndCourseName(courseName, token);
            return ResponseEntity.ok("Course removed successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to remove course: " + e.getMessage());
        }
    }


}
