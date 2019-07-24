package com.uball.uballapp.controller;


import com.uball.uballapp.models.League;
import com.uball.uballapp.repos.LeagueRepository;
import com.uball.uballapp.repos.MachineRepository;
import com.uball.uballapp.repos.ScoreRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import com.uball.uballapp.models.User;
import com.uball.uballapp.repos.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

@Controller
public class UserController<leagueRepository> {
    private UserRepository userDao;
    private PasswordEncoder passwordEncoder;
    private MachineRepository machineDao;
    private ScoreRepository scoreDao;
    private LeagueRepository leagueDoa;


    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder, MachineRepository machineDao, LeagueRepository leagueDoa, ScoreRepository scoreDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.machineDao = machineDao;
        this.scoreDao = scoreDao;
        this.leagueDoa = leagueDoa;

    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user", new User());
        return "user/register";
    }
    @GetMapping("/user/userprofile")
    public String userprofilepage(Model model){
        model.addAttribute("user", new User());
        return "user/userprofile";
    }

    @PostMapping("/register")
    public String saveUser(@Valid User user, Errors validation, Model m){

        if (validation.hasErrors()) {
            m.addAttribute("errors", validation);
            m.addAttribute("user", user);
            return "user/register";
        }

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

    @GetMapping("/leagues")
    public String all(Model model){

        User userSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = userSession.getId();
        String username = userSession.getUsername();

        model.addAttribute("userName", username);
        model.addAttribute("users",userDao.findTop4ScoringUsers());
        model.addAttribute("oneLeagueScores",scoreDao.Top4ScoresByLeague(1));
        model.addAttribute("twoLeagueScores",scoreDao.Top4ScoresByLeague(2));

        return "league/leaguedashboard";
    }

    @GetMapping("/userprofile")
    public String userProfileView(Model model, HttpServletRequest request){

        User userSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = userSession.getId();
        User userDB = userDao.findOne(userId);

        HttpSession session = request.getSession();
        session.setAttribute("isAdmin", userDB.isAdmin());

        model.addAttribute("user", userDB);
        model.addAttribute("scores", scoreDao.findAllByUser_Id(userDB.getId()));
        model.addAttribute("scoremach", scoreDao.findTopScoresOnMachines(userDB.getId()));
        model.addAttribute("machines", machineDao.findTopScoresOnMachines(userDB.getId()));

        return "user/userprofile";
    }

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
        return "redirect:/userprofile";
    }

}
