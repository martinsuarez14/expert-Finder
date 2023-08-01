
package com.egg.expertfinder.repository;

import com.egg.expertfinder.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    
    @Query("SELECT c FROM Comment c WHERE c.id = :id")
    Comment findCommentById(@Param("id") Long id); 
    
    @Query("SELECT c FROM Comment c WHERE c.user.id = :id")
    Comment findCommentByUser_Id(@Param("id") Long id);
    
    @Query("SELECT c FROM Comment c WHERE c.reports > 0")
    List<Comment> findCommentsWithReportsGreaterThanZero();
    
}
