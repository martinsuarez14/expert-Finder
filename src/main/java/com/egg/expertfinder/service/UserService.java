package com.egg.expertfinder.service;

import com.egg.expertfinder.entity.Image;
import com.egg.expertfinder.entity.User;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

//  Creamos un USER
    public void createUser(String name, String lastName, String email, String password,
            String password2, String role, MultipartFile file) throws MyException {

        validate(name, lastName, email, password, password2, role, file);

        if (userRepository.findUserByEmail(email) != null) {
            throw new MyException("Ya existe un usuario con ese email.");
        }

        User user = new User(name, lastName, email, password, role);

        Image image = imageService.createImage(file);

        user.setImage(image);

        userRepository.save(user);
    }

//  Actualización de un User
    public void updateUser(Long id, String name, String lastName, String email,
            MultipartFile file) throws MyException {

//      Corroboramos que exista el User con el Id que llega
        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {
            User user = response.get();
//          Actualizamos los valores que hayan llegado completos.

            user.updateUser(name, lastName, email);

            if (file != null) {
                Long idImage = user.getImage().getId();
                Image image = imageService.updateImage(idImage, file);
                user.setImage(image);
            }

            userRepository.save(user);
        }
    }

//  Traemos un User si es que existe
    public User getUserById(Long id) throws Exception {
        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new Exception("no se encontró al usuario");
        }
    }

//  Listamos los Users activos
    public List<User> getUsersActiveTrue() {
        return userRepository.findUserByActiveTrue();
    }

//  Listamos los users desactivados
    public List<User> getUsersActiveFalse() {
        return userRepository.findUserByActiveFalse();
    }

//  Listamos TODOS los Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//  Eliminamos un User
    public void deleteUser(Long id) throws Exception {
        Optional<User> response = userRepository.findById(id);
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

}
