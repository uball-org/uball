package com.uball.uballapp.controller;


import com.uball.uballapp.models.Group;
import com.uball.uballapp.models.Machine;
import com.uball.uballapp.models.Score;
import com.uball.uballapp.repos.*;
import com.uball.uballapp.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.validation.Valid;
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

        User userSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = userSession.getId();
        boolean isAdmin = userSession.isAdmin();

        model.addAttribute("groups", groupDao.findAll());
        model.addAttribute("users", adminDao.findAll());
        model.addAttribute("machines", machineDao.findAll());
        model.addAttribute("scores", scoreDao.findDistinctByAddedscoredateAndScore(LocalDate.now(), 0));
        model.addAttribute("machiness", machineDao.findAllByMachine_Id(0));
        model.addAttribute("userselect", userDao.findAllByUserId());
        model.addAttribute("score", new Score());

        if (isAdmin) {
            model.addAttribute("group", new Group());
            return "admin/admindashboard";
        } else {
            return "redirect:/userprofile";
        }
    }

    @GetMapping ("/isNotAdmin/{id}")
    public String notAdminNow(@ModelAttribute User user,
                              @PathVariable long id) {
        userDao.isNewNonAdmin(id);
        return "redirect:/admindashboard";
    }

    @GetMapping("/isAdmin/{id}")
    public String isAdminNow(@ModelAttribute User user,
                              @PathVariable long id) {
        userDao.isNewAdmin(id);
        return "redirect:/admindashboard";
    }


            //Value of user selected on view is : "uchecked" / "mchecked"
    @RequestMapping(value = "/admindashboard/creategrouping", method = RequestMethod.POST)
    public String newUsersForGroups(@ModelAttribute Group group,
                                    @RequestParam(name = "uchecked") List<User> newU,
                                    @RequestParam(name = "group_id") long groupId,
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
            System.out.println(groupId);
            users.getGroups().add(groupDao.findOne(groupId));
            userDao.save(users);
        }

        System.out.println("size of new users list divide by two= " + newU.size()/2);
        System.out.println("size of new machines list divide by two= " + newM.size()/2);

        return "redirect:/admindashboard";
    }

    @PostMapping("/week-scores")
    public String weeksScores(@ModelAttribute Score score,
                              @RequestParam(name = "score") Long scoreAmount) {

        Machine machine = score.getMachine();
        User user = score.getUser();
        long scored = score.getScore();
        score.setScore(scored);
        scoreDao.updateScore(scoreAmount, machine, user);
        return "redirect:/admindashboard";
    }


    @GetMapping ("/weeks-scores")
    public String groupWeek(Model model){

        User userSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = userSession.getId();
        boolean isAdmin = userSession.isAdmin();

        model.addAttribute("scores1", scoreDao.findAllInGroup(1L, LocalDate.now()));
        model.addAttribute("scores2", scoreDao.findAllInGroup(2L, LocalDate.now()));
        model.addAttribute("scores3", scoreDao.findAllInGroup(3L, LocalDate.now()));
        model.addAttribute("scores4", scoreDao.findAllInGroup(4L, LocalDate.now()));
        model.addAttribute("group1", groupDao.findAllInGroup(LocalDate.now()));

        return "admin/weeks-scores";

    }

    @GetMapping("/admindashboard/updatepoints/{id}")
    public String updatepoints(@ModelAttribute User user,
                               @PathVariable long id,
                               @RequestParam(name = "points") long newPoints) {

        System.out.println("userId = " + id);
        System.out.println("newPoints = " + newPoints);
        userDao.updatePointsAdd(newPoints, id);
        return "redirect:/weeks-scores";
    }

    @PostMapping("/admindashboard/updatepoints")
    public String insertNewPoints(
            @RequestParam (name = "points") List<Long> addPoints) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (!user.getRole().equals("instructor")) {
//            return "redirect:/";
//        }
        Iterable<User> userPointsUpdate = userDao.findAllByUserId();
        int i = 0;
        for (User userpointsupdate : userPointsUpdate) {
            userpointsupdate.setPoints(addPoints.get(i));
            i++;
        }
        userDao.save(userPointsUpdate);
        return "redirect:/weeks-scores";
    }




    // disable button
//
//    @PostMapping("/user/{id}/disable") //This will actually be to disable NOT DELETE
//    public String delete(@PathVariable long id) {
//        adminDao.delete(id);
//        return "redirect:/admindashboard";
//    }


}
