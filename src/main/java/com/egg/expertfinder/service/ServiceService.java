package com.egg.expertfinder.service;

import com.egg.expertfinder.entity.Service;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.repository.ServiceRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;
    @Transactional
    public void createService(String name) throws MyException{
        validate(name);
        if(serviceRepository.findServiceByName(name)!=null){
            throw new MyException("Ya existe este servicio");
        }
        Service service = new Service(name);
        serviceRepository.save(service);
    }
    @Transactional
    public void deleteService(Long id) throws MyException{
        Optional<Service> response=serviceRepository.findById(id);
        if(response.isPresent()){
            serviceRepository.delete(response.get());
        }else{
            throw new MyException("No se encontro el servicio");
        }
    }
    @Transactional
    public void updateService(Long id,String name) throws MyException{
        Optional<Service> response=serviceRepository.findById(id);
        if(response.isPresent()){
            Service service =response.get();
            service.setName(name);
        }else{
            throw new MyException("No se encontro el servicio");
        }
    }
    public List<Service> getAllServices(){
        return serviceRepository.findAll();
    } 
    private void validate(String name) throws MyException{
        if(name==null || name.isEmpty()){
            throw new MyException("El nombre del servicio no puede estar vacio");
            
        }
    }
}
