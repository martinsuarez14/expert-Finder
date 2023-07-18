package com.egg.expertfinder.controller;

import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")  // localhost:8080/
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/login")  // localhost:8080/login
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Email o Contraseña invalidos!");
        }
        return "login.html";
    }

    @GetMapping("/register")  // localhost:8080/register
    public String register() {
        return "user_form.html";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name, @RequestParam String lastName, 
            @RequestParam String email, @RequestParam String password, @RequestParam String password2, 
            @RequestParam String role, @RequestParam MultipartFile file, ModelMap model) {

        try {
            userService.createUser(name, lastName, email, password, password2, role, file);
            model.put("exito", "Usuario registrado.");
            return "index.html";
        } catch (MyException e) {
            model.put("error", e.getMessage());
            return "user_form.html";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PRO')")
    @GetMapping("/home")
    public String home() {
        return "home.html";
    }
    
}
