package com.egg.expertfinder.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Fausto
 */
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    @Column(nullable = false)
    protected String content;
    
    @Column(nullable = false)
    protected Integer score;
    
    @Column(nullable = false)
    protected boolean active;
    
    @Column(nullable = false)
    protected Integer report;
    
    public void deactivateComment(){
        active = false;
    }
}
