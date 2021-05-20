
  package com.example.demo.entity;
 
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import
  javax.persistence.OneToMany;
  
  @Entity 
  public class Employer {
  
  @Id
  private String id;
  @Column(nullable = false)
  private String Nom; 
  @Column(nullable = false)
  
  private String nni;
  @Column(name = "datRecrt")
  private Date  datRecrt;
  private String fonction;
  @OneToMany(mappedBy = "employer")
  private List<Mois>rendmentmois;
  
  private String categorie;
  
  @OneToMany(mappedBy = "employer")
  private Collection<Taches>tachCollection;

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getNni() {
	return nni;
}

public void setNni(String nni) {
	this.nni = nni;
}

public Date getDatRecrt() {
	return datRecrt;
}

public void setDatRecrt(Date datRecrt) {
	this.datRecrt = datRecrt;
}

public List<Mois> getRendmentmois() {
	return rendmentmois;
}

public void setRendmentmois(List<Mois> rendmentmois) {
	this.rendmentmois = rendmentmois;
}

public String getCategorie() {
	return categorie;
}

public void setCategorie(String categorie) {
	this.categorie = categorie;
}

public Collection<Taches> getTachCollection() {
	return tachCollection;
}

public void setTachCollection(Collection<Taches> tachCollection) {
	this.tachCollection = tachCollection;
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



public Employer(String id, String nom, String fonction) {
	super();
	this.id = id;
	Nom = nom;
	this.fonction = fonction;
}



public Employer() {
	this.tachCollection=null;
}

	  public Employer(String id) {
		  this.id = id;
	  }





  }
 