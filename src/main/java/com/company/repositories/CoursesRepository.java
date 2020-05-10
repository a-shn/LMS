package com.company.repositories;

import com.company.models.Course;

import java.util.List;
import java.util.Optional;

public interface CoursesRepository {
    void updateStatus(long courseId, String status);

    Course save(Course course);

    Optional<Course> findById(Long id);

    List<Course> availableCourses();

    List<Course> allCourses();
}
