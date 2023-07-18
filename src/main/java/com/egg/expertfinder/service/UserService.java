package com.egg.expertfinder.service;

import com.egg.expertfinder.entity.Image;
import com.egg.expertfinder.entity.CustomUser;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

//  Creamos un USER
    @Transactional
    public void createUser(String name, String lastName, String email, String password,
            String password2, String role, MultipartFile file) throws MyException {

        validate(name, lastName, email, password, password2, role, file);

        if (userRepository.findCustomUserByEmail(email) != null) {
            throw new MyException("Ya existe un usuario con ese email.");
        }

        CustomUser user = new CustomUser(name, lastName, email, role);
        
        user.setPassword(new BCryptPasswordEncoder().encode(password));

        Image image = imageService.createImage(file);

        user.setImage(image);

        userRepository.save(user);
    }

//  Actualización de un User
    @Transactional
    public void updateUser(Long id, String name, String lastName, String email,
            MultipartFile file) throws MyException {

//      Corroboramos que exista el User con el Id que llega
        Optional<CustomUser> response = userRepository.findById(id);
        if (response.isPresent()) {
            CustomUser user = response.get();
//          Actualizamos los valores que hayan llegado completos.

            user.updateUser(name, lastName);
            
//          Comprobamos que si llega un email para actualizar no exista en la DB
            if (email != null) {
                CustomUser userEmail = userRepository.findCustomUserByEmail(email);
                if (userEmail != null) {
                    throw new MyException("Ya existe un usuario con ese Email.");
                } else {
                    user.setEmail(email);
                }
            }

//          Si llega un MultipartFile lo actualizamos
            if (file != null) {
                Long idImage = user.getImage().getId();
                Image image = imageService.updateImage(idImage, file);
                user.setImage(image);
            }

            userRepository.save(user);
        }
    }

//  Traemos un User si es que existe
    public CustomUser getUserById(Long id) throws Exception {
        Optional<CustomUser> response = userRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new Exception("No se encontró al usuario");
        }
    }

//  Listamos los Users activos
    public List<CustomUser> getUsersActiveTrue() {
        return userRepository.findCustomUserByActiveTrue();
    }

//  Listamos los users desactivados
    public List<CustomUser> getUsersActiveFalse() {
        return userRepository.findCustomUserByActiveFalse();
    }

//  Listamos TODOS los Users
    public List<CustomUser> getAllUsers() {
        return userRepository.findAll();
    }

//  Eliminamos un User
    @Transactional
    public void deleteUser(Long id) throws Exception {
        Optional<CustomUser> response = userRepository.findById(id);
        if (response.isPresent()) {
            userRepository.delete(response.get());
        } else {
            throw new Exception("No se encontró al usuario");
        }
    }

//  Validamos que lleguen los datos necesarios para crear un User
    private void validate(String name, String lastName, String email, String password,
            String password2, String role, MultipartFile file) throws MyException {

        if (name == null || name.isEmpty()) {
            throw new MyException("El nombre no de estar vacío.");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new MyException("El apellido no de estar vacío.");
        }
        if (email == null || email.isEmpty()) {
            throw new MyException("El email no de estar vacío.");
        }
        if (!password.equals(password2)) {
            throw new MyException("Las contraseñas no coinciden.");
        }
        if (password.length() <= 5 || password.isEmpty()) {
            throw new MyException("La constraseña no debe estar vacía y debe ser mayor a 5 caracteres.");
        }
        if (role == null || role.isEmpty()) {
            throw new MyException("Debe ingresar un rol.");
        }
        if (file == null) {
            throw new MyException("Debe ingresar una imagen de perfil.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        CustomUser userLogin = userRepository.findCustomUserByEmail(email);
        
        if (userLogin != null) {
            
            List<GrantedAuthority> permissions = new ArrayList<>();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + userLogin.getRole().toString());

            permissions.add(p);
            
            return new User(userLogin.getEmail(), userLogin.getPassword(), permissions);
        } else {
            return null;
        }
    }

}
