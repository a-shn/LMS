package com.company.controllers;

import com.company.models.Lesson;
import com.company.repositories.CourseLessonsRepository;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseLessonsRepository courseLessonsRepository;

    @SneakyThrows
    @GetMapping("/course")
    public void redirect(HttpServletResponse response) {
        response.sendRedirect("/courses");
    }

    @RequestMapping(value = "/course", params = "courseId")
    public ModelAndView getCoursePage(@RequestParam long courseId) {
        ModelAndView modelAndView = new ModelAndView();
        List<Lesson> lessons = courseLessonsRepository.getLessonsByCourseId(courseId);
        modelAndView.addObject("lessons", lessons);
        modelAndView.setViewName("course");
        return modelAndView;
    }
}
