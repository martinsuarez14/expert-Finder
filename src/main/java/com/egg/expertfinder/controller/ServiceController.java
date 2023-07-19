package com.egg.expertfinder.controller;

import com.egg.expertfinder.entity.Service;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.service.ServiceService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/Service")
public class ServiceController {
    
    @Autowired
    
    private ServiceService serviceService;
    
    @GetMapping("/Services")
    public String list(ModelMap model){
        List<Service> services=serviceService.getAllServices();
        model.put("services", services);
        return "service_list.html";
    }
    
    @GetMapping("/register")
    public String registerService(ModelMap Model){
        return "";
    }
    @PostMapping("/regist")
    public String regist(@RequestParam String name){
        try {
            serviceService.createService(name);
            return "";
        } catch (MyException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
            return "/regist";
        }
    }
    
    @GetMapping("/update")
    public String update(){
        return "";
    }
    
    @PostMapping("/updater")
    public String updater(@RequestParam Long id,@RequestParam String name){
        try {
            serviceService.updateService(id, name);
            return "";
        } catch (MyException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
            return "/updater";
        }
    }
}
