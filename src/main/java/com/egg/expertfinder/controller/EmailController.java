package com.egg.expertfinder.controller;

import com.egg.expertfinder.entity.Email;
import com.egg.expertfinder.service.EmailService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public String sendEmail(String to, String subject, String body, ModelMap model) {
        try {
            emailService.sendEmail(to, subject, body);
            model.put("exito", "Email enviado con éxito");
            return "redirect:/admin/dashboard";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "redirect:/admin/dashboard";
        }
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public String getAllEmails(ModelMap model) {
        try {
            List<Email> emails = emailService.getAllEmails();
            model.addAttribute("emails", emails);
            return "email-list.html";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "redirect:/admin/dashboard";
        }
    }

}
