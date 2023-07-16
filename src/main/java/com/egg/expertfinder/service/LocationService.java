package com.egg.expertfinder.service;

import com.egg.expertfinder.entity.Location;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.repository.LocationRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location createLocation(String city, String country, String address) throws MyException {

        validate(city, country, address);

        Location location = new Location(city, country, address);

        return locationRepository.save(location);
    }

    public void updateLocation(Long id, String city, String country, String address) throws MyException {
        Optional<Location> response = locationRepository.findById(id);
        if (response.isPresent()) {
            Location location = response.get();
            location.updateLocation(city, country, address);
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

    private void validate(String city, String country, String address) throws MyException {
        if (city == null || city.isEmpty()) {
            throw new MyException("Debe ingresar una ciudad.");
        }
        if (country == null || country.isEmpty()) {
            throw new MyException("Debe ingresar el country al que pertenece.");
        }
        if (address == null || address.isEmpty()) {
            throw new MyException("Debe ingresar la dirección.");
        }
    }
}
