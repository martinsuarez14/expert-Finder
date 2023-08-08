package com.egg.expertfinder.controller;

import com.egg.expertfinder.entity.Task;
import com.egg.expertfinder.enumeration.StatusEnum;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.service.TaskService;
import java.util.ArrayList;
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
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/register")
    public String registerTask(@RequestParam String title, @RequestParam String description,
            @RequestParam Long idProfessional, @RequestParam Long idUser, ModelMap model) {
        try {
            taskService.createTask(title, description, idProfessional, idUser);
            model.put("exito", "Se creó la tarea correctamente.");
            return "redirect:/home";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "task-form.html";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_PRO', 'ROLE_ADMIN')")
    @GetMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, ModelMap model) {
        try {
            model.addAttribute("task", taskService.getTaskById(id));
            return "task-edit.html";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/home";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_PRO', 'ROLE_ADMIN')")
    @PostMapping("/update")
    public String updateTask(@RequestParam Long id, @RequestParam(required = false) String description,
            @RequestParam(required = false) String status, ModelMap model) {

        try {
            taskService.updateTask(id, status);
            model.put("exito", "Se editó la tarea correctamente.");
            return "redirect:/home";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/home";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_PRO', 'ROLE_ADMIN')")
    @GetMapping("/get/{id}")
    public String getTaskById(@PathVariable Long id, ModelMap model) {
        try {
            Task task = taskService.getTaskById(id);
            model.addAttribute("task", task);
            return "task-details.html";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/home";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_PRO', 'ROLE_ADMIN')")
    @GetMapping("/list")
    public String getAllTasks(ModelMap model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "task-list.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_PRO', 'ROLE_ADMIN')")
    @GetMapping("/list-by-status")
    public String getTasksByIdProfessionalAndStatus(@RequestParam Long id, @RequestParam String status, ModelMap model) {
        List<Task> tasks = taskService.getTaskByStatus(id, status);
        model.addAttribute("tasks", tasks);
        return "task-list.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_PRO', 'ROLE_ADMIN')")
    @GetMapping("/list-by-pro/{idPro}")
    public String getTasksByIdProfessional(@PathVariable Long idPro, ModelMap model) {
        List<Task> tasks = taskService.getTasksByProfessionalId(idPro);
        model.addAttribute("tasks", tasks);
        return "task-list.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_PRO', 'ROLE_ADMIN')")
    @GetMapping("/list-by-user/{idUser}")
    public String getTasksByIdUser(@PathVariable Long idUser, ModelMap model) {
        List<Task> tasks = taskService.getTasksByUserId(idUser);
        model.addAttribute("tasks", tasks);
        return "task-list.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteTaskById(@PathVariable Long id, ModelMap model) {
        try {
            taskService.deleteTaskById(id);
            model.put("exito", "Se eliminó la tarea correctamente.");
            return "redirect:/home";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/home";
        }
    }

}
