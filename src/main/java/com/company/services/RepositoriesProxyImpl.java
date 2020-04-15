package com.company.services;

import com.company.models.Course;
import com.company.models.User;
import com.company.repositories.CoursesRepository;
import com.company.repositories.UsersCoursesRepository;
import com.company.repositories.UsersRepository;

import java.util.List;

public class RepositoriesProxyImpl implements RepositoriesProxy {
    private UsersRepository usersRepository;
    private UsersCoursesRepository usersCoursesRepository;

    public RepositoriesProxyImpl(UsersRepository usersRepository, CoursesRepository coursesRepository) {

    }

    @Override
    public List<Course> getCoursesOfUser(User user) {
        return usersCoursesRepository.getCoursesById(user.getUserId());
    }
}
