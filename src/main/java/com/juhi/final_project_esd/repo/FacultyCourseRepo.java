package com.juhi.final_project_esd.repo;

import com.juhi.final_project_esd.dto.CourseDTO;
import com.juhi.final_project_esd.dto.FacultyCourse;
import com.juhi.final_project_esd.entity.Course;
import com.juhi.final_project_esd.entity.Faculty_course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface FacultyCourseRepo extends JpaRepository<Faculty_course, Long> {
    List<Faculty_course> findByFacultyId(Long facultyId);
    Faculty_course findByFacultyIdAndCourseId(Long facultyId, String courseId);
}
