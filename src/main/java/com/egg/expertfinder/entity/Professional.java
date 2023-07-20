package com.egg.expertfinder.entity;

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
public class Professional extends CustomUser{
    
    @Column(nullable = false)
    protected String description;
    
    @Column(nullable = false)
    protected String license;
    
    @Column(nullable = false)
    protected String phone;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    protected Comment comment;
}
