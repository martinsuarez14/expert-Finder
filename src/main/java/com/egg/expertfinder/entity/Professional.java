package com.egg.expertfinder.entity;

import com.egg.expertfinder.enumeration.RoleEnum;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Inheritance(strategy = InheritanceType.JOINED)
public class Professional extends CustomUser {

    /*
    *   EL PROFESIONAL TIENE, EN LA LOCATION, COUNTRY = VISITANTE;
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String license;

    @Column(nullable = false)
    private String phone;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private Comment comment;

    public void updateProfessional(String description, String phone, String name, String lastName) {
        super.updateUser(name, lastName);
        if (description != null) {
            this.description = description;
        }
        /*if (license != null){
            this.license = license; No creo que sea necesario, porque la matrícula es única.
        }*/
        if (phone != null) {
            this.phone = phone;
        }
    }


}