package com.example.demo.model;

public class ModelRapportTotal {
	
	private String matricule;
	private String nom;
	private String fonction;
	private int sommeHn;
	private int sommeHs15;
	private int sommeHs40;
	private int somme50;
	private int somme100;
	private int sommeHt;
	private int sommepaniers;
	private double rendement;
	public String getMatricule() {
		return matricule;
	}
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getFonction() {
		return fonction;
	}
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}
	public int getSommeHn() {
		return sommeHn;
	}
	public void setSommeHn(int sommeHn) {
		this.sommeHn = sommeHn;
	}
	public int getSommeHs15() {
		return sommeHs15;
	}
	public void setSommeHs15(int sommeHs15) {
		this.sommeHs15 = sommeHs15;
	}
	public int getSommeHs40() {
		return sommeHs40;
	}
	public void setSommeHs40(int sommeHs40) {
		this.sommeHs40 = sommeHs40;
	}
	public int getSomme50() {
		return somme50;
	}
	public void setSomme50(int somme50) {
		this.somme50 = somme50;
	}
	public int getSomme100() {
		return somme100;
	}
	public void setSomme100(int somme100) {
		this.somme100 = somme100;
	}
	public int getSommeHt() {
		return sommeHt;
	}
	public void setSommeHt(int sommeHt) {
		this.sommeHt = sommeHt;
	}
	public int getSommepaniers() {
		return sommepaniers;
	}
	public void setSommepaniers(int sommepaniers) {
		this.sommepaniers = sommepaniers;
	}
	public double getRendement() {
		return rendement;
	}
	public void setRendement(double rendement) {
		this.rendement = rendement;
	}
	public ModelRapportTotal(String matricule, String nom, String fonction, int sommeHn, int sommeHs15, int sommeHs40,
			int somme50, int somme100, int sommeHt, int sommepaniers, double rendement) {
		super();
		this.matricule = matricule;
		this.nom = nom;
		this.fonction = fonction;
		this.sommeHn = sommeHn;
		this.sommeHs15 = sommeHs15;
		this.sommeHs40 = sommeHs40;
		this.somme50 = somme50;
		this.somme100 = somme100;
		this.sommeHt = sommeHt;
		this.sommepaniers = sommepaniers;
		this.rendement = rendement;
	}
	public ModelRapportTotal() {
		super();
	}
	
	
	
	

}
