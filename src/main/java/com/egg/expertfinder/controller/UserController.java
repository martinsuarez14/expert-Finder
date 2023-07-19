package com.egg.expertfinder.controller;

import com.egg.expertfinder.entity.Image;
import com.egg.expertfinder.entity.CustomUser;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.service.UserService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.relation.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user") // localhost:8080/user
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String list(ModelMap modelo) {
        List<CustomUser> users = userService.getAllUsers();
        modelo.addAttribute("users", users);

        return "user_list.html";
    }
    @GetMapping("/register")
    public String registUser(ModelMap modelo){
        
        return "";
    }
    
    @GetMapping("update")
    public String update(){
        
        return  "";
    }
    
    @PostMapping("/updater")
    public String update(@RequestParam Long id,@RequestParam(required = false) String name,
            @RequestParam(required = false) String lastName, @RequestParam(required = false) String email,
            @RequestParam(required = false) MultipartFile file){
        try {
            userService.updateUser(id,name,lastName,email,file);
            return "index.html";
        } catch (MyException e) {
            return "/updater";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable Long id, ModelMap model) {
        try {
            CustomUser user = userService.getUserById(id);
            model.addAttribute("user", user);
            return "user-details.html";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "redirect:/home";
        }
    }

}
