package com.egg.expertfinder.repository;

import com.egg.expertfinder.entity.Professional;
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> e92afb29bb327a2c7f62042ed61bc13ce47033ed
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
<<<<<<< HEAD
public interface ProfessionalRepository extends JpaRepository<Professional,Long>{
    //  Listamos los Professional active = true
    List<Professional> findProfessionalByActiveTrue();

    //Listamos los Professional active = false
    List<Professional> findProfessionalByActiveFalse();
    
    //Buscar un Professional by email
    Professional findProfessionalByEmail(String email);
=======
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

>>>>>>> e92afb29bb327a2c7f62042ed61bc13ce47033ed
}
