package com.egg.expertfinder.service;

import com.egg.expertfinder.entity.Comment;
import com.egg.expertfinder.entity.CustomUser;
import com.egg.expertfinder.entity.Image;
import com.egg.expertfinder.entity.Location;
import com.egg.expertfinder.entity.Professional;
import com.egg.expertfinder.enumeration.KeyEnum;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.repository.ProfessionalRepository;
import com.egg.expertfinder.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfessionalService {
    @Autowired
    private ProfessionalRepository professionalRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LocationService locationService;

    @Autowired
    private ImageService imageService;
    
    
    //Creación de un profesional.
    @Transactional
    public void createProfessional(String name, String lastName, String email, String password,
            String password2, String address, MultipartFile file, 
            String description, String license, String phone) throws MyException{
            
        validate(name, lastName, email, password, password2, file, description, license, phone);
        
        Professional professional = new Professional(name, lastName, email,
        description, license, phone);
        
        //Seteo de contraseña encriptada.
        professional.setPassword(new BCryptPasswordEncoder().encode(password));
        
        Location location = new Location();
        
        location.setAddress(address);
        
        professional.setLocation(location);
        
        Image image = imageService.createImage(file);

        professional.setImage(image);
        
        professionalRepository.save(professional);
    }
    
    @Transactional
    public void updateProfessional(Long id, String name, String lastName, String email,
            MultipartFile file, String description, String phone) throws MyException {
        Optional<Professional> response = professionalRepository.findById(id);
        if(response.isPresent()){
            Professional professional = response.get();
            
            professional.updateProfessional(name, lastName, description, phone);
            //Comprobamos que si llega un email para actualizar no exista en la DB
            if(email!=null){
                Professional proEmail = professionalRepository.findProfessionalByEmail(email);
                CustomUser userEmail = userRepository.findCustomUserByEmail(email);
                if (proEmail!=null || userEmail!=null) {
                    throw new MyException("Ya existe un usuario registrado con ese email.");
                } else {
                    professional.setEmail(email);
                }
            }
            
            //Si llega un MultipartFile lo actualizamos
            if (file != null) {
                Long idImage = professional.getImage().getId();
                Image image = imageService.updateImage(idImage, file);
                professional.setImage(image);
            }
            
            professionalRepository.save(professional);
        }
    }
    
    public void validate(String name, String lastName, String email, String password,
            String password2, MultipartFile file, String description, 
            String license, String phone) throws MyException {
        if(name == null || name.isEmpty()) {
            throw new MyException("El nombre no puede ser nulo o estar vacío.");
        }
        if(lastName == null || lastName.isEmpty()) {
            throw new MyException("El apellido no puede ser nulo o estar vacío.");
        }
        if(password == null || password.isEmpty()) {
            throw new MyException("La contraseña no puede ser nula o estar vacía.");
        }
        if(password.length()<=5) {
            throw new MyException("La contraseña no puede contener 5 caracteres o menos.");
        }
        if(!password2.equals(password)){
            throw new MyException("Las contraseñas no coinciden.");
        }
        if (file == null) {
            throw new MyException("Debe ingresar una imagen de perfil.");
        }
        if (description == null || description.isEmpty()) {
            throw new MyException("Debe ingresar una descripción.");
        }
        if (license == null || license.isEmpty()) {
            throw new MyException("Debe presentar su matrícula.");
        }
        if (phone == null || phone.isEmpty()) {
            throw new MyException("Debe ingresar su número de teléfono.");
        }
    }
}
