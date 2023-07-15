package com.egg.expertfinder.repository;

import com.egg.expertfinder.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//  Listamos los Users active = true
    List<User> findUserByActiveTrue();

//  Listamos los Users active = false
    List<User> findUserByActiveFalse();
    
//  Buscamos usuario por email
    User findUserByEmail(String email);
}
