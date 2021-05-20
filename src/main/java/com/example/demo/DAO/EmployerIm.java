package com.example.demo.DAO;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Employer;


public interface EmployerIm extends JpaRepository<Employer, String>{
	boolean existsById(String id);
	
	
	
	
	
	
	
}
