package com.egg.expertfinder.service;

import com.egg.expertfinder.entity.Image;
import com.egg.expertfinder.entity.CustomUser;
import com.egg.expertfinder.entity.Location;
import com.egg.expertfinder.enumeration.KeyEnum;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ImageService imageService;

//  Creamos un USER
    @Transactional
    public void createUser(String name, String lastName, String email, String password,
            String password2, String countryKey, String country, String address,
            MultipartFile file) throws MyException {

        validate(name, lastName, email, password, password2, countryKey, file);
        
        if (userRepository.findCustomUserByEmail(email) != null) {
            throw new MyException("Ya existe un usuario con ese email.");
        }

        CustomUser user = new CustomUser(name, lastName, email);

        user.setPassword(new BCryptPasswordEncoder().encode(password));

        Location location = locationService.createLocation(validateCountryKey(countryKey), address);

        user.setLocation(location);

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
            throw new Exception("No se encontró al usuario.");
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
    
    @Transactional
    public void deactivateUser(Long id) throws MyException {
        Optional<CustomUser> response = userRepository.findById(id);
        if (response.isPresent()) {
            CustomUser user = response.get();
            user.deactivateUser();
            userRepository.save(user);
        } else {
            throw new MyException("No se encontró al usuario con ese id.");
        }
    }

//  Validamos que lleguen los datos necesarios para crear un User
    private void validate(String name, String lastName, String email, String password,
            String password2, String countryKey, MultipartFile file) throws MyException {
        if (name == null || name.isEmpty()) {
            throw new MyException("El nombre no debe estar vacío.");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new MyException("El apellido no debe estar vacío.");
        }
        if (email == null || email.isEmpty()) {
                        throw new MyException("Debe ingresar un correo");
                    } else if (!email.contains("@")) {
                        throw new MyException("El correo debe poseer '@'");
                    } else if (email.substring(email.length() - 1).equals("@")) {
                        throw new MyException("El correo debe poseer caracteres luego de la '@'");
                    }
        if (!password.equals(password2)) {
            throw new MyException("Las contraseñas no coinciden.");
        }
        if (password == null || password.isEmpty()) {
            throw new MyException("La contraseña no puede ser nula o estar vacía.");
        }
        if (password.length() <= 5) {
            throw new MyException("La contraseña no puede contener 5 caracteres o menos.");
        }
        if (countryKey == null || countryKey.isEmpty()) {
            throw new MyException("Debe ingresar la clave del Barrio.");
        }
        if (file == null || file.isEmpty()) {
            throw new MyException("Debe ingresar una imagen de perfil.");
        }
    }
     public void validateAll(String name, String lastName, String email, String password,
            String password2, String countryKey,String address, MultipartFile file,int num) throws MyException {
        switch (num) {
               case 1:
                   if (name == null || name.isEmpty()) {
                       throw new MyException("El nombre no puede ser nulo o estar vacío.");
                   }
                   break;
               case 2:
                   if (lastName == null || lastName.isEmpty()) {
                       throw new MyException("El apellido no puede ser nulo o estar vacío.");
                   }
                   break;
               case 3:
                   if (password == null || password.isEmpty()) {
                      throw new MyException("La contraseña no puede ser nula o estar vacía.");
                   }else if(password.length() <= 5){
                       throw new MyException("La contraseña no puede contener 5 caracteres o menos.");
                   }
                   break;
               case 4:
                   if (!password2.equals(password)) {
                       throw new MyException("Las contraseñas no coinciden.");
                   }
                   break;
               case 5:
                   if (address == null || address.isEmpty()) {
                       throw new MyException("Debe ingresar una direccion");
                   }
                   break;
               case 6:
                   if (file == null || file.isEmpty()) {
                       throw new MyException("Debe ingresar una imagen de perfil.");
                   }
                   break;
               case 7:
                   if (countryKey == null || countryKey.isEmpty()) {
                    throw new MyException("Debe ingresar la clave del Barrio.");
                   }else{
                       validateCountryKey(countryKey);
                   }
                   break;
                case 8:
                    if (email == null || email.isEmpty()) {
                        throw new MyException("Debe ingresar un correo");
                    } else if (!email.contains("@")) {
                        throw new MyException("El correo debe poseer '@'");
                    } else if (email.substring(email.length() - 1).equals("@")) {
                        throw new MyException("El correo debe poseer caracteres luego de la '@'");
                    }
                       break;
           }
    }

    private String validateCountryKey(String countryKey) throws MyException {

        String[] claves = new String[3];

        claves[0] = KeyEnum.BARRIO1.toString();
        claves[1] = KeyEnum.BARRIO2.toString();
        claves[2] = KeyEnum.BARRIO3.toString();

        int count = 0;

        for (int i = 0; i < claves.length; i++) {

            boolean flag = claves[i].equals(countryKey);

            if (claves[i].equals(countryKey)) {
                break;
            } else {
                count++;
            }
        }
        if (count == 3) {
            throw new MyException("La contraseña del barrio es incorrecta.");
        }
        return countryKey;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        CustomUser userLogin = userRepository.findCustomUserByEmail(email);

        if (userLogin != null) {

            List<GrantedAuthority> permissions = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + userLogin.getRole().toString());

            permissions.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usersession", userLogin);

            return new User(userLogin.getEmail(), userLogin.getPassword(), permissions);
        } else {
            return null;
        }
    }

}
