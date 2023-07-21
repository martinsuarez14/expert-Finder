package com.egg.expertfinder.repository;

import com.egg.expertfinder.entity.Professional;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProfessionalRepository extends JpaRepository<Professional,Long>{
    //  Listamos los Professional active = true
    List<Professional> findProfessionalByActiveTrue();

    //Listamos los Professional active = false
    List<Professional> findProfessionalByActiveFalse();
    
    //Buscar un Professional by email
    Professional findProfessionalByEmail(String email);

}
