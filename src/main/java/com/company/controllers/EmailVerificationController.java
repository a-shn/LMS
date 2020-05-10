package com.company.controllers;

import com.company.services.SignUpPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.RequestDispatcher;

@Controller
public class EmailVerificationController {
    @Autowired
    private SignUpPool signUpPool;

    @GetMapping("/verification")
    public String verificate(@RequestParam String hash) {
        String link = "http://localhost:8080/verification?hash=" + hash;
        boolean isAllGood = signUpPool.verify(link);
        if (isAllGood) {
            return "email_verified";
        }
        return "invalid_link";
    }
}
