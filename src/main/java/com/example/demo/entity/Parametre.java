package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Parametre {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int heure_Normale;
	private int heure_sup15;
	private int heure_sup40;
	private int [] weekends;
//	private String weekend_1;
//	private String weekend_2;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getHeure_Normale() {
		return heure_Normale;
	}
	public void setHeure_Normale(int heure_Normale) {
		this.heure_Normale = heure_Normale;
	}
	public int getHeure_sup15() {
		return heure_sup15;
	}
	public void setHeure_sup15(int heure_sup15) {
		this.heure_sup15 = heure_sup15;
	}
	public int getHeure_sup40() {
		return heure_sup40;
	}
	public void setHeure_sup40(int heure_sup40) {
		this.heure_sup40 = heure_sup40;
	}
	
	

	public int[] getWeekends() {
		return weekends;
	}
	public void setWeekends(int[] weekends) {
		this.weekends = weekends;
	}
	public Parametre(int heure_Normale, int heure_sup15, int heure_sup40, int[] weekends) {
		super();
		this.heure_Normale = heure_Normale;
		this.heure_sup15 = heure_sup15;
		this.heure_sup40 = heure_sup40;
		this.weekends = weekends;
	}
	public Parametre(Long id, int heure_Normale, int heure_sup15, int heure_sup40, int[] weekends) {
		super();
		this.id = id;
		this.heure_Normale = heure_Normale;
		this.heure_sup15 = heure_sup15;
		this.heure_sup40 = heure_sup40;
		this.weekends = weekends;
	}
	public Parametre() {
		super();
	}
	
	
	
	
	
	

}
