package com.egg.expertfinder.controller;

import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/location")
public class LocationController {
    
    @Autowired
    private LocationService locationService;
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_PRO')")
    @PostMapping("/update/{id}")
    public String updateLocationById(@PathVariable Long id, @RequestParam(required = false) String country,
            @RequestParam(required = false) String address, ModelMap model) {
        try {
            locationService.updateLocation(id, country, address);
            model.put("exito", "Se editó la ubicación correctamente.");
            return "redirect:/home";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "";
        }
    }
    
    
}