package com.egg.expertfinder.repository;

import com.egg.expertfinder.entity.Service;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long>{
    List<Service> findServiceByName(String name);
    
}
