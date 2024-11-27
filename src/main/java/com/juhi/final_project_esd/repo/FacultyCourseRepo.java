package com.juhi.final_project_esd.repo;

import com.juhi.final_project_esd.entity.Faculty_course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FacultyCourseRepo extends JpaRepository<Faculty_course, Long> {
    List<Faculty_course> findByFacultyId(Long facultyId);
    Faculty_course findByFacultyIdAndCourseId(Long facultyId, String courseId);
    @Transactional
    @Modifying
    @Query("DELETE FROM Faculty_course fc WHERE fc.facultyId = :employeeId AND fc.courseId = :courseCode")
    void deleteByEmployeeIdAndCourseCode(Long employeeId, String courseCode);
}
