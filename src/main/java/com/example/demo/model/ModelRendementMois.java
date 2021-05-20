package com.example.demo.model;

public class ModelRendementMois {
	
	private String matricule;
	private String  note1;
	private  String note2;
	private String note3;
	private String note4;
	
	
	
	
	public String getMatricule() {
		return matricule;
	}
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}
	public String getNote1() {
		return note1;
	}
	public void setNote1(String note1) {
		this.note1 = note1;
	}
	public String getNote2() {
		return note2;
	}
	public void setNote2(String note2) {
		this.note2 = note2;
	}
	public String getNote3() {
		return note3;
	}
	public void setNote3(String note3) {
		this.note3 = note3;
	}
	public String getNote4() {
		return note4;
	}
	public void setNote4(String note4) {
		this.note4 = note4;
	}
	public ModelRendementMois(String matricule, String note1, String note2, String note3, String note4) {
		super();
		this.matricule = matricule;
		this.note1 = note1;
		this.note2 = note2;
		this.note3 = note3;
		this.note4 = note4;
	}
	public ModelRendementMois() {
		super();
	}
	


}
