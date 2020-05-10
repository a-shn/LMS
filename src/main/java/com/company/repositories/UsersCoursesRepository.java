package com.company.repositories;

import com.company.models.Course;
import com.company.models.User;

import java.util.List;

public interface UsersCoursesRepository {
    void save(User user, Course course);

    void save(User user, long courseId);

    List<Course> getCoursesById(Long id);

    Boolean isUserInCourse(User user, long courseId);
}
