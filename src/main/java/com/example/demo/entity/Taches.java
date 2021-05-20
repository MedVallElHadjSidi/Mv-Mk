
  package com.example.demo.entity;
  
   import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne; import
  javax.persistence.Temporal; import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

  @Entity 
  public class Taches {
  
  @Id 
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Temporal(TemporalType.DATE) 
  @Column(nullable = false)
  
  private Date dateday;
  @Column(nullable = false)
  @Temporal(TemporalType.TIME) 
  private Date datedebut;
  @Column(nullable = false)
  @Temporal(TemporalType.TIME) 
  private Date datefint;
  
  @ManyToOne
  @JoinColumn(name = "Matricule_Em")
  private Employer employer;
  @Column(name = "total_Heure")
  private int total_Heure;
  
  @Column(name = "NbreNormale")
  private int nbreHtNormale;
  @Column(name = "NbreHS15")
  private int nbreSup15;
  @Column(name = "NbreHS40")
  private int nbreSup40;
  @Column(name = "NbreHS50")
  private int nbreSup50;
  

  @Column(name = "NbreHS100")
  private int nbreSup100;
  @Column(name = "Panier")
  private int panier;
  private  int tHs;

      public int gettHs() {
          return tHs;
      }

      public void settHs(int tHs) {
          this.tHs = tHs;
      }

      public void setPanier(int panier) {
          this.panier = panier;
      }

      public int getPanier() {
          return panier;
      }

      public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public Date getDateday() {
	return dateday;
}
public void setDateday(Date dateday) {
	this.dateday = dateday;
}
public Date getDatedebut() {
	return datedebut;
}
public void setDatedebut(Date datedebut) {
	this.datedebut = datedebut;
}
public Date getDatefint() {
	return datefint;
}
public void setDatefint(Date datefint) {
	this.datefint = datefint;
}
public Employer getEmployer() {
	return employer;
}
public void setEmployer(Employer employer) {
	this.employer = employer;
}
public int getTotal_Heure() {
	return total_Heure;
}
public void setTotal_Heure(int total_Heure) {
	this.total_Heure = total_Heure;
}
public int getNbreHtNormale() {
	return nbreHtNormale;
}
public void setNbreHtNormale(int nbreHtNormale) {
	this.nbreHtNormale = nbreHtNormale;
}
public int getNbreSup15() {
	return nbreSup15;
}
public void setNbreSup15(int nbreSup15) {
	this.nbreSup15 = nbreSup15;
}
public int getNbreSup40() {
	return nbreSup40;
}
public void setNbreSup40(int nbreSup40) {
	this.nbreSup40 = nbreSup40;
}
public int getNbreSup50() {
	return nbreSup50;
}
public void setNbreSup50(int nbreSup50) {
	this.nbreSup50 = nbreSup50;
}
public int getNbreSup100() {
	return nbreSup100;
}
public void setNbreSup100(int nbreSup100) {
	this.nbreSup100 = nbreSup100;
}
public Taches(Date dateday, Date datedebut, Date datefint, Employer employer) {
	super();
	this.dateday = dateday;
	this.datedebut = datedebut;
	this.datefint = datefint;
	this.employer = employer;
}
public Taches() {
	super();
}

@Override
public String toString() {
	// TODO Auto-generated method stub
	return super.toString();
}
  
  
  
  
  
  }
  


  
  





  
  
  
  
  
  
  
  
  
 