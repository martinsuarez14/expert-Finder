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
public class Professional extends CustomUser {

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String license;

    @Column(nullable = false)
    private String phone;
    
    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    @OneToMany
    private List<Comment> comments;

    public Professional(String name, String lastName, String email,
            String description, String license, String phone) {
        super(name, lastName, email);
        this.role = RoleEnum.PRO;
        this.active = false;
        this.description = description;
        this.license = license;
        this.phone = phone;
    }

    public void updateProfessional(String description, String license, String phone) {
        if (description != null) {
            this.description = description;
        }
        if (license != null) {
            this.license = license;
        }
        if (phone != null) {
            this.phone = phone;
        }
    }

    @Override
    public void updateUser(String name, String lastName) {
        super.updateUser(name, lastName);
    }
    
}
