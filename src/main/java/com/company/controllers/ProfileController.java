package com.company.controllers;

import com.company.models.Course;
import com.company.security.details.UserDetailsImpl;
import com.company.services.RepositoriesProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public ModelAndView getProfilePage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        Map<String, String> attributes = new HashMap<>();
        attributes.put("email", userDetails.getUsername());
        attributes.put("nickname", userDetails.getUser().getLogin());
        modelAndView.addAllObjects(attributes);
        return modelAndView;
    }
}
