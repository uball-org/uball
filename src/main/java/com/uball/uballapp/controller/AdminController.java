package com.uball.uballapp.controller;


import com.uball.uballapp.models.Group;
import com.uball.uballapp.repos.GroupRepository;
import com.uball.uballapp.repos.MachineRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import com.uball.uballapp.models.User;
import org.springframework.web.bind.annotation.*;
import com.uball.uballapp.repos.AdminRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {
    private AdminRepository adminDao;
    private MachineRepository machineDao;
    private GroupRepository groupDao;

    public AdminController(AdminRepository adminDao, MachineRepository machineDao, GroupRepository groupDao) {
        this.adminDao = adminDao;
        this.machineDao = machineDao;
        this.groupDao = groupDao;
    }

    //Getting all Users!
    @GetMapping("/admindashboard")
    public String all(Model model){
//        model.addAttribute("user", adminDao.findOne(id));
        model.addAttribute("users", adminDao.findAll());
        model.addAttribute("machines", machineDao.findAll());
        return "admin/admindashboard";
    }

    //Getting new Group!
    @GetMapping("/admindashboard/creategroup")
    public String newUserGroupList(Model model){

        return "admin/admindashboard";
    }

    @PostMapping("admin/admindashboard")
    public String createPost(){

        return "redirect:/admindashboard";
    }


    //Getting new Group!
    @GetMapping("/admindashboard/createmachine")
    public String newMachineList(Model model){

        return "admin/admindashboard";
    }


    // disable button
//
//    @PostMapping("/user/{id}/disable") //This will actually be to disable NOT DELETE
//    public String delete(@PathVariable long id) {
//        adminDao.delete(id);
//        return "redirect:/admindashboard";
//    }


}
