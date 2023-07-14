package com.egg.expertfinder.entity;

import com.egg.expertfinder.enumeration.RoleEnum;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String name;

    protected String lastName;

    protected String email;

    protected String password;
    @Enumerated(value = EnumType.STRING)
    protected RoleEnum role;
    
    @OneToOne
    protected Image image;
    
    // protected List<Comment> comments;
    
    protected boolean active;

//  Constructor creado para instanciar un Objeto user desde el  Service.
    public User(String name, String lastName, String email, String password, String role) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this. password = password;
        if (name == "adminExpertFinder") {
            this.role = RoleEnum.ADMIN;
        } else {
            this.role = RoleEnum.valueOf(role);
        }
        this.active = true;
    }
    
    public void updateUser(String name, String lastName, String email) {
        if (name != null || !name.isEmpty()) {
            this.name = name;
        }
        if (lastName != null || !lastName.isEmpty()) {
            this.lastName = lastName;
        }
        if (email != null || !email.isEmpty()) {
            this.email = email;
        }
    }
    
}
