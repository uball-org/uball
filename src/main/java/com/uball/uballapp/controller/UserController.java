package com.uball.uballapp.controller;


import com.uball.uballapp.models.League;
import org.springframework.security.core.context.SecurityContextHolder;
import com.uball.uballapp.models.User;
import com.uball.uballapp.repos.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class UserController {
    private UserRepository userDao;
    private PasswordEncoder passwordEncoder;
//    private MachineRepository machineDao;
//    private ScoresRepository scoresDao;


    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
//        this.machineDao = machineDao;
//        this.scoresDao = scoresDao;
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid User user, Errors validation, Model m){

        Date DOB = user.getDOB();
        user.setDOB(DOB);
        League league = user.getLeague();
        user.setLeague(league);
        char gender = user.getGender();
        user.setGender(gender);
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/login";
    }

    //Getting all Users!
    @GetMapping("/leagues") // this will be the method that shows all members of the league page
    public String all(Model model){
        model.addAttribute("users", userDao.findAll());
        return "league/leaguedashboard";
    }

    //Show one user, by ID #
    @GetMapping("/userprofile/{id}")
    public String show(@PathVariable long id, Model model){
        model.addAttribute("user", userDao.findOne(id));
        return "user/userprofile";
    }

    //Editing a User from user side
    @GetMapping("/edituser/{id}")
    public String edit ( @PathVariable long id, Model model){
        model.addAttribute("user", userDao.findOne(id));
        return "user/edituser";
    }

    @PostMapping("/edituser/{id}")
    public String update (
            @PathVariable long id,
            @ModelAttribute User user)
    {
        User original = userDao.findOne(id);
        user.setId(original.getId());
        user.setPassword(original.getPassword());
        user.setUsername(original.getUsername());
        userDao.save(user);
        return "redirect:/userprofile/{id}";
    }

}
