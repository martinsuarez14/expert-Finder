package com.egg.expertfinder.controller;

import com.egg.expertfinder.entity.Professional;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.service.ProfessionalService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/professional") // localhost:8080/professional
public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;

//    @PreAuthorize("hasRole('ROLE_PRO')")
    @GetMapping("/update/{id}") // /professional/update/{id}
    public String updateProfessional(@PathVariable Long id, ModelMap model) throws MyException {
        Professional professional = professionalService.getProfessionalById(id);
        model.addAttribute("professional", professional);
        return "update-professional.html";
    }

//    @PreAuthorize("hasRole('ROLE_PRO')")
    @PostMapping("/update") // /professional/update
    public String updateProfessional(Long id, String name, String lastName, String email,
            MultipartFile file, String description, String phone, ModelMap model) {
        try {
            professionalService.updateProfessional(id, name, lastName, email, file, description, phone);
            model.put("exito", "El Usuario se modificó correctamente.");
            return "redirect:/home";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "update-professional.html";
        }
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list") // /professional/list
    public String professionalList(ModelMap model) {
        List<Professional> professionals = professionalService.getAllProfessionals();
        model.addAttribute("professionals", professionals);
        return "professional-list.html";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list-deactivate") // /professional/list-deactivate
    public String professionalListDeactivate(ModelMap model) {
        List<Professional> professionals = professionalService.getProfessionalsDeactivate();
        model.addAttribute("professionals", professionals);
        return "professional-list.html";
    }

//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PRO')")
    @GetMapping("/list-activate") // /professional/list-activate
    public String professionalListActivate(ModelMap model) {
        List<Professional> professionals = professionalService.getProfessionalsActivate();
        model.addAttribute("professionals", professionals);
        return "professional-list.html";
    }

//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PRO')")
    @GetMapping("/detail/{id}") // /professional/detail/{id}
    public String profileProfessional(@PathVariable Long id, ModelMap model) throws MyException {
        Professional professional = professionalService.getProfessionalById(id);
        model.addAttribute("professional", professional);
        return "professional-detail.html";
    }

    @GetMapping("/list-by-job")
    public String listByJobName(@RequestParam String name) {
        return "";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/delete/{id}") // /professional/delete/{id}
    public String deleteProfessional(@PathVariable Long id, ModelMap model) {
        try {
            professionalService.deleteProfessional(id);
            model.put("exito", "Se eliminó el Professional Correctamente.");
            return "redirect:/home";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/professional/list";
        }
    }

    @PostMapping("/deactivate/{id}")
    public String deactivateProfessional(@PathVariable Long id, ModelMap model) {
        try {
            professionalService.deactivateProfessional(id);
            model.put("exito", "El usuario se editó con éxito.");
            return "redirect:/admin/dashboard";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/admin/dashboard";
        }
    }

    @PostMapping("/activate/{id}")
    public String activateProfessional(@PathVariable Long id, ModelMap model) {
        try {
            professionalService.activateProfessional(id);
            model.put("exito", "El usuario se editó con éxito.");
            return "redirect:/admin/dashboard";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/admin/dashboard";
        }
    }

}
