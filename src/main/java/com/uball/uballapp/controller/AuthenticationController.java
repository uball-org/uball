package com.uball.uballapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
    @GetMapping("/")
    public String loginPage() {
        return "index";
    }
}
