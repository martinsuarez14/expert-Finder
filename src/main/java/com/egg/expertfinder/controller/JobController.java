package com.egg.expertfinder.controller;

import com.egg.expertfinder.entity.Job;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.service.JobService;
import java.util.ArrayList;
import java.util.List;
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
@RequestMapping("/job")
public class JobController {
    
    @Autowired
    private JobService jobService;
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/register")
    public String registerJob(ModelMap Model){
        return "job-register.html";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register")
    public String registerJob(@RequestParam String name, MultipartFile file, ModelMap model){
        try {
            jobService.createJob(name, file);
            model.put("exito", "Se creó con éxito el Servicio.");
            return "redirect:/admin/dashboard";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "job-register.html";
        }
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/update/{id}")
    public String updateJob(@PathVariable Long id, ModelMap model){
        try {
            Job job = jobService.getJobById(id);
            model.addAttribute("job", job);
            return "job-update.html";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "job-update.html";
        }
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/update")
    public String updateJob(@RequestParam Long id, @RequestParam(required = false) String name, 
            @RequestParam(required = false) MultipartFile file, ModelMap model){
        try {
            jobService.updateJob(id, name, file);
            model.put("exito", "Se editó el Servicio correctamente.");
            return "redirect:/admin/home";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "job-update.html";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_PRO')")
    @GetMapping("/list")
    public String list(ModelMap model){
        List<Job> jobs = jobService.getAllJobs();
        model.addAttribute("jobs", jobs);
        return "job-list.html";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteJobById(@PathVariable Long id, ModelMap model) {
        try {
            jobService.deleteJob(id);
            model.put("exito", "Se eliminó el Servicio correctamente.");
            return "redirect:/admin/dashboard";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/admin/dashboard";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_PRO')")
    @GetMapping("/buscar")
    public String findJobs(@RequestParam(name = "q", required = false) String query, ModelMap model) {
        if (query != null && !query.isEmpty()) {
            List<Job> results = new ArrayList<>();
            for (Job job : jobService.getAllJobs()) {
                if (job.getName().toLowerCase().contains(query.toLowerCase())) {
                    results.add(job);
                }
            }
            model.addAttribute("results", results);
        } else {
            model.addAttribute("results", null);
        }

        return "/fragments/busqueda-trabajos-fragment.html"; // Nombre de la vista HTML parcial para la lista de resultados
    }
    
}
