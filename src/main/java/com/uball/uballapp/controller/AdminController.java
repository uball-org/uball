package com.uball.uballapp.controller;


import com.uball.uballapp.repos.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
//import com.uball.uballapp.models.User;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
    private AdminRepository adminDao;
    private PasswordEncoder passwordEncoder;

    public AdminController(AdminRepository adminDao, PasswordEncoder passwordEncoder) {
        this.adminDao = adminDao;
        this.passwordEncoder = passwordEncoder;
    }


}
