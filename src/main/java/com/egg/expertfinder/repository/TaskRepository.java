package com.egg.expertfinder.repository;

import com.egg.expertfinder.entity.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  @Query("SELECT t FROM Task t JOIN t.professional p WHERE p.id = :professionalId AND t.status = :status")
   List<Task> findTasksByProfessionalAndStatus(@Param("professionalId") Long professionalId, @Param("status") String status);
}
