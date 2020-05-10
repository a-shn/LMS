package com.company.controllers;

import com.company.models.Course;
import com.company.models.Lesson;
import com.company.repositories.CourseLessonsRepository;
import com.company.repositories.UsersCoursesRepository;
import com.company.security.details.UserDetailsImpl;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    @Autowired
    private UsersCoursesRepository usersCoursesRepository;

    @SneakyThrows
    @GetMapping("/course")
    public void redirect(HttpServletResponse response) {
        response.sendRedirect("/courses");
    }

    @RequestMapping(value = "/course", params = "courseId")
    public ModelAndView getCoursePage(@RequestParam long courseId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ModelAndView modelAndView = new ModelAndView();
        List<Lesson> lessons = courseLessonsRepository.usersPersonalisedLessons(userDetails.getUser().getUserId(), courseId);
        modelAndView.addObject("courseId", courseId);
        modelAndView.addObject("lessons", lessons);
        modelAndView.addObject("isUserInCourse", usersCoursesRepository
                .isUserInCourse(userDetails.getUser(), courseId));
        modelAndView.setViewName("course");
        return modelAndView;
    }

    @PostMapping("/joincourse")
    public String joinCourse(@RequestParam long courseId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        usersCoursesRepository.save(userDetails.getUser(), courseId);
        return "redirect: /course?courseId=" + courseId;
    }

    @PostMapping("/lessonwatched")
    public String lessonWatched(@RequestParam long courseId, @RequestParam int lessonNumber,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        courseLessonsRepository.lessonWatched(userDetails.getUser().getUserId(), courseId, lessonNumber);
        return "redirect: /course?courseId=" + courseId;
    }
}
