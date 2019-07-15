package com.uball.uballapp.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import com.uball.uballapp.models.User;
import com.uball.uballapp.repos.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UserRepository userDao;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user){
//        Date DOB = user.getDOB();
//        user.setDOB(DOB);
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/";
    }

//    //Getting all Users!
//    @GetMapping("/leaguepage") // this will be the method that shows all members of the league page
//    public String all(Model model){
//        model.addAttribute("users", userDao.findAll());
//        return "leaguepage";
//    }

//    //Show one user, by ID #
//    @GetMapping("/user/{id}/userprofile")
//    public String show(@PathVariable long id, Model model){
//        model.addAttribute("post", userDao.findOne(id));
//        return "user/userprofile";
//    }
//
//    //Editing a User from user side
//    @GetMapping("/user/userprofile/{id}/edit")
//    public String edit ( @PathVariable long id, Model model){
//        model.addAttribute("user", userDao.findOne(id));
//        return "user/userprofile";
//    }

//    @PostMapping("/user/userprofile/{id}/edit")
//    public String update (
//            @PathVariable long id,
//            @ModelAttribute User user)
//    {
//        user.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        userDao.save(user);
//        return "redirect:/user/userprofile/{id}";
//    }

}
