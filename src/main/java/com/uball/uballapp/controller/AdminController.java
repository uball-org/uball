package com.uball.uballapp.controller;


import com.uball.uballapp.models.Group;
import com.uball.uballapp.models.Machine;
import com.uball.uballapp.repos.GroupRepository;
import com.uball.uballapp.repos.MachineRepository;
import com.uball.uballapp.repos.UserRepository;
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
    private UserRepository userDao;
    private MachineRepository machineDao;
    private GroupRepository groupDao;

    public AdminController(AdminRepository adminDao,
                           MachineRepository machineDao,
                           GroupRepository groupDao,
                           UserRepository userDao) {

        this.adminDao = adminDao;
        this.machineDao = machineDao;
        this.groupDao = groupDao;
        this.userDao = userDao;
    }

    //Getting all Users!
    @GetMapping("/admindashboard")
    public String all(Model model){
//        model.addAttribute("user", adminDao.findOne(id));
        model.addAttribute("users", adminDao.findAll());
        model.addAttribute("machines", machineDao.findAll());
        return "admin/admindashboard";
    }

//    Value of user selected on view is : "uchecked"

    @RequestMapping(value = "/admindashboard/creategroup", method = RequestMethod.POST)
    public String newUsersForGroups(Model model, @RequestParam(name = "uchecked") List<User> newU) {
        System.out.println(newU.size());
        System.out.println(newU.get(0).getFirstName());
        for(User users : newU){
            model.addAttribute("usergroup", adminDao.findAll());
            users.getGroups().add(groupDao.findOne(1L));
            userDao.save(users);
            System.out.println(groupDao.findOne(1L).getUsers().size());
        }

        return "admin/admindashboard";
    }


    //    Value of user selected on view is : "mchecked"

    @RequestMapping(value = "/admindashboard/createmachines", method = RequestMethod.POST)
    public String newMachines(Model model, @RequestParam(name = "mchecked") List<Machine> newM) {
        System.out.println(newM.size()/2);
        System.out.println(newM.get(0).getName());
        model.addAttribute(newM);
        for(Machine machine : newM){
            System.out.println(newM.get(newM.indexOf(machine)).getName());
        }
        return "admin/admindashboard";
//            machineDao.save(machine);
    }

    // disable button
//
//    @PostMapping("/user/{id}/disable") //This will actually be to disable NOT DELETE
//    public String delete(@PathVariable long id) {
//        adminDao.delete(id);
//        return "redirect:/admindashboard";
//    }


}
