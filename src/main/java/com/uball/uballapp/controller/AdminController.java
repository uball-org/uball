package com.uball.uballapp.controller;


import com.uball.uballapp.repos.MachineRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import com.uball.uballapp.models.User;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.uball.uballapp.repos.AdminRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
    private AdminRepository adminDao;
    private MachineRepository machineDao;

    public AdminController(AdminRepository adminDao, MachineRepository machineDao) {
        this.adminDao = adminDao;
        this.machineDao = machineDao;
    }

    //Getting all Users!
    @GetMapping("/admindashboard") // needs to be factored to use the "id of button" for this element
    public String all(  Model model){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        model.addAttribute("user", adminDao.findOne(id));
        model.addAttribute("users", adminDao.findAll());
        model.addAttribute("machines", machineDao.findAll());
        return "admin/admindashboard";
    }

//    //Editing a User from adminside
//    @GetMapping("/edituser/{id}")
//    public String edit ( @PathVariable long id, Model model){
//        model.addAttribute("user", adminDao.findOne(id));
//        return "user/edituser";
//    }
//
//    @PostMapping("/edituser/{id}")
//    public String update (
//            @PathVariable long id,
//            @ModelAttribute User user)
//    {
//        User original = adminDao.findOne(id);
//        user.setId(original.getId());
//        adminDao.save(user);
//        return "redirect:/userprofile/{id}";
//    }

//
//
//    @PostMapping("/user/{id}/disable") //This will actually be to disable NOT DELETE
//    public String delete(@PathVariable long id) {
//        adminDao.delete(id);
//        return "redirect:/admindashboard";
//    }


}
