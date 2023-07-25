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


    public void updateLocation(String country, String address) {

        if (country != null) {
            this.country = country;
        }
        if (address != null) {

            this.address = address;
        }
    }
}
