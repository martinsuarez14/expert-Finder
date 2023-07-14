package com.egg.expertfinder.repository;

import com.egg.expertfinder.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{


}
