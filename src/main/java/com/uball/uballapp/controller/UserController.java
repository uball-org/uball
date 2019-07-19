package com.uball.uballapp.controller;


import com.uball.uballapp.repos.LeagueRepository;
import com.uball.uballapp.repos.MachineRepository;
import com.uball.uballapp.repos.ScoreRepository;
import org.codehaus.groovy.transform.SourceURIASTTransformation;

import org.springframework.security.core.context.SecurityContextHolder;
import com.uball.uballapp.models.User;
import com.uball.uballapp.repos.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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

    //Getting all Users!

    @GetMapping("/leagues") // this will be the method that shows all members of the league page
    public String all(Model model){
        model.addAttribute("users",userDao.findTop4ScoringUsers());
        model.addAttribute("scores",scoreDao.findTop4Scores());
        model.addAttribute("leagues", leagueDoa.findTop4ScoringLeagues());

        model.addAttribute("oneLeagueUsers",userDao.Top4ScoringUserByLeague(1));
//        model.addAttribute("oneLeagueScores",scoreDao.Top4ScoresByLeague(1));
        model.addAttribute("oneLeagueMachines",machineDao.Top4ScoringMachinesByLeague(1));

        model.addAttribute("twoLeagueUsers",userDao.Top4ScoringUserByLeague(2));
//        model.addAttribute("twoLeagueScores",scoreDao.Top4ScoresByLeague(2));
        model.addAttribute("twoLeagueMachines",machineDao.Top4ScoringMachinesByLeague(2));

        return "league/leaguedashboard";
    }

    //Show User Profile, by ID #
    @GetMapping("/userprofile/{id}")
    public String userProfileView(@PathVariable long id, Model model){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("users", userDao.findOne(id));
        model.addAttribute("machines", machineDao.findOne(id));
//        model.addAttribute("machines1", machineDao.findDistinctTopByScoresAnd_User_Id(id));
        model.addAttribute("scores", scoresDao.findAllByUser_Id(id));
//        model.addAttribute("scores1", scoresDao.findDistinctTopByMachineAndUser_Id(machineDao.findAll(), id));
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







    @GetMapping("/userprofile")
    public String userPro(Model model){
            model.addAttribute("msg", "what can you see");
            return "/userprofile";
        }

}
