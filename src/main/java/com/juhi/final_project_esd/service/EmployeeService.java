package com.juhi.final_project_esd.service;

import com.juhi.final_project_esd.dto.*;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
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
    private RestTemplate restTemplate;

    @Value("${employee.images.base-path}")
    private String imageBasePath;


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

        token = jwtHelper.removeBearerFromToken(token);
        if (!jwtHelper.validateToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }

        Long facultyId = jwtHelper.extractEmployeeId(token);

        List<Faculty_course> facultyCourses = facultyCourseRepo.findByFacultyId(facultyId);
        List<String> courseIds = facultyCourses.stream()
                .map(Faculty_course::getCourseId)
                .collect(Collectors.toList());

        List<Course> courses = courseRepo.findByCourseCodeIn(courseIds);
        return courses.stream()
                .map(Course::getName)
                .collect(Collectors.toList());
    }


    public Employee saveImage(Long employeeId, MultipartFile file) throws IOException {

        Optional<Employee> optionalEmployee = facultyRepo.findById(employeeId);
        if (optionalEmployee.isEmpty()) {
            throw new IllegalArgumentException("Employee not found with ID: " + employeeId);
        }

        Employee employee = optionalEmployee.get();

        Path directoryPath = Paths.get(imageBasePath);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        String fileName = employeeId + "_" + file.getOriginalFilename();
        Path filePath = directoryPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        employee.setPhotoPath(fileName);
        return facultyRepo.save(employee);
    }

    public Resource getImage(String fileName) throws Exception {

        Path imagePath = Paths.get(imageBasePath).resolve(fileName);
        Resource resource = new UrlResource(imagePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Image not found or not readable");
        }

        return resource;
    }

    public MediaType getImageContentType(String fileName) {
        String fileExtension = getFileExtension(fileName);

        switch (fileExtension) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? "" : fileName.substring(lastDotIndex + 1).toLowerCase();
    }


    public void removeCourseByEmployeeAndCourseName(String courseName, String token) throws Exception {

        token = jwtHelper.removeBearerFromToken(token);
        if (!jwtHelper.validateToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }

        Long facultyId = jwtHelper.extractEmployeeId(token);

        Course course = courseRepo.findByName(courseName);

        facultyCourseRepo.deleteByEmployeeIdAndCourseCode(facultyId, course.getCourseCode());
    }


}
