package com.uball.uballapp.controller;

import com.uball.uballapp.models.Group;
import com.uball.uballapp.models.League;
import com.uball.uballapp.models.User;
import com.uball.uballapp.repos.GroupRepository;
import com.uball.uballapp.repos.LeagueRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LeagueController {
    private LeagueRepository leagueDao;


    public LeagueController(LeagueRepository leagueDao) {
        this.leagueDao = leagueDao;
    }


    /**
     * ounces URI accessed, displays machines page
     * */
    @GetMapping("/createleague")
    public String index(Model model) {

        User userSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = userSession.getId();
        boolean isAdmin = userSession.isAdmin();

        model.addAttribute("leaguesList", leagueDao.findAll());

        if (isAdmin) {
            return "leagues/index";
        } else {
            return "redirect:/userprofile";
        }

    }
    /**
     * returns create machine form
     * */
    @GetMapping("/leagues/create")
    public String create(Model model) {
        model.addAttribute("leaguesForm", new League());
        return "leagues/create";
    }
    /**
     * accessed through create machine form and inserts into DB before redirecting to machines index page
     * */
    @PostMapping("/leagues/create")
    public String insert(@ModelAttribute League league) {
        leagueDao.save(league);
        return "redirect:/createleague";
    }

    /**
     * when URI accessed from machines index page, displays edit machine page by id
     * */
    @GetMapping("/leagues/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("thisLeague", leagueDao.findOne(id));
        return "leagues/edit";
    }

    /**
     * accessed through edit machine form and inserts into DB before redirecting to machines index page
     * */
    @PostMapping("/leagues/{id}/edit")
    public String update(@PathVariable long id, @ModelAttribute League league) {
        leagueDao.save(league);
        return "redirect:/creategroup";
    }








}
