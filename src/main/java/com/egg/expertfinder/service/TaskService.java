package com.egg.expertfinder.service;

import com.egg.expertfinder.entity.Professional;
import com.egg.expertfinder.entity.Task;
import com.egg.expertfinder.enumeration.StatusEnum;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.repository.ProfessionalRepository;
import com.egg.expertfinder.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Transactional
    public void createTask(String description, Long id) throws MyException {
        validate(description, id);

        Optional<Professional> response = professionalRepository.findById(id);
        if (response.isPresent()) {
            Professional professional = response.get();
            Task task = new Task(description);

            List<Task> tasks = professional.getTasks();

            tasks.add(taskRepository.save(task));
            professional.setTasks(tasks);
            professionalRepository.save(professional);
        }

    }

    public void updateTask(Long idTask, String newStatus) {
        Optional<Task> response = taskRepository.findById(idTask);
        if (response.isPresent()) {
            Task task = response.get();
            task.setStatus(StatusEnum.valueOf(newStatus));
            taskRepository.save(task);
        }
    }
    
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
    
    public Task getTaskById(Long id) throws MyException{
        Optional<Task> response = taskRepository.findById(id);
        if(response.isPresent()){
            return response.get();
        }else{
            throw new MyException("No se encontró la tarea.");
        }
    }
    
    public List<Task> getTaskByStatus(Long idPro, String status) {
        return taskRepository.findTasksByProfessionalAndStatus(idPro, status);
    }
    
    private void validate(String description, Long id) throws MyException {
        if (description == null || description.isEmpty()) {
            throw new MyException("La tarea no puede estar vacia.");
        }
        if (id == null) {
            throw new MyException("El id del profesional no puede ser nulo.");
        }
    }
}
