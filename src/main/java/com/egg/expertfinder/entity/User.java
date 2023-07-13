package com.egg.expertfinder.entity;

import com.egg.expertfinder.enumeration.RoleEnum;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    
    //protected Image image;
    
    // protected List<Comment> comments;
    
    protected boolean active;

}
