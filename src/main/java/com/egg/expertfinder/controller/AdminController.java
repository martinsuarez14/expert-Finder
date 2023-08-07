package com.egg.expertfinder.controller;

import com.egg.expertfinder.entity.CustomUser;
import com.egg.expertfinder.service.UserService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, ModelMap model) {
        
        CustomUser userLogin = (CustomUser) session.getAttribute("usersession");
        
        List<CustomUser> users = userService.getUsersWithRolePro();
        
        model.addAttribute("users", users);
        
        return "panel.html";
    }
    
    @GetMapping("/users-list")
    public String usersList(ModelMap model) {
        List<CustomUser> users = userService.getAllUsers();
        
        model.addAttribute("users", users);
        
        return "users-list.html";
    }
    
    @GetMapping("/reports")
    public String reports() {
        return "reports.html";
    }
    
    
}
