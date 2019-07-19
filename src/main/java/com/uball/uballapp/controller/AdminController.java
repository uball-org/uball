package com.uball.uballapp.controller;


import com.uball.uballapp.models.Group;
import com.uball.uballapp.models.Machine;
import com.uball.uballapp.models.Score;
import com.uball.uballapp.repos.*;
import org.springframework.security.core.context.SecurityContextHolder;
import com.uball.uballapp.models.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class AdminController {
    private AdminRepository adminDao;
    private UserRepository userDao;
    private ScoreRepository scoreDao;
    private MachineRepository machineDao;
    private GroupRepository groupDao;

    public AdminController(AdminRepository adminDao,
                           MachineRepository machineDao,
                           GroupRepository groupDao,
                           UserRepository userDao,
                           ScoreRepository scoreDao) {

        this.adminDao = adminDao;
        this.machineDao = machineDao;
        this.groupDao = groupDao;
        this.userDao = userDao;
        this.scoreDao = scoreDao;
    }
            //All Machines and Users
    @GetMapping("/admindashboard")
    public String all(Model model){
        model.addAttribute("users", adminDao.findAll());
        model.addAttribute("machines", machineDao.findAll());

        return "admin/admindashboard";
    }

            //Value of user selected on view is : "uchecked" / "mchecked"
    @RequestMapping(value = "/admindashboard/creategrouping", method = RequestMethod.POST)
    public String newUsersForGroups(Model model,
                                    @RequestParam(name = "uchecked") List<User> newU,
                                    @RequestParam(name = "mchecked") List<Machine> newM) {
        for(User users : newU){
            System.out.println("users = " +
                    newU.indexOf(users) + " " +
                    users.isAdmin() + " " +
                    users.getGroups() + " " +
                    users.getFirstName() + " " +
                    users.getLastName() + " " + users.getId());

            //Create logic for making group amount and number of groups

            for(Machine machine : newM){
                System.out.println(machine.getId() + " " +
                        machine.getName() + " " +
                        machine.getScores());

                Score blankscore = new Score();
                blankscore.setScore(0);
                blankscore.setMachine(machine);
                blankscore.setUser(users);
                scoreDao.save(blankscore);
//                model.addAttribute("matchmachines", blankscore);

            }
            users.getGroups().add(groupDao.findOne(2L));
            userDao.save(users);
        }

        System.out.println("size of new users list divide by two= " + newU.size()/2);
        System.out.println("size of users in group 1 = " + groupDao.findOne(1L).getUsers().size());
        System.out.println("size of new machines list divide by two= " + newM.size()/2);

        return "redirect:/admindashboard";
    }


    // disable button
//
//    @PostMapping("/user/{id}/disable") //This will actually be to disable NOT DELETE
//    public String delete(@PathVariable long id) {
//        adminDao.delete(id);
//        return "redirect:/admindashboard";
//    }


}
