package com.company.controllers;

import com.company.dto.LessonDto;
import com.company.repositories.CourseLessonsRepository;
import com.company.security.details.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController {
    @Autowired
    private CourseLessonsRepository courseLessonsRepository;

    @PostMapping("/lessonwatched")
    public void lessonWatched(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody LessonDto lessonDto) {
        courseLessonsRepository.lessonWatched(userDetails.getUser().getUserId(), lessonDto.getCourseId(), lessonDto.getLesson());
    }
}
