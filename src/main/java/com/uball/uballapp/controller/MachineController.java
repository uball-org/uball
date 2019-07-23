package com.uball.uballapp.controller;

import com.uball.uballapp.models.Machine;
import com.uball.uballapp.models.User;
import com.uball.uballapp.repos.MachineRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MachineController {
    private MachineRepository machineDao;


    public MachineController(MachineRepository machineDao) {
        this.machineDao = machineDao;
    }


    /**
     * ounces URI accessed, displays machines page
     * */
    @GetMapping("/createmachine")
    public String index(Model model) {

        User userSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = userSession.getId();
        boolean isAdmin = userSession.isAdmin();

        model.addAttribute("machines", machineDao.findAll());

        if (isAdmin) {
            return "machines/index";
        } else {
            return "redirect:/userprofile";
        }

    }
    /**
     * returns create machine form
     * */
    @GetMapping("/machines/create")
    public String create(Model model) {
        model.addAttribute("machinesForm", new Machine());
        return "machines/create";
    }
    /**
     * accessed through create machine form and inserts into DB before redirecting to machines index page
     * */
    @PostMapping("/machines/create")
    public String insert(@ModelAttribute Machine machine) {
        machineDao.save(machine);
        return "redirect:/createmachine";
    }

    /**
     * when URI accessed from machines index page, displays edit machine page by id
     * */
    @GetMapping("/machines/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("thisMachine", machineDao.findOne(id));
        return "machines/edit";
    }

    /**
     * accessed through edit machine form and inserts into DB before redirecting to machines index page
     * */
    @PostMapping("/machines/{id}/edit")
    public String update(@PathVariable long id, @ModelAttribute Machine machine) {
        machineDao.save(machine);
        return "redirect:/createmachine";
    }


    @PostMapping("/machines/{id}/delete")
    public String delete(@PathVariable long id) {
        machineDao.delete(id);
        return "redirect:/createmachine";
    }
















}
