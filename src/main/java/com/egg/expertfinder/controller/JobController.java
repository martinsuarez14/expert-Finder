package com.egg.expertfinder.controller;

import com.egg.expertfinder.entity.Job;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.service.JobService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @GetMapping("/list")
    public String list(ModelMap model){
        List<Job> jobs = jobService.getAllJobs();
        model.put("jobs", jobs);
        return "job_list.html";
    }
    
    @GetMapping("/register")
    public String registerJob(ModelMap Model){
        return "job-register.html";
    }
    
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
    
    @GetMapping("/update/{id}")
    public String updateJob(@PathVariable Long id, ModelMap model){
        try {
            Job job = jobService.getJobById(id);
            model.addAttribute("job", job);
            return "update-job.html";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/admin/home";
        }
    }
    
    @PostMapping("/update")
    public String updateJob(@RequestParam Long id, @RequestParam String name, ModelMap model){
        try {
            jobService.updateJob(id, name);
            model.put("exito", "Se editó el Servicio correctamente.");
            return "redirect:/admin/home";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/admin/home";
        }
    }
    
    @PostMapping("/delete/{id}")
    public String deleteJobById(@PathVariable Long id, ModelMap model) {
        try {
            jobService.deleteJob(id);
            model.put("exito", "Se editó el Servicio correctamente.");
            return "redirect:/admin/home";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/admin/home";
        }
    }
}
