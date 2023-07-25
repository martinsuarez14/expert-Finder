
package com.egg.expertfinder.entity;

import com.egg.expertfinder.enumeration.StatusEnum;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    
    @ManyToOne
    @JoinColumn(name = "custom_user_id")
    private CustomUser user;
    
    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;
    
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public Task(String description) {
        this.description = description;
        this.status = StatusEnum.SOLICITADA;
    }
    
}
