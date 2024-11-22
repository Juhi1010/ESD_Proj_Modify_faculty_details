package com.juhi.final_project_esd.repo;

import com.juhi.final_project_esd.entity.Course;
import com.juhi.final_project_esd.entity.Faculty_course;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FacultyCourseRepo extends JpaRepository<Faculty_course, Long> {
    // Custom query methods (if needed)
}
