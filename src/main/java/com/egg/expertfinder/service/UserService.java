package com.egg.expertfinder.service;

import com.egg.expertfinder.entity.User;
import com.egg.expertfinder.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //@Autowired
    //private ImageRepository imageRepository;
    
    public void createUser(String name, String lastName, String email, String password, String role) {

        User user = new User(name, lastName, email, password, role);

        userRepository.save(user);
    }

    public void updateUser(Long id, String name, String lastName, String email) {

        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {
            User user = response.get();

            if (name != null || !name.isEmpty()) {
                user.setName(name);
            }
            if (lastName != null || !lastName.isEmpty()) {
                user.setLastName(lastName);
            }
            if (email != null || !email.isEmpty()) {
                user.setEmail(email);
            }

            userRepository.save(user);
        }
    }

    public User getUserById(Long id) throws Exception {
        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new Exception("no se encontró al usuario");
        }
    }
//    public List<User> getUsersActiveTrue() {
//        return userRepository.findUserByActiveTrue();
//    }

//    public List<User> getUsersActiveFalse() {
//        return userRepository.findUserByActiveFalse();
//    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) throws Exception {
        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {
            userRepository.delete(response.get());
        } else {
            throw new Exception("No se encontró al usuario");
        }
    }

}
