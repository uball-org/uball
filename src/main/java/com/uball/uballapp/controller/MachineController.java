package com.uball.uballapp.controller;

import com.uball.uballapp.models.Machine;
import com.uball.uballapp.repos.MachineRepository;
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
    @GetMapping("/machines")
    public String index(Model model) {
        model.addAttribute("machines", machineDao.findAll());
        return "machines/index";
    }
    /**
     * returns create machine form
     * */
    @GetMapping("/machines/create")
    public String create(Model model) {
        model.addAttribute("machine", new Machine());
        return "machine/create";
    }











}
