package com.company.repositories;

import com.company.models.Course;

import java.util.List;
import java.util.Optional;

public interface CoursesRepository {
    Course save(Course course);

    Optional<Course> findById(Long id);

    List<Course> allCourses();
}
