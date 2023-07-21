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
    protected Integer reports = 0;
    
    public Comment(String content, Double score) {
        this.content = content;
        this.score = score;
        this.active = true;
    }
    
    public void updateComment(String content) {
        if (content != null) {
            this.content = content;
        }
    }
    
    public void deactivateComment(){
        active = false;
    }
    
    public void updateReports() {
        this.reports += 1;
    }
}

