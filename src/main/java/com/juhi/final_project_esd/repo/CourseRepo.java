package com.juhi.final_project_esd.repo;

import com.juhi.final_project_esd.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course, Long> {
    Course findByName(String name);
    List<Course> findByCourseCodeIn(List<String> courseCodes);
    Course findByCourseCode(String courseCode);
}
