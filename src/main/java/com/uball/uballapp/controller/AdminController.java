package com.uball.uballapp.controller;


import com.uball.uballapp.models.Score;
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

    public AdminController(AdminRepository adminDao) {
        this.adminDao = adminDao;
    }

    //Getting all Users!
    @GetMapping("/admin/admindashboard") // needs to be factored to use the "id of button" for this element
    public String all(Model model){
        model.addAttribute("users", adminDao.findAll());
        return "admin/admindashboard";
    }

    //Show one user, by ID #
    @GetMapping("/admin/{id}/userprofile")
    public String show(@PathVariable long id, Model model){
        model.addAttribute("user", adminDao.findOne(id));
        return "user/userprofile";
    }

    //Editing a User from adminside
    @GetMapping("/admin/admindashboard/{id}/edit")
    public String edit ( @PathVariable long id, Model model){
        model.addAttribute("user", adminDao.findOne(id));
        return "admin/admindashboard";
    }

    @PostMapping("/admin/admindashboard/{id}/edit")
    public String update (
            @PathVariable long id,
            @ModelAttribute User user)
    {
        User original = adminDao.findOne(id);
        user.setId(original.getId());
        adminDao.save(user);
        return "redirect:/posts";
    }

    @PostMapping("/user/{id}/delete") //This will actually be to disable NOT DELETE
    public String delete(@PathVariable long id) {
        adminDao.delete(id);
        return "redirect:/admin/admindashboard";
    }


}
