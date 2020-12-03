package com.example.demo.DAO;

import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    public  Role  findByRoleName(String code);
}
