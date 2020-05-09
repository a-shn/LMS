package com.company.controllers;

import com.company.models.Course;
import com.company.repositories.UsersCoursesRepository;
import com.company.security.details.UserDetailsImpl;
import com.company.services.FileUploader;
import com.company.services.RepositoriesProxy;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CoursesController {
    @Autowired
    private UsersCoursesRepository usersCoursesRepository;
    @Autowired
    private FileUploader fileUploader;

    @GetMapping("/courses")
    public ModelAndView getCoursesPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, List<Course>> map = new HashMap<>();
        List<Course> courses = usersCoursesRepository.getCoursesById(userDetails.getUser().getUserId());
        map.put("courses", courses);
        modelAndView.setViewName("courses");
        modelAndView.addAllObjects(map);
        return modelAndView;
    }

    @PostMapping("/uploadCourse")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if (extension.equals("torrent")) {
            fileUploader.uploadCourse(multipartFile);

            return "redirect:/courses";
        }
        return "redirect:/courses?error";
    }
}
