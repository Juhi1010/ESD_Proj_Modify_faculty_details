package com.juhi.final_project_esd.service;

import com.juhi.final_project_esd.dto.CourseDTO;
import com.juhi.final_project_esd.entity.Course;
import com.juhi.final_project_esd.entity.Faculty_course;
import com.juhi.final_project_esd.repo.CourseRepo;
import com.juhi.final_project_esd.repo.FacultyCourseRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepo courseRepo;
    private final FacultyCourseRepo facultyCourseRepo;

    public CourseService(CourseRepo courseRepo, FacultyCourseRepo facultyCourseRepo) {
        this.courseRepo = courseRepo;
        this.facultyCourseRepo = facultyCourseRepo;
    }

//    public List<Course> getAllCourses() {
//        return courseRepo.findAll(); // Fetch all courses from the database
//    }

    public List<CourseDTO> getAllCourses() {
        return courseRepo.findAll().stream()
                .map(course -> new CourseDTO(course.getCourseCode(), course.getName()))
                .collect(Collectors.toList());
    }

    public List<Course> getCoursesTaughtByFaculty(Long facultyId) {
        List<Faculty_course> facultyCourses = facultyCourseRepo.findByFacultyId(facultyId);
        List<Long> courseIds = facultyCourses.stream()
                .map(Faculty_course::getCourseId)
                .map(Long::valueOf)
                .collect(Collectors.toList());

        return courseRepo.findAllById(courseIds);
    }

}

