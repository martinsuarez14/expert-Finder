package com.egg.expertfinder.controller;

import com.egg.expertfinder.entity.Comment;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.service.CommentService;
import java.util.List;
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

    @GetMapping("/list")
    public String list(ModelMap model) {
        List<Comment> comment = commentService.getAllComments();
        model.put("comment", comment);
        return "comment-list.html";
    }

    @GetMapping("/register")
    public String registerComment(ModelMap Model) {
        return "comment-register.html";
    }

    @PostMapping("/register")
    public String registComment(@RequestParam Long idTask, @RequestParam Long idUser,
            @RequestParam Long idProfessional, @RequestParam String content,
            @RequestParam Double score, ModelMap model) {
        try {
            commentService.createComment(idTask, idUser, idProfessional, content, score);
            model.put("exito", "El comentario fue registrado.");
            return "redirect:/professional/profile/" + idProfessional;
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "comment-register.html";
        }
    }

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
        return "comments-list.html";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/deactivate/{id}")
    public String deactivateCommentById(@RequestParam Long id, ModelMap model) {
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
