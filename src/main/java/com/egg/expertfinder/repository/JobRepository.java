package com.egg.expertfinder.repository;

import com.egg.expertfinder.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>{
    
    Job findJobByName(String name);
    
}
