package com.company.controllers;

import com.company.models.Course;
import com.company.repositories.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CoursesCatalogueController {
    @Autowired
    private CoursesRepository coursesRepository;

    @GetMapping("/courses_catalogue")
    public ModelAndView getCatalogue() {
        ModelAndView modelAndView = new ModelAndView();
        List<Course> courses = coursesRepository.allCourses();
        modelAndView.addObject("courses", courses);
        return modelAndView;
    }
}
