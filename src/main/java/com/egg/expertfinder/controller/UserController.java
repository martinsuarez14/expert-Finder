package com.egg.expertfinder.controller;

import com.egg.expertfinder.entity.Image;
import com.egg.expertfinder.entity.User;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.service.UserService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.relation.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user") // localhost:8080/user
public class UserController {
    @Autowired
    private UserService userservice;

    @GetMapping("/users")
    public String list(ModelMap modelo) {
        List<User> users = userservice.getAllUsers();
        modelo.addAttribute("users", users);

        return "user_list.html";
    }
    @GetMapping("/register")
    public String registUser(ModelMap modelo){
        
        return "";
    }
    
    @PostMapping("/regist")
    public String regist(@RequestParam String name,@RequestParam String lastName,@RequestParam String email,@RequestParam String password,@RequestParam String password2,@RequestParam String role,@RequestParam MultipartFile file){
        try {
            userservice.createUser(name, lastName, email, password, password2, role, file);
             return "";
        } catch (MyException ex) {
             Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
             return "/regist";
        }
        }
    
    @GetMapping("update")
    public String update(){
        
        return  "";
    }
    
    @PostMapping("/updater")
    public String update(@RequestParam Long id,@RequestParam String name,@RequestParam String lastName,@RequestParam String email,@RequestParam MultipartFile file){
        try {
            userservice.updateUser(id,name,lastName,email,file);
            return "";
        } catch (MyException e) {
            return "/updater";
        }
    }
    

}
