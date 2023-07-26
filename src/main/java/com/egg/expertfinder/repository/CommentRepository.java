
package com.egg.expertfinder.repository;

import com.egg.expertfinder.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    
    Comment findCommentById(Long id); 
    
    Comment findCommentByUser_Id(Long id);
    
    @Query("SELECT c FROM Comment c WHERE c.reports > 0")
    List<Comment> findCommentsWithReportsGreaterThanZero();
}
