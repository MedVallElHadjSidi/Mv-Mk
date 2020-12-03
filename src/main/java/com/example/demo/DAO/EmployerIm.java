package com.example.demo.DAO;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Employer;


public interface EmployerIm extends JpaRepository<Employer, String>{
	boolean existsById(String id);
	
	
	
}
