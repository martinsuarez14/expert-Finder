package com.egg.expertfinder.repository;

import com.egg.expertfinder.entity.Professional;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional,Long> {
    
    //Listamos los Professional active = true
    List<Professional> findByActiveTrue();

    //Listamos los Professional active = false
    List<Professional> findByActiveFalse();
    
    //Buscar un Professional by email
    Professional findProfessionalByEmail(String email);

    //Buscar un professional by job name
    @Query("SELECT p FROM Professional p WHERE p.job.id = :id")
    List<Professional> findByJobId(@Param("id") Long id);
}
