package com.juhi.final_project_esd.controller;

import com.juhi.final_project_esd.dto.CourseDTO;
import com.juhi.final_project_esd.entity.Course;
import com.juhi.final_project_esd.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
//
//    @GetMapping
//    public ResponseEntity<List<Course>> getAllCourses() {
//        List<Course> courses = courseService.getAllCourses();
//        return ResponseEntity.ok(courses);
//    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

}
