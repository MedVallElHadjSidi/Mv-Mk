package com.example.demo.Metier;

import java.text.ParseException;
import java.util.Date;

import com.example.demo.entity.Taches;

public interface IMetier {
	public int NUmeroweekend(Date d1);
	public Taches TacheCompletJour(Taches t);
	public  Taches TACHESCOMPLETVendredi(Taches t1,Taches T2) throws ParseException;
	public  Taches TACHESCOMPLETSUPVendredi(Taches t1);
	public int  NombreHeureJours(int t1,int t2);
	public Taches MemeJours(Taches t1 ,Taches t2)throws ParseException;
	public Taches TachesCompletJoursSup(Taches t1 ,Taches t2) throws ParseException;
	public Taches vider(Taches t);
	public Taches HeureSupSecuriter(Taches t1,Taches t2);
	public Taches HeureSupSecuriterparticuliers(Taches t1,Taches t2);
	public int CalulePanier(Taches t);
	public Taches TacheWeekend(Taches t);
	public int  ConverteDate(Date t);
	public  Boolean IdentiqueDate(Date d1,Date d2);
	public  Boolean  IdentiqueWeekend(Date d1,Date d2);
	public Date DebutWeekend(Date date) throws ParseException;
	public Taches SecuriryDebut(Taches t1);
	public Taches SecuritySuplementaire(Taches t1,Taches t2) throws ParseException ;


}
