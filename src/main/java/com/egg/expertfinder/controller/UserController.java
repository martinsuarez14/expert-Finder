package com.egg.expertfinder.controller;

import com.egg.expertfinder.entity.CustomUser;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.service.UserService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list") // /user/list
    public String list(ModelMap modelo) {
        List<CustomUser> users = userService.getAllUsers();
        modelo.addAttribute("users", users);
        return "user-list.html";
    }

//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/update/{id}") // /user/update/{id}
    public String updateUser(@PathVariable Long id, ModelMap model) {
        try {
            CustomUser user = userService.getUserById(id);
            model.addAttribute("user", user);
            return "update-user.html";
        } catch (Exception ex) {
            model.put("error", ex.getMessage());
            return "update-user.html";
        }
    }

//    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/update") // /user/update
    public String updateUser(@RequestParam Long id, @RequestParam(required = false) String name,
            @RequestParam(required = false) String lastName, @RequestParam(required = false) String email,
            @RequestParam(required = false) MultipartFile file, ModelMap model) {
        try {
            userService.updateUser(id, name, lastName, email, file);
            model.put("exito", "El Usuario se modificó correctamente.");
            return "redirect:/home";
        } catch (MyException e) {
            model.put("error", e.getMessage());
            return "update-user.html";
        }
    }

//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/get/{id}") // /user/user/{id}
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

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list-deactivate")
    public String getUsersDeactivate(ModelMap model) {
        List<CustomUser> users = userService.getUsersActiveFalse();
        model.addAttribute("users", users);
        return "user-list.html";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list-activate")
    public String getUsersActivate(ModelMap model) {
        List<CustomUser> users = userService.getUsersActiveTrue();
        model.addAttribute("users", users);
        return "user-list.html";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteUserById(@PathVariable Long id, ModelMap model) {
        try {
            userService.deleteUser(id);
            model.put("exito", "Se eliminó el usuario correctamente.");
            return "redirect:/admin/dashboard";
        } catch (Exception ex) {
            model.put("error", ex.getMessage());
            return "redirect:/admin/dashboard";
        }
    }
    
    @PostMapping("/deactivate/{id}")
    public String deactivateUser(@PathVariable Long id, ModelMap model) {
        try {
            userService.deactivateUser(id);
            model.put("exito", "El usuario fué desactivado con éxito.");
            return "redirect:/admin/dashboard";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/admin/dashboard";
        }
    }

}
