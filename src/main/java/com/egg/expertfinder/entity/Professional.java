package com.egg.expertfinder.entity;

import com.egg.expertfinder.enumeration.RoleEnum;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Professional extends CustomUser{

    /*
    *   EL PROFESIONAL TIENE, EN LA LOCATION, COUNTRY = VISITANTE;
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String license;

    @Column(nullable = false)
    private String phone;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    
    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public Professional(String name, String lastName, String email,
            String description, String license, String phone) {
        super(name, lastName, email);
        this.role = RoleEnum.PRO;
        this.active = false;
        this.description = description;
        this.license = license;
        this.phone = phone;
    }
    
    public void updateProfessional(String name, String lastName, String description, String phone){
        super.updateUser(name, lastName);
        if (description != null){
            this.description = description;
        }
        if (phone != null){
            this.phone = phone;
        }
    }

    public void handleChangeRoleUser() {
        this.role = RoleEnum.USER;
    }

}

