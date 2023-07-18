package com.egg.expertfinder.repository;

import com.egg.expertfinder.entity.CustomUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {

//  Listamos los Users active = true
    List<CustomUser> findCustomUserByActiveTrue();

//  Listamos los Users active = false
    List<CustomUser> findCustomUserByActiveFalse();
    
//  Buscamos usuario por email
    CustomUser findCustomUserByEmail(String email);
}
