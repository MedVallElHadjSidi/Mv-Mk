package com.example.demo.DAO;


import com.example.demo.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,String> {
    public Utilisateur findByUsername(String code);
}
