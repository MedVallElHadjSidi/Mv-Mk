package com.example.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Role;
    @Column(unique = true)
    private  String roleName;

    public Long getId_Role() {
        return id_Role;
    }

    public String getRoleName() {
        return roleName;
    }

    public Role(Long id_Role, String roleName) {
        this.id_Role = id_Role;
        this.roleName = roleName;
    }

    public Role() {
    }
}