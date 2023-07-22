package com.egg.expertfinder.service;

import com.egg.expertfinder.entity.Location;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.repository.LocationRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Transactional
    public Location createLocation(String country, String address) throws MyException {

        validate(address);
        
        Location location = new Location(country, address);

        return locationRepository.save(location);
    }

    @Transactional
    public void updateLocation(Long id, String country, String address) throws MyException {
        Optional<Location> response = locationRepository.findById(id);
        if (response.isPresent()) {
            Location location = response.get();
            location.updateLocation(country, address);
            locationRepository.save(location);
        } else {
            throw new MyException("No se pudo modificar la ubicación.");
        }
    }

    public Location getLocation(Long id) throws MyException {
        Optional<Location> response = locationRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new MyException("No se pudo hallar la ubicación.");
        }
    }

    private void validate(String address) throws MyException {
        if (address == null || address.isEmpty()) {
            throw new MyException("Debe ingresar la dirección.");
        }
    }
}
