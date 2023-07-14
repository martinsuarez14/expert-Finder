package com.egg.expertfinder.controller;

import com.egg.expertfinder.entity.User;
<<<<<<< HEAD
=======
import com.egg.expertfinder.service.UserService;
>>>>>>> e2cc4cbc04f5033ef4241ead7b3f31d410165294
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user") // localhost:8080/user
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listar(ModelMap modelo) {
        List<User> users = userService.getAllUsers();
        modelo.addAttribute("users", users);

        return "user_list.html";
    }

}
