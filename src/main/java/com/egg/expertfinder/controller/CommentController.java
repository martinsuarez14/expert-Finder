
package com.egg.expertfinder.controller;

import com.egg.expertfinder.entity.Comment;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.service.CommentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comment")
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    @GetMapping("/list")
    public String list(ModelMap model){
        List<Comment> comment = commentService.getAllComments();
        model.put("comment", comment);
        return "comment_list.html";
    }
    
    @GetMapping("/register")
    public String registerComment(ModelMap Model){
        return "comment-register.html";
    }
    
    @PostMapping("/register")
    public String registComment(@RequestParam String content, ModelMap model)  {
        try {
            commentService.createComment(content);
            model.put("exito", "Se ha agregado su comentario.");
            return "redirect:/admin/dashboard";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "comment-register.html";
        }
    }
    
    @GetMapping("/update/{id}")
    public String updateComment(@PathVariable Long id, ModelMap model){
        try {
            Comment comment = commentService.getCommentById(id);
            model.addAttribute("comment", comment);
            return "update-comment.html";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/admin/home";
        }
    }
    
    @PostMapping("/update")
    public String updateComment(@RequestParam Long id, @RequestParam String content, ModelMap model){
        try {
            commentService.updateComment(id, content);
            model.put("exito", "Se editó el comentario correctamente.");
            return "redirect:/admin/home";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/admin/home";
        }
    }
}
