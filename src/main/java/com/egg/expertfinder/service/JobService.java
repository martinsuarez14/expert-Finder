package com.egg.expertfinder.service;

import com.egg.expertfinder.entity.Job;
import com.egg.expertfinder.exception.MyException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.egg.expertfinder.repository.JobRepository;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    
    @Autowired
    private JobRepository jobRepository;
    
    @Transactional
    public void createJob(String name) throws MyException {
        validate(name);
        if (jobRepository.findJobByName(name) != null) {
            throw new MyException("Ya existe este servicio");
        }
        Job job = new Job(name);
        
       jobRepository.save(job);
    }
    
    @Transactional
    public void deleteJob(Long id) throws MyException {
        Optional<Job> response = jobRepository.findById(id);
        if (response.isPresent()) {
            jobRepository.delete(response.get());
        } else {
            throw new MyException("No se encontro el servicio.");
        }
    }
    @Transactional
    public void updateJob(Long id, String name) throws MyException{
        Optional<Job> response = jobRepository.findById(id);
        if (response.isPresent()) {
            Job job = response.get();
            job.setName(name);
        } else {
            throw new MyException("No se encontro el servicio.");
        }
    }
    public List<Job> getAllJobs(){
        return jobRepository.findAll();
    }
    
    public Job getJobById(Long id) throws MyException{
        Optional<Job> response = jobRepository.findById(id);
        if(response.isPresent()){
            return response.get();
        }else{
            throw new MyException("No se encontró un Servicio con ese ID.");
        }
    }
    
    private void validate(String name) throws MyException {
        if(name == null || name.isEmpty()) {
            throw new MyException("El nombre del servicio no puede estar vacio.");
            
        }
    }
}
