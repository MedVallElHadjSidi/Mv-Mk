package com.example.demo.services;



import com.example.demo.DAO.RoleRepository;
import com.example.demo.DAO.UtilisateurRepository;
import com.example.demo.entity.Role;
import com.example.demo.entity.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImp  implements  AccountService{
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Utilisateur findUserByUser(String codeUser) {

        return utilisateurRepository.findByUsername(codeUser);
    }

    @Override
    public Role addRole(Role roles) {
        return roleRepository.save(roles);
    }


    @Override
    public Utilisateur addUser(Utilisateur user) {
        String passwdBrpt=bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(passwdBrpt);
        return utilisateurRepository.save(user);
    }
    @Override
    public Utilisateur AddRoles(String username, String rolename) {
        Role role=roleRepository.findByRoleName(rolename);
        Utilisateur utilisateur=utilisateurRepository.findByUsername(username);
        utilisateur.getRoles().add(role);

        return utilisateur;


    }



}
