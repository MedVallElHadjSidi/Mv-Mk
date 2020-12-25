package com.example.demo.web;

import java.io.ByteArrayInputStream;

import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.demo.DAO.UtilisateurRepository;
import com.example.demo.entity.Role;
import com.example.demo.entity.Utilisateur;
import com.example.demo.services.AccountService;
import com.sun.javafx.collections.MappingChange;
import org.apache.commons.compress.utils.IOUtils;
import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.DAO.EmployerIm;
import com.example.demo.DAO.TachesIm;
import com.example.demo.Metier.IMetier;
import com.example.demo.entity.Employer;
import com.example.demo.entity.Taches;
import com.example.demo.excel.ExcelFileExporter;


@Controller
public class TachesContolleur {
	@Autowired
	ExcelFileExporter exel;
	@Autowired
	private AccountService accountService;

	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	
	
	@Autowired
	TachesIm tachesim;
	@Autowired
	EmployerIm employerim;
	@Autowired
	IMetier imetier;
	@Autowired
	ServletContext context;
	Date dateDebut_Weekend=null;
	
	
	
	Employer em=new Employer();
	
	@RequestMapping(value = "/taches",method = RequestMethod.POST)
	public String sommes(Model model,@RequestParam(name = "Duree1")String d1,
			@RequestParam(name = "Duree2")String d2,
			@RequestParam(name = "motcler")String matricule,@RequestParam(name = "page",defaultValue = "0")int page) {
		Date dat=null;
		Date dt2=null;
		String m=matricule;
		List<Taches>taches=new ArrayList<>();
		if(employerim.existsById(matricule)) {
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			try {
				dat = formatter.parse(d1);
				 dt2=formatter.parse(d2);
				taches=tachesim.SommeSemaine(dat, dt2, matricule);
				int somme15=tachesim.SommeSup15(dat,dt2,matricule);
				int somme40=tachesim.SommeSup40(dat,dt2,matricule);
				model.addAttribute("sommehn",tachesim.SommeHN(dat,dt2,matricule));
                model.addAttribute("somme15",somme15);
                model.addAttribute("somme40",somme40);
                model.addAttribute("somme50",tachesim.SommeSup50(dat,dt2,matricule));
				model.addAttribute("somme100",tachesim.SommeSup100(dat,dt2,matricule));
				model.addAttribute("panier",tachesim.SommePanier(dat,dt2,matricule));
				model.addAttribute("sommeHt",tachesim.SommeHt(dat,dt2,matricule));
				model.addAttribute("d1",d1);
				model.addAttribute("d2",d2);
				model.addAttribute("m",matricule);
			


				
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addAttribute("taches", taches);
		
		}







		
		

		return "calcleSemaine";
	}
	@RequestMapping(value = "/liste",method = RequestMethod.GET)
	public String ListeEmployer(Model model,@RequestParam(name = "page",defaultValue = "0") int page){
		Page<Employer>employers=employerim.findAll(PageRequest.of(page,7));
		model.addAttribute("employers",employers.getContent());
		model.addAttribute("pages",new int[employers.getTotalPages()]);
		model.addAttribute("pageactuel",page);

		return "listeEmplyer";
	}

	@RequestMapping(value = "/delete",method = RequestMethod.GET)
	public String DeleteEmployer(@RequestParam(name = "id") String id,@RequestParam(name = "page") int page){
		employerim.deleteById(id);
		return "redirect:/liste?page="+page;
	}

	@RequestMapping(value = "/chercherbyid",method = RequestMethod.GET)
	public String ChercherByIdEmployer(Model model,@RequestParam(name = "id") String id){
		Employer employer=employerim.findById(id).get();
		String em_id=employer.getId();
		String em_nom=employer.getNom();
		String em_fonc=employer.getFonction();


		return "redirect:/updateEmployer?id="+em_id+"&nom="+em_nom+"&fonction="+em_fonc;
	}
	@RequestMapping(value = "/updateEmployer",method = RequestMethod.GET)
	public String updateEmployer(Model model,@RequestParam(name = "id") String id,@RequestParam(name = "nom")String nom,@RequestParam(name = "fonction")String fonction ){
		model.addAttribute("id",id);
		model.addAttribute("nom",nom);
		model.addAttribute("fonction",fonction);

		return "ajouterEmployer";
	}

	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public String updateEmployerFinale(Model model,@RequestParam(name = "id") String id,@RequestParam(name = "nom")String nom,@RequestParam(name = "fonction")String fonction ){
		employerim.save(new Employer(id,nom,fonction));
		return "redirect:/liste?page="+0;
	}

	
	
	@RequestMapping(value = "/AjouterEm" ,method = RequestMethod.POST)
	public String AjouterEmployer(@RequestParam(name = "matircule")String matricule,
			@RequestParam(name = "nom")String nom,
			@RequestParam(name = "fonction")String fonction,Model model) {
		String valider="";
		String nv="";
		String ma="";
		Employer em=new Employer(matricule,nom,fonction);
		if(!employerim.existsById(matricule)) {
			Employer emmodel= employerim.save(em);
			valider="images/valider.jpg";
			nv=null;
			ma="L'ajout de l'employer "+emmodel.getId()+" "+"avec success";
		}

		
		else {
			nv="images/dow22.png";
			valider=null;
			
			ma="desoler cet matricule existe deja!";
			
		}
		model.addAttribute("valider", valider);
		model.addAttribute("nv", nv);
		model.addAttribute("matricule", ma);

	
		
		
		
		
		return "AjouterEmployer";	
	}
	@RequestMapping(value = "/consulter" ,method = RequestMethod.GET)
	public String ConsulterTaches(Model model,@RequestParam(name = "MATRICULE",defaultValue = "") String matricule,@RequestParam(name = "page",defaultValue = "0")int page) {
		Page<Taches>page2=(Page<Taches>) tachesim.Chercherbymotcler("%"+matricule+"%", PageRequest.of(page,7));
		
		model.addAttribute("pagetaches",page2.getContent());
		model.addAttribute("pagecourant",page);
		model.addAttribute("pages", new int [page2.getTotalPages()]);
		
		
		
		
		
		return "consulterTaches";
	}


	@RequestMapping(value = "/consulterByMot" ,method = RequestMethod.GET)
	public String consulterByMot(Model model,@RequestParam(name = "matricule",defaultValue = "") String matricule,@RequestParam(name = "page",defaultValue = "0")int page) {
		Page<Taches>page2=(Page<Taches>) tachesim.Chercherbymotcler("%"+matricule+"%", PageRequest.of(page,7));

		model.addAttribute("pagetaches",page2.getContent());
		model.addAttribute("pagecourant",page);
		model.addAttribute("pages", new int [page2.getTotalPages()]);
		model.addAttribute("motcler",matricule);





		return "Jours";
	}
	

	@RequestMapping(value = "/")
	public String quoditienne(Model model,   @AuthenticationPrincipal UserDetails currentUser) {
		Utilisateur user=utilisateurRepository.findByUsername(currentUser.getUsername());
		System.out.println(user.getNom());

		for(Role r:user.getRoles()){
			if(r.getRoleName().equals("USER")){


				return "Jours";

			}
			else if(r.getRoleName().equals("ADMIN")){
				return  "tem";

			}

		}

		return "Jours";
	}

	@RequestMapping(value = "/chaquejours",method = RequestMethod.POST)
	public String chaqueJours(Model model,
							  @RequestParam(name = "matircule")String matricule,
							  @RequestParam(name="Dureedb")String db,
							  @RequestParam(name = "Dureef")String df,
							  @RequestParam(name = "Dureej")String date) {
		String valider=null;
		String failse=null;
		String comment="";
		String mydate=null;


		Taches tache=new Taches();
		if(employerim.existsById(matricule)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			//SimpleDateFormat formatter7 = new SimpleDateFormat("MM-dd-yyyy");
			DateFormat formatter2 = new SimpleDateFormat("HH:mm");
			DateFormat formatter3 = new SimpleDateFormat("HH:mm");
			try {
				Date dureeb=formatter3.parse(db);
				Date dureef=formatter2.parse(df);
				Date journee=formatter.parse(date);
				tache.setDateday(journee);
				tache.setDatedebut(dureeb);
				tache.setDatefint(dureef);
				mydate=formatter.format(journee);
				System.out.println(mydate);
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(tache.getDateday());
				
				System.out.println("Jour"+calendar.MONDAY);
				  
				
				

				//System.out.println("DayOfMonth"+calendar.DAY_OF_MONTH);

				em=(Employer)employerim.findById(matricule).get();
				tache.setPanier(imetier.CalulePanier(tache));
				tache.setEmployer(em);
				Taches tv=null;
				Taches tach=tachesim.SommeJous(tache.getEmployer().getId());
				
				if(tache.getEmployer().getFonction().equals("Securiter")) {
					System.out.println("securiter");
					if(tach==null) {
						Taches t=tachesim.save(imetier.SecuriryDebut(tache));
						if(t!=null) {
							valider="images/valider.jpg";
							failse=null;
							comment="L'ajout  de la tache de l'employer "+tache.getEmployer().getId()+" "+"avec success"+""+"Date"+":"+mydate;
									
							
						}
						else {
							
							failse="images/dow22.png";
							valider=null;
							comment="desoler  la tache de l'employer "+tache.getEmployer().getId()+" "+"est echouer"+""+"Date"+":"+mydate;;
									
						}
						
					}
					else {
						
						System.out.println("numero semaine tache"+imetier.NUmeroweekend(tache.getDateday()));
					System.out.println("debut wekend tache"+imetier.DebutWeekend(tache.getDateday()));
				
						if(imetier.IdentiqueWeekend(tache.getDateday(),tach.getDateday())) {
							System.out.println("m w");
						Taches tr=tachesim.save(imetier.SecuritySuplementaire(tache, tach));
						if(tr!=null) {
							valider="images/valider.jpg";
							failse=null;
							comment="L'ajout  de la tache de l'employer "+tache.getEmployer().getId()+" "+"avec success"+""+"Date"+":"+mydate;;
									
							
						}
						else {
							
							failse="images/dow22.png";
							valider=null;
							comment="desoler  la tache de l'employer "+tache.getEmployer().getId()+" "+"est echouer"+""+"Date"+":"+mydate;;
									
						}
							
						}
						else {
							
							Taches ts=tachesim.save(imetier.SecuriryDebut(tache));
							if(ts!=null) {
								valider="images/valider.jpg";
								failse=null;
								comment="L'ajout  de la tache de l'employer "+tache.getEmployer().getId()+" "+"avec success"+""+"Date"+":"+mydate;;
										
								
							}
							else {
								
								failse="images/dow22.png";
								valider=null;
								comment="desoler  la tache de l'employer "+tache.getEmployer().getId()+" "+"est echouer"+""+"Date"+":"+mydate;;
										
							}
						}
						
						
						
					}
					
					
					
				}
				
				else {
					System.out.println("n'estpas securiter");
				


				if(tach==null){
					System.out.println("tach null");
					if (tache.getDateday().getDay()==5){
						System.out.println("tach null ms tache Vendredi ");
						Taches taches=tachesim.save(imetier.TACHESCOMPLETSUPVendredi(tache));
						if(taches!=null) {
							valider="images/valider.jpg";
							failse=null;
							comment="L'ajout  de la tache de l'employer "+tache.getEmployer().getId()+" "+"avec success"+""+"Date"+":"+mydate;;
									
							
						}
						else {
							
							failse="images/dow22.png";
							valider=null;
							comment="desoler  la tache de l'employer "+tache.getEmployer().getId()+" "+"est echouer"+""+"Date"+":"+mydate;
									
						}

					}
					else if (tache.getDateday().getDay()==6|| tache.getDateday().getDay()==0){
						System.out.println("tach null ms tache wekend ");
						Taches taches=tachesim.save(imetier.TacheWeekend(tache));
						if(taches!=null) {
							valider="images/valider.jpg";
							failse=null;
							comment="L'ajout  de la tache de l'employer "+tache.getEmployer().getId()+" "+"avec success"+""+"Date"+":"+mydate;
									
							
						}
						else {
							
							failse="images/dow22.png";
							valider=null;
							comment="desoler  la tache de l'employer "+tache.getEmployer().getId()+" "+"est echouer"+""+"Date"+":"+mydate;
									
						}

					}
					else{
						System.out.println("tach null ms tache un jour du semanine ");
						Taches taches=tachesim.save(imetier.TacheCompletJour(tache));
						if(taches!=null) {
							valider="images/valider.jpg";
							failse=null;
							comment="L'ajout  de la tache de l'employer "+tache.getEmployer().getId()+" "+"avec success"+""+"Date"+":"+mydate;
									
							
						}
						else {
							
							failse="images/dow22.png";
							valider=null;
							comment="desoler  la tache de l'employer "+tache.getEmployer().getId()+" "+"est echouer"+""+"Date"+":"+mydate;
									
						}

					}

				}

				else {
					System.out.println(tach.getId());
					System.out.println("Numero wekend tache"+ imetier.IdentiqueWeekend(tache.getDateday(), tach.getDateday()));
					System.out.println("numero semamine"+imetier.NUmeroweekend(tache.getDateday()));
				

					System.out.println("tach  ! null  ");
					if (tache.getDateday().getDay()==6||tache.getDateday().getDay()==0){
						System.out.println("tach !null ms tache weekend");
						Taches taches=tachesim.save(imetier.TacheWeekend(tache));
						if(taches!=null) {
							valider="images/valider.jpg";
							failse=null;
							comment="L'ajout  de la tache de l'employer "+tache.getEmployer().getId()+" "+"avec success"+""+"Date"+":"+mydate;
									
							
						}
						else {
							
							failse="images/dow22.png";
							valider=null;
							comment="desoler  la tache de l'employer "+tache.getEmployer().getId()+" "+"est echouer"+""+"Date"+":"+mydate;
									
						}


					}
					else{
						System.out.println("tach !null  tache n'est pas wekend ");
						
						if (imetier.IdentiqueWeekend(tache.getDateday(),tach.getDateday())){
							System.out.println("tach !null  tache n'est pas wekend  et tache et tach meme wekend");

							if (!imetier.IdentiqueDate(tache.getDateday(),tach.getDateday())){
								System.out.println("tach !null  tache n'est pas wekend  et tache et tach meme wekend ms jour #");

								if (tache.getDateday().getDay()==5){
									System.out.println("tach !null  tache n'est pas wekend  et tache et tach meme wekend +vndr");

									Taches taches=tachesim.save(imetier.TACHESCOMPLETVendredi(tache,tach));
									if(taches!=null) {
										valider="images/valider.jpg";
										failse=null;
										comment="L'ajout  de la tache de l'employer "+tache.getEmployer().getId()+" "+"avec success"+""+"Date"+":"+mydate;
												
										
									}
									else {
										
										failse="images/dow22.png";
										valider=null;
										comment="desoler  la tache de l'employer "+tache.getEmployer().getId()+" "+"est echouer"+""+"Date"+":"+mydate;
												
									}

								}
								else {
									System.out.println("tach !null  tache n'est pas wekend  et tache et tach meme  wekend  m #j");

									Taches taches=tachesim.save(imetier.TachesCompletJoursSup(tache,tach));
									if(taches!=null) {
										valider="images/valider.jpg";
										failse=null;
										comment="L'ajout  de la tache de l'employer "+tache.getEmployer().getId()+" "+"avec success"+""+"Date"+":"+mydate;
												
										
									}
									else {
										
										failse="images/dow22.png";
										valider=null;
										comment="desoler  la tache de l'employer "+tache.getEmployer().getId()+" "+"est echouer"+""+"Date"+":"+mydate;
												
									}

								}


							}
							else{
								if (tache.getDateday().getDay()==5){
									Taches taches=tachesim.save(imetier.MemeJours(tache,tach));
									if(taches!=null) {
										valider="images/valider.jpg";
										failse=null;
										comment="L'ajout  de la tache de l'employer "+tache.getEmployer().getId()+" "+"avec success"+""+"Date"+":"+mydate;
												
										
									}
									else {
										
										failse="images/dow22.png";
										valider=null;
										comment="desoler  la tache de l'employer "+tache.getEmployer().getId()+" "+"est echouer"+""+"Date"+":"+mydate;
												
									}

								}
								else {
									Taches taches=tachesim.save(imetier.MemeJours(tache,tach));
									if(taches!=null) {
										valider="images/valider.jpg";
										failse=null;
										comment="L'ajout  de la tache de l'employer "+tache.getEmployer().getId()+" "+"avec success"+""+"Date"+":"+mydate;
												
										
									}
									else {
										
										failse="images/dow22.png";
										valider=null;
										comment="desoler  la tache de l'employer "+tache.getEmployer().getId()+" "+"est echouer"+""+"Date"+":"+mydate;
												
									}

								}

							}



						}
						else{
							System.out.println("new wekend");
							if (tache.getDateday().getDay()==1){
								System.out.println("on 'est la lundi ");
								Taches taches=tachesim.save(imetier.TacheCompletJour(tache));
								if(taches!=null) {
									valider="images/valider.jpg";
									failse=null;
									comment="L'ajout  de la tache de l'employer "+tache.getEmployer().getId()+" "+"avec success"+""+"Date"+":"+mydate;
											
									
								}
								else {
									
									failse="images/dow22.png";
									valider=null;
									comment="desoler  la tache de l'employer "+tache.getEmployer().getId()+" "+"est echouer"+""+"Date"+":"+mydate;
											
								}

							}

						else if (tache.getDateday().getDay()==5){
								Taches taches=tachesim.save(imetier.TACHESCOMPLETSUPVendredi(tache));
								if(taches!=null) {
									valider="images/valider.jpg";
									failse=null;
									comment="L'ajout  de la tache de l'employer "+tache.getEmployer().getId()+" "+"avec success"+""+"Date"+":"+mydate;
											
									
								}
								else {
									
									failse="images/dow22.png";
									valider=null;
									comment="desoler  la tache de l'employer "+tache.getEmployer().getId()+" "+"est echouer"+""+"Date"+":"+mydate;
											
								}

							}
							else{
								System.out.println("on 'est la ");
								Taches taches=tachesim.save(imetier.TacheCompletJour(tache));
								if(taches!=null) {
									valider="images/valider.jpg";
									failse=null;
									comment="L'ajout  de la tache de l'employer "+tache.getEmployer().getId()+" "+"avec success"+""+"Date"+":"+mydate;
											
									
								}
								else {
									
									failse="images/dow22.png";
									valider=null;
									comment="desoler  la tache de l'employer "+tache.getEmployer().getId()+" "+"est echouer"+""+"Date"+":"+mydate;
											
								}
							}



						}




					}

				}






               /*Calendar first=(Calendar) calendar1.clone();
				first.add(calendar1.DAY_OF_WEEK,first.getFirstDayOfWeek()-first.get(Calendar.DAY_OF_WEEK));
				System.out.println("first"+first);
				System.out.println("resultat"+formatter5.format(first.getTime()));*/





				}
				
				model.addAttribute("valide", valider);
				model.addAttribute("failse", failse);
				model.addAttribute("comment", comment);
		


			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
		else {

			failse="images/dow22.png";
			model.addAttribute("etat",failse);
			comment="Desoler cet employer n'existe pas!"+" "+"verfier matricule";
			model.addAttribute("comment",comment);
		}

		return "Jours";
	}
/*
	@RequestMapping(value = "/chaquejours",method = RequestMethod.POST)
	public String chaqueJours(Model model,
			@RequestParam(name = "matircule")String matricule,
			@RequestParam(name="Dureedb")String db,
			@RequestParam(name = "Dureef")String df,
			@RequestParam(name = "Dureej")String date) {
		String valider="";
		String failse="";
		String comment="";
		
		
		Taches tache=new Taches();
		if(employerim.existsById(matricule)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat formatter2 = new SimpleDateFormat("HH:mm");
			DateFormat formatter3 = new SimpleDateFormat("HH:mm");
			try {
				Date dureeb=formatter3.parse(db);
				Date dureef=formatter2.parse(df);
				Date journee=formatter.parse(date);
				tache.setDateday(journee);
				tache.setDatedebut(dureeb);
				tache.setDatefint(dureef);
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(journee);
				System.out.println("calender"+calendar);
				//System.out.println("DayOfMonth"+calendar.DAY_OF_MONTH);

				em=(Employer)employerim.findById(matricule).get();
				tache.setPanier(imetier.CalulePanier(tache));
				tache.setEmployer(em);
				Taches tv=null;
				Taches tach=tachesim.SommeJous(tache.getEmployer().getId());
             Date dateDebut_Weekend=tachesim.DebutSemaine(tache.getDateday());
              System.out.println(dateDebut_Weekend);
              System.out.println(tache.getDateday().getDay());

				if(tache.getDateday().getDay()==6||tache.getDateday().getDay()==0){
					System.out.println("wekend");
					tachesim.save(imetier.TacheWeekend(tache));
				}

           else  if(tach==null) {
              	if (tache.getEmployer().getFonction().equals("Securiter")){

					System.out.println("ona entrer ici ");
              	tv=	tachesim.save(imetier.HeureSupSecuriter(tache,null));
              	if (tv==null){
              		System.out.println("ereeur");
				}
              	else {
					System.out.println("yes");

				}
				}
              	else{

					tv=imetier.TacheCompletJour(tache);
					if(tv!=null) {
						tachesim.save(tv);
						valider="images/valider.jpg";
						comment="vous vient d'enregistrer la tache de l'employer"+" : "+tv.getEmployer().getId();

						model.addAttribute("etat",valider);
						model.addAttribute("comment",comment);

					}
					else {
						failse="images/dow22.png";
						model.addAttribute("etat",failse);
						comment="on a rencontrer une probleme concernant tache de l'employer"+" : "+tache.getEmployer().getId();
						model.addAttribute("comment",comment);
					}


				}


              }
              else if(tach!=null&&dateDebut_Weekend==null) {
              	System.out.println("debut wekkend");
				  if (tache.getEmployer().getFonction().equals("Securiter")){
					  tachesim.save(imetier.HeureSupSecuriter(tache,tach));
				  }

            	  
              }
              
              else {


					if(tache.getDateday().getDay()==tach.getDateday().getDay()) {
						if (tache.getEmployer().getFonction().equals("Securiter")){
							tachesim.save(imetier.HeureSupSecuriter(tache,tach));
						}
						else{

						}

					}
					else {
						if (tache.getEmployer().getFonction().equals("Securiter")){
							tachesim.save(imetier.HeureSupSecuriter(tache,tach));
						}
						else
						{
							Taches tachs=tachesim.save(imetier.TachesCompletJoursSup(tache,tach));
							if(tachs!=null){
							valider="images/valider.jpg";
							comment="vous vient d'enregistrer la tache de l'employer"+" : "+tachs.getEmployer().getId();

							model.addAttribute("etat",valider);
							model.addAttribute("comment",comment);

						}
					else {
							failse="images/dow22.png";
							model.addAttribute("etat",failse);
							comment="on a rencontrer une probleme concernant tache de l'employer"+" : "+tache.getEmployer().getId();
							model.addAttribute("comment",comment);
						}


						}


					}




              }

			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
		else {
			
			failse="images/dow22.png";
				model.addAttribute("etat",failse);
				comment="Desoler cet employer n'existe pas!"+" "+"verfier matricule";
				model.addAttribute("comment",comment);
		}
		
		return "Jours";
	}
	
*/
	@RequestMapping(value = "/download",method = RequestMethod.GET)
    public void downloadExcel(HttpServletResponse response, @RequestParam(name = "d1")String d1,@RequestParam(name = "d2")String d2,@RequestParam(name = "m")String m) throws IOException {
	

		Date dat1=null;
		Date dat2=null;


		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			 dat1=formatter.parse(d1);
			  dat2=formatter.parse(d2);





			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=taches.xlsx");
			List<Taches>taches= tachesim.SommeSemaine(dat1,dat2,m);
			ByteArrayInputStream stream = exel.contactListToExcelFile(taches);
			IOUtils.copy(stream, response.getOutputStream());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("oui");


	}




	
	

		
		
		
	

		
	}
	


