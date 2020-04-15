package com.company.repositories;

import com.company.models.Course;

import java.util.List;

public interface UsersCoursesRepository {
    List<Course> getCoursesById(Long id);
}
