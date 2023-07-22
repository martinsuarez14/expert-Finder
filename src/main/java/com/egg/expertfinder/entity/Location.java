package com.egg.expertfinder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false, unique = true)
    private String address;

    public Location(String country, String address) {
        this.country = country;
        this.address = address;
    }
<<<<<<< HEAD
    
    public void updateLocation(String city, String country, String address) {

        if (city != null || !city.isEmpty()) {
            this.city = city;
        }
        if (country != null || !country.isEmpty()) {
            this.country = country;
        }
        if (address != null || !address.isEmpty()) {
        }
        
        if (city != null) {
            this.city = city;
        }
=======

    public void updateLocation(String country, String address) {
>>>>>>> f76c388ae542fc6f53dc2667c957735208ba0f8d
        if (country != null) {
            this.country = country;
        }
        if (address != null) {
<<<<<<< HEAD

=======
>>>>>>> f76c388ae542fc6f53dc2667c957735208ba0f8d
            this.address = address;
        }
    }
}
