package com.uball.uballapp.controller;

import com.uball.uballapp.models.League;
import com.uball.uballapp.models.User;
import com.uball.uballapp.repos.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LeagueController <leagueRepository>{
    private UserRepository userDao;
    private MachineRepository machineDao;
    private ScoreRepository scoreDao;
    private LeagueRepository leagueDao;


    public LeagueController(UserRepository userDao, MachineRepository machineDao, LeagueRepository leagueDao, ScoreRepository scoreDao) {
        this.userDao = userDao;
        this.machineDao = machineDao;
        this.scoreDao = scoreDao;
        this.leagueDao = leagueDao;
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

    @GetMapping("/leagues/SAPL")
    public String league1(Model model){

        User userSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = userSession.getId();
        String username = userSession.getUsername();

        model.addAttribute("userName", username);
        model.addAttribute("users",userDao.findTop4ScoringUsers());
        model.addAttribute("oneLeagueScores",scoreDao.Top4ScoresByLeague(1));
        model.addAttribute("twoLeagueScores",scoreDao.Top4ScoresByLeague(2));

        return "league/leaguesaplview";
    }

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

    @GetMapping("/leagues/create")
    public String create(Model model) {

        User userSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = userSession.getId();
        boolean isAdmin = userSession.isAdmin();

        if (isAdmin) {
            model.addAttribute("leaguesForm", new League());
            return "leagues/create";
        } else {
            return "redirect:/userprofile";
        }
    }

    @PostMapping("/leagues/create")
    public String insert(@ModelAttribute League league) {

        User userSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = userSession.getId();
        boolean isAdmin = userSession.isAdmin();

        if (isAdmin) {
            leagueDao.save(league);
            return "redirect:/createleague";
        } else {
            return "redirect:/userprofile";
        }
    }

    @GetMapping("/leagues/{id}/edit")
    public String edit(@PathVariable long id, Model model) {

        User userSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = userSession.getId();
        boolean isAdmin = userSession.isAdmin();

        if (isAdmin) {
            model.addAttribute("thisLeague", leagueDao.findOne(id));
            return "leagues/edit";
        } else {
            return "redirect:/userprofile";
        }
    }

    @PostMapping("/leagues/{id}/edit")
    public String update(@PathVariable long id, @ModelAttribute League league) {

        User userSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = userSession.getId();
        boolean isAdmin = userSession.isAdmin();

        if (isAdmin) {
            leagueDao.save(league);
            return "redirect:/creategroup";
        } else {
            return "redirect:/userprofile";
        }
    }

    @GetMapping("/leagues/{id}/delete")
    public String deleteLeague(@PathVariable long id, @ModelAttribute League league) {

        User userSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = userSession.getId();
        boolean isAdmin = userSession.isAdmin();

        if (isAdmin) {
            leagueDao.delete(id);
            return "redirect:/createleague";
        } else {
            return "redirect:/userprofile";
        }
    }


}
