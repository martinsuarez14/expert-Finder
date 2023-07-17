package com.egg.expertfinder.controller;

import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña invalidos!");
        }
        return "login.html";
    }

    @GetMapping("/register")
    public String register() {
        return "user_form.html";
    }

    @PostMapping("/register")
    public String registerUser(String name, String lastName, String email, String password, String password2, String role, MultipartFile file) {

        try {
            userService.createUser(name, lastName, email, password, password2, role, file);
            return "index.html";
        } catch (MyException e) {
            System.out.println("Error al crear imagen." + e.getMessage());
            return "user_form.html";
        }
    }
}
