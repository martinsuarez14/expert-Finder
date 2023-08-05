package com.egg.expertfinder.service;

import com.egg.expertfinder.entity.Comment;
import com.egg.expertfinder.entity.CustomUser;
import com.egg.expertfinder.entity.Professional;
import com.egg.expertfinder.entity.Task;
import com.egg.expertfinder.enumeration.StatusEnum;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.repository.ProfessionalRepository;
import com.egg.expertfinder.repository.TaskRepository;
import com.egg.expertfinder.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public void createTask(String title, String description, Long idProfessional, 
            Long idUser) throws MyException {

        validate(title, description, idProfessional, idUser);

        Optional<Professional> response = professionalRepository.findById(idProfessional);
        if (response.isPresent()) {
            Professional professional = response.get();

            Task task = new Task(title, description);

            task.setProfessional(professional);

            CustomUser user = userRepository.getReferenceById(idUser);

            task.setUser(user);

            List<Task> tasks = professional.getTasks();

            tasks.add(taskRepository.save(task));

            professional.setTasks(tasks);

            professionalRepository.save(professional);
        }
    }

    @Transactional
    public void updateTask(Long idTask, String newStatus) throws MyException {
        Optional<Task> response = taskRepository.findById(idTask);
        if (response.isPresent()) {
            Task task = response.get();

            if (newStatus != null) {
                task.setStatus(StatusEnum.valueOf(newStatus));
            }

            if (task.getStatus().equals(StatusEnum.FINALIZADA)) {
//                Enviar mensaje
                emailService.sendEmail(
                        task.getUser().getEmail(),
                        "Trabajo Finalizado",
                        "El Trabajo con el profesional " + task.getProfessional().getName() + " "
                        + task.getProfessional().getLastName() + " fue finalizado. Por favor, deja un comentario "
                        + "y una valoración de la atención recibida.");
            }
            
            taskRepository.save(task);
        } else {
            throw new MyException("No se encontró una tarea con ese Id.");
        }
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) throws MyException {
        Optional<Task> response = taskRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new MyException("No se encontró la tarea.");
        }
    }

    public List<Task> getTaskByStatus(Long idPro, String status) {
        return taskRepository.findTasksByProfessionalAndStatus(idPro, StatusEnum.valueOf(status));
    }

    public List<Task> getTasksByUserId(Long id) {
        return taskRepository.findByUser_Id(id);
    }

    public List<Task> getTasksByProfessionalId(Long id) {
        return taskRepository.findByProfessional_Id(id);
    }

    public List<Task> getTasksByUserAndProfessionalIds(Long idUser, Long idProfessional) {
        return taskRepository.findTaskByUserAndProfessionalIds(idUser, idProfessional);
    }

    @Transactional
    public void deleteTaskById(Long id) throws MyException {
        Optional<Task> response = taskRepository.findById(id);
        if (response.isPresent()) {
            taskRepository.delete(response.get());
        } else {
            throw new MyException("No se encontró una Tarea con ese Id.");
        }
    }

    private void validate(String title, String description, Long idProfessional, Long idUser) throws MyException {
        if (title == null || title.isEmpty()) {
            throw new MyException("Debe ingresar un título a la tarea.");
        }
        if (description == null || description.isEmpty()) {
            throw new MyException("La tarea no puede estar vacia.");
        }
        if (idProfessional == null) {
            throw new MyException("El id del profesional no puede ser nulo.");
        }
        if (idUser == null) {
            throw new MyException("El id del usuario no puede ser nulo.");
        }
    }

}
