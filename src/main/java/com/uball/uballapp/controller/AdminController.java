package com.uball.uballapp.controller;


import com.uball.uballapp.models.Machine;
import com.uball.uballapp.models.Score;
import com.uball.uballapp.repos.*;
import com.uball.uballapp.models.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.time.LocalDate;
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
        model.addAttribute("scores", scoreDao.findDistinctByAddedscoredateAndScore(LocalDate.now(), 0));
        model.addAttribute("machiness", machineDao.findAllByMachine_Id(0));
        model.addAttribute("userselect", userDao.findAllByUserId(0));
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
                        machine.getName() );

                Score blankscore = new Score(); // New score object to use to add new scores tied to the user_id and machine_id just added
                blankscore.setScore(0);
                blankscore.setAddedscoredate(LocalDate.now());
                blankscore.setMachine(machine);
                blankscore.setUser(users);
                scoreDao.save(blankscore);
            }

            users.getGroups().add(groupDao.findOne(2L));
            userDao.save(users);
        }

        System.out.println("size of new users list divide by two= " + newU.size()/2);
        System.out.println("size of users in group 1 = " + groupDao.findOne(1L).getUsers().size());
        System.out.println("size of new machines list divide by two= " + newM.size()/2);

        return "redirect:/admindashboard";
    }

    @RequestMapping(value = "/week-scores", method = RequestMethod.POST)
    public String weeksScores(@ModelAttribute Score score,
                              @RequestParam(name = "user") Long newUser,
                              @RequestParam(name = "machine") Long newMachine,
                              @RequestParam(name = "newScore") Long scoreAmount) {
//        score.setUser(newUser);
        System.out.println(newUser);
//        score.setMachine(newMachine);
        System.out.println(newMachine);
        score.setScore(scoreAmount);
        System.out.println(scoreAmount);
        System.out.println(scoreDao.updateScore(newUser, newMachine, scoreAmount));

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
