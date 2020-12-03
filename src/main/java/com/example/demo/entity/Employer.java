
  package com.example.demo.entity;
 
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id; import
  javax.persistence.OneToMany;
  
  @Entity 
  public class Employer {
  
  @Id
 
  private String id;
  @Column(nullable = false)
  private String Nom; 
  @Column(nullable = false)
  private String fonction;
  
  @OneToMany(mappedBy = "employer")
  private Set<Taches>taches;

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getNom() {
	return Nom;
}

public void setNom(String nom) {
	Nom = nom;
}

public String getFonction() {
	return fonction;
}

public void setFonction(String fonction) {
	this.fonction = fonction;
}

public Set<Taches> getTaches() {
	return taches;
}

public void setTaches(Set<Taches> taches) {
	this.taches = taches;
}

public Employer(String id, String nom, String fonction) {
	super();
	this.id = id;
	Nom = nom;
	this.fonction = fonction;
}

public Employer(String id, String nom, String fonction, Set<Taches> taches) {
	super();
	this.id = id;
	Nom = nom;
	this.fonction = fonction;
	this.taches = taches;
}

public Employer() {
	super();
}

	  public Employer(String id) {
		  this.id = id;
	  }

	  @Override
public String toString() {
	return "Employer [id=" + id + ", Nom=" + Nom + ", fonction=" + fonction + ", taches=" + taches + "]";
}





  }
 