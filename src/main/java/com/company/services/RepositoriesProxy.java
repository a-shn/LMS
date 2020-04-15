package com.company.services;

import com.company.models.Course;
import com.company.models.User;

import java.util.List;

public interface RepositoriesProxy {
    List<Course> getCoursesOfUser(User user);
}
