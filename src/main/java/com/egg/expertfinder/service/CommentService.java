
package com.egg.expertfinder.service;

import com.egg.expertfinder.entity.Comment;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.repository.CommentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Transactional
    public void createComment(String content) throws MyException {
        validate(content);
        if (commentRepository.findCommentByContent(content) != null) {
            throw new MyException("Ya existe este comentario");
        }
        Comment comment = new Comment(content);        
        
       commentRepository.save(comment);
    }    
    
    @Transactional
    public void updateComment(Long id, String content) throws MyException{
        Optional<Comment> response = commentRepository.findById(id);
        if (response.isPresent()) {
            Comment comment = response.get();
            comment.setContent(content);
        } else {
            throw new MyException("No se encontro el servicio.");
        }
    }
    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }
    
    public Comment getCommentById(Long id) throws MyException{
        Optional<Comment> response = commentRepository.findById(id);
        if(response.isPresent()){
            return response.get();
        }else{
            throw new MyException("No se encontró un comentario con ese ID.");
        }
    }
    
    private void validate(String content) throws MyException {
        if(content == null || content.isEmpty()) {
            throw new MyException("El contenido del comentario no puede estar vacio.");
            
        }
    }
    
}
