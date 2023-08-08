package com.egg.expertfinder.controller;

import com.egg.expertfinder.entity.CustomUser;
import com.egg.expertfinder.entity.Image;
import com.egg.expertfinder.entity.Job;
import com.egg.expertfinder.entity.Professional;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.service.ImageService;
import com.egg.expertfinder.service.JobService;
import com.egg.expertfinder.service.ProfessionalService;
import com.egg.expertfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/image")
public class ImageController {
    
    @Autowired
    private ImageService imageService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProfessionalService professionalService;
    
    @Autowired
    private JobService jobService;
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_PRO', 'ROLE_ADMIN')")
    @GetMapping("/profile-user/{id}")
    public ResponseEntity<byte[]> imageUser(@PathVariable Long id) throws Exception {
        
        CustomUser user = userService.getUserById(id);
        
        byte[] image = user.getImage().getContent();
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
    
    @GetMapping("/profile-professional/{id}")
    public ResponseEntity<byte[]> imageProfessional(@PathVariable Long id) throws Exception {
        
        Professional professional = professionalService.getProfessionalById(id);
        
        byte[] image = professional.getImage().getContent();
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
    
    @GetMapping("/job/{id}")
    public ResponseEntity<byte[]> imageJob(@PathVariable Long id) throws MyException {
        
        Job job = jobService.getJobById(id);
        
        byte[] image = job.getImage().getContent();
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_PRO', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> imageById(@PathVariable Long id) throws MyException {
        
        Image image = imageService.getImage(id);
        
        byte[] img = image.getContent();
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(img, headers, HttpStatus.OK);
    }
    
}
