package com.egg.expertfinder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
=======
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
>>>>>>> 83631ad8488dece588e6374251c64dae1397ec2c
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    @Column(nullable = false)
    protected String content;
    
    @Column(nullable = false)
    protected Double score;
    
    @Column(nullable = false)
    protected boolean active;
    
    @Column(nullable = false)
    protected Integer report;
    
    public void deactivateComment(){
        active = false;
    }
    
}

