package com.egg.expertfinder.controller;

import com.egg.expertfinder.entity.Task;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.service.TaskService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @PostMapping("/create")
    public String createTask(@RequestParam String description, @RequestParam Long idUser,
            @RequestParam Long idProfessional, ModelMap model) {
        try {
            taskService.createTask(description, idProfessional, idUser);
            model.put("exito", "Se creó la tarea correctamente.");
            return "redirect:/home";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "task_form.html";
        }
    }
    
    @GetMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, ModelMap model) {
        try {
            model.addAttribute("task", taskService.getTaskById(id));
            return "task_edit.html";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/home";
        }
    }
    
    @PostMapping("/update/{id}")
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
    
    @GetMapping("/list")
    public String getAllTasks(ModelMap model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "task_list.html";
    }
    
    @GetMapping("/list-by-status")
    public String getTasksByStatus(@RequestParam Long id, @RequestParam String status, ModelMap model) {
        List<Task> tasks = taskService.getTaskByStatus(id, status);
        model.addAttribute("tasks", tasks);
        return "task_list.html";
    }
    
    @GetMapping("/list/{idPro}")
    public String getTasksByIdProfessional(@PathVariable Long idPro, ModelMap model) {
        List<Task> tasks = taskService.getTasksByProfessionalId(idPro);
        model.addAttribute("tasks", tasks);
        return "task_list.html";
    }
    
    @GetMapping("/list/{idUser}")
    public String getTasksByIdUser(@PathVariable Long idUser, ModelMap model) {
        List<Task> tasks = taskService.getTasksByUserId(idUser);
        model.addAttribute("tasks", tasks);
        return "task_list.html";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteTaskById(@RequestParam Long id, ModelMap model) {
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
