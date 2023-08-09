package com.egg.expertfinder.controller;

import com.egg.expertfinder.entity.Comment;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.service.CommentService;
import com.egg.expertfinder.service.TaskService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    
    @Autowired
    private TaskService taskService;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_PRO', 'ROLE_ADMIN')")
    @GetMapping("/list")
    public String list(ModelMap model) {
        List<Comment> comment = commentService.getAllComments();
        model.put("comment", comment);
        return "comment-list.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/register/{idTask}")
    public String registerComment(@PathVariable Long idTask, ModelMap model) {
        try {
            model.addAttribute("task", taskService.getTaskById(idTask));
            return "comment-form.html";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/home";
        }
        
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/register")
    public String registComment(@RequestParam Long idTask, @RequestParam Long idUser,
            @RequestParam Long idProfessional, @RequestParam String content,
            @RequestParam Double score, ModelMap model) {
        try {
            commentService.createComment(idTask, idUser, idProfessional, content, score);
            model.put("exito", "El comentario fue registrado.");
            return "redirect:/home";
        } catch (Exception ex) {
            model.put("error", ex.getMessage());
            return "redirect:/home";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PRO')")
    @GetMapping("/update/{id}")
    public String updateComment(@PathVariable Long id, ModelMap model) {

        try {
            Comment comment = commentService.getCommentById(id);
            model.addAttribute("comment", comment);
            return "update-comment.html";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/professional/profile/";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PRO')")
    @PostMapping("/update")
    public String updateComment(@RequestParam Long idTask, @RequestParam Long idUser,
            @RequestParam String content, ModelMap model) {
        try {
            commentService.updateComment(idTask, idUser, content);
            model.put("exito", "Se editó el comentario correctamente.");
            return "redirect:/admin/home";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/admin/home";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/comments-reports")
    public String getCommentsWithReports(ModelMap model) {
        List<Comment> comments = commentService.getCommentsWithReports();
        model.addAttribute("comments", comments);
        return "comment-list.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/deactivate/{id}")
    public String deactivateCommentById(@PathVariable Long id, ModelMap model) {
        try {
            commentService.deactivateCommentById(id);
            model.put("exito", "El comentario se desactivó con éxito.");
            return "redirect:/admin/dashboard";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/admin/comments-reports";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list-reports")
    public String getAllCommentsWithReports(ModelMap model) {
        List<Comment> comments = commentService.getCommentsWithReports();
        model.addAttribute("comments", comments);
        return "comment-list.html";
    }
    
}
