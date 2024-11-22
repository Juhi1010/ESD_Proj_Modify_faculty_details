package com.juhi.final_project_esd.controller;

import com.juhi.final_project_esd.dto.CourseUpdateRequest;
import com.juhi.final_project_esd.dto.LoginRequest;
import com.juhi.final_project_esd.entity.Employee;
import com.juhi.final_project_esd.helper.JWTHelper;
//import com.juhi.final_project_esd.service.CustomerService;
import com.juhi.final_project_esd.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @PutMapping("/update")
//    public ResponseEntity<Employee> updateFacultyDetails(
////            @PathVariable Long employeeId,
//            @RequestBody Map<String, String> payload,
//            @RequestHeader("Authorization") String authHeader) {
//            String firstName = payload.get("firstName");
//            String lastName = payload.get("lastName");
//            String password = payload.get("password");
//            String photographPath = payload.get("photographPath");
//
////            String token = jwtHelper.removeBearerFromToken(authHeader);
////
////            if (!jwtHelper.validateToken(token)) {
////                throw new IllegalArgumentException("Invalid token");
////            }
////
//            Employee updatedEmployee = facultyService.updateFacultyDetails(authHeader, firstName, lastName, password, photographPath);
//            return ResponseEntity.ok(updatedEmployee);
//    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(
//            @PathVariable Long employeeId,
            @RequestBody Map<String, Object> updates,
            @RequestHeader("Authorization") String authHeader) {

//        String email = (String) updates.get("email");
        String firstName = (String) updates.get("firstName");
        String lastName = (String) updates.get("lastName");
        String jobTitle = (String) updates.get("jobTitle");
        String password = (String) updates.get("password");
        String photographPath = (String) updates.get("photographPath");
        Long department = updates.get("department") != null ? Long.valueOf((Integer) updates.get("department")) : null;

        Employee updatedEmployee = facultyService.updateEmployeeDetails(authHeader, firstName, lastName, jobTitle, password, photographPath, department);

        return ResponseEntity.ok(updatedEmployee);
    }


//    private FacultyCourseService facultyCourseService;
//@Autowired
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


}
