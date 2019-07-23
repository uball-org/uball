package com.uball.uballapp.controller;

import com.uball.uballapp.models.Group;
import com.uball.uballapp.models.User;
import com.uball.uballapp.repos.GroupRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GroupController {
    private GroupRepository groupDao;


    public GroupController(GroupRepository groupDao) {
        this.groupDao = groupDao;
    }


    /**
     * ounces URI accessed, displays machines page
     * */
    @GetMapping("/creategroup")
    public String index(Model model) {

        User userSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = userSession.getId();
        boolean isAdmin = userSession.isAdmin();

        model.addAttribute("groups", groupDao.findAll());

        if (isAdmin) {
            return "groups/index";
        } else {
            return "redirect:/userprofile";
        }



    }
    /**
     * returns create machine form
     * */
    @GetMapping("/groups/create")
    public String create(Model model) {
        model.addAttribute("groupsForm", new Group());
        return "groups/create";
    }
    /**
     * accessed through create machine form and inserts into DB before redirecting to machines index page
     * */
    @PostMapping("/groups/create")
    public String insert(@ModelAttribute Group group) {
        groupDao.save(group);
        return "redirect:/creategroup";
    }

    /**
     * when URI accessed from machines index page, displays edit machine page by id
     * */
    @GetMapping("/groups/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("thisGroup", groupDao.findOne(id));
        return "groups/edit";
    }

    /**
     * accessed through edit machine form and inserts into DB before redirecting to machines index page
     * */
    @PostMapping("/groups/{id}/edit")
    public String update(@PathVariable long id, @ModelAttribute Group group) {
        groupDao.save(group);
        return "redirect:/creategroup";
    }

    @GetMapping("/groups/{id}/delete")
    public String deleteLeague(@PathVariable long id) {
        groupDao.delete(id);
        return "redirect:/creategroup";
    }













}
