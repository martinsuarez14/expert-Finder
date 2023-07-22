/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.expertfinder.controller;

import com.egg.expertfinder.entity.CustomUser;
import com.egg.expertfinder.entity.Professional;
import com.egg.expertfinder.service.ImageService;
import com.egg.expertfinder.service.ProfessionalService;
import com.egg.expertfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    
//    @GetMapping("/noticia/{id}")
//    public ResponseEntity<byte[]> imagenNoticia(@PathVariable Long id) {
//        
//        Noticia noticia = noticiaServicio.getOne(id);
//        
//        Imagen foto = imagenServicio.traerImagen(noticia.getImagen().getId());
//        
//        byte[] imagen = foto.getContenido();
//        
//        HttpHeaders headers = new HttpHeaders();
//        
//        headers.setContentType(MediaType.IMAGE_JPEG);
//        
//        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
//    }
    
//    @GetMapping("/periodista/{id}")
//    public ResponseEntity<byte[]> imagenPeriodista(@PathVariable Long id) {
//        
//        Periodista periodista = periodistaServicios.getOne(id);
//        
//        Imagen foto = imagenServicio.traerImagen(periodista.getImagen().getId());
//        
//        byte[] imagen = foto.getContenido();
//        
//        HttpHeaders headers = new HttpHeaders();
//        
//        headers.setContentType(MediaType.IMAGE_JPEG);
//        
//        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
//    }
}
