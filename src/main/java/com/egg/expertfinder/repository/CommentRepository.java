
package com.egg.expertfinder.repository;

import com.egg.expertfinder.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    
    List<Comment> findCommentByContent(String content); 
}
