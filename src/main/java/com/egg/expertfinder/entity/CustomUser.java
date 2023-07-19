package com.egg.expertfinder.entity;

import com.egg.expertfinder.enumeration.RoleEnum;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
public class CustomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false)
    protected String lastName;

    @Column(nullable = false, unique = true)
    protected String email;

    @Column(nullable = false)
    protected String password;
    
    @Enumerated(value = EnumType.STRING)
    protected RoleEnum role;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    protected Location location;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    protected Image image;
    
    // protected List<Comment> comments;
    
    protected boolean active;

//  Constructor creado para instanciar un Objeto user desde el  Service.
    public CustomUser(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        if (name.equalsIgnoreCase("adminExpertFinder")) {
            this.role = RoleEnum.ADMIN;
        } else {
            this.role = RoleEnum.USER;
        }
        this.active = true;
    }
    
    public void updateUser(String name, String lastName) {
        if (name != null) {
            this.name = name;
        }
        if (lastName != null) {
            this.lastName = lastName;
        }
    }
    
}
