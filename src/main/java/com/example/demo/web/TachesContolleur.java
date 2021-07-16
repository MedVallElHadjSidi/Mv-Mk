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
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.DAO.EmployerIm;
import com.example.demo.DAO.MoisRepository;
import com.example.demo.DAO.ParametreRepository;
import com.example.demo.DAO.TachesIm;
import com.example.demo.Metier.IMetier;
import com.example.demo.entity.Employer;
import com.example.demo.entity.Mois;
import com.example.demo.entity.Parametre;
import com.example.demo.entity.Taches;
import com.example.demo.excel.ExcelFileExporter;
import com.example.demo.excel.ExcelFileRapportTotal;
import com.example.demo.model.ModelRapportTotal;
import com.example.demo.model.ModelRendementMois;


@Controller
public class TachesContolleur {
	
	@Autowired
	ExcelFileExporter exel;
	
	
	@Autowired
	ExcelFileRapportTotal exTotal;
	@Autowired
	private AccountService accountService;

	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private MoisRepository moisRepository;
	
	
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

	
	@RequestMapping(value = "/tachesUser",method = RequestMethod.POST)
	public String sommesSemaine(Model model,@RequestParam(name = "Duree1")String d1,
			@RequestParam(name = "Duree2")String d2,
			@RequestParam(name = "motcler")String matricule,@RequestParam(name = "page",defaultValue = "0")int page) {
		 String result="";
	
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


			return  "calculSemaineUser";

	


		

	}
	
	@RequestMapping(value = "/taches",method = RequestMethod.POST)
	public String sommes(Model model,@RequestParam(name = "Duree1")String d1,
			@RequestParam(name = "Duree2")String d2,
			@RequestParam(name = "motcler")String matricule,@RequestParam(name = "page",defaultValue = "0")int page,@AuthenticationPrincipal UserDetails currentUser) {
		 String result="";
		Utilisateur user=utilisateurRepository.findByUsername(currentUser.getUsername());
		System.out.println(user.getNom());
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
	
	@PostMapping("/GererRendement")
	public String VerfierForm(Model model, @Valid @RequestParam(name = "matricule") String matricule,@Valid @RequestParam(name = "note1")String note1,@RequestParam(name = "note2")String note2,@RequestParam(name = "note3")String note3,@RequestParam(name = "note4")String note4) {
		System.out.println("hello");
		System.out.println(note1);
		Date date=new Date();
		String mot="";
		double n1=Double.parseDouble(note1);
		double n2=Double.parseDouble(note2);
		double n3=Double.parseDouble(note3);
		double n4=Double.parseDouble(note4);
		double rs=imetier.CalculeRendementMois(n1, n2, n3, n4);
		Calendar calendar=Calendar.getInstance();
		  calendar.setTime(date);
		int annes=calendar.get(calendar.YEAR);
		 int mois=calendar.get(calendar.MONTH)+1;
		  
		  Mois m=moisRepository.findByMoisAndAnneAndEmployerId(mois, annes, matricule);
		  Employer em=employerim.findById(matricule).get();
		  if(m==null&& em!=null) {
			  Mois moi=new Mois();
			
			  moi.setAnne(annes);
			  moi.setMois(mois);
			  moi.setRendement(rs);
			  moi.setEmployer(em);
			Mois mt =  moisRepository.save(moi);
			  if(mt!=null) {
				  mot="rendement est genener avec success";
					model.addAttribute("mot", mot);
			  }
			  else {
				  mot="il ya un probleme";
					model.addAttribute("mot", mot);
			  }
			  
	  
			  
		  }
		  else {
			  System.out.println("on a rien faire");
			  mot="vous avez deja affecter un rendements";
				model.addAttribute("mot", mot);
		  }


		
		
		return "rendementmois";
		
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
		List<Employer>employers=employerim.findAll();
		List<String>list=new ArrayList<String>();

		for(Role r:user.getRoles()){
			if(r.getRoleName().equals("USER")){
				for(Employer e:employers) {
					list.add(e.getId());
					
					
				}
				model.addAttribute("matricules", list);
				


				return "pointage";

			}
			else if(r.getRoleName().equals("ADMIN")){
				return  "Jours";

			}

		}

		return "Jours";
	}

	@RequestMapping(value = "/chaquejours",method = RequestMethod.POST)
	public String chaqueJours(Model model,
							  @RequestParam(name = "matircule")String matricule,
							  @RequestParam(name="Dureedb")String db,
							  @RequestParam(name = "Dureef")String df,
							  @RequestParam(name = "Dureej")String date,
							  @RequestParam(name = "ferier")String ferier) {
		String valider=null;
		String failse=null;
		String comment="";
		String mydate=null;
		List<Employer>employers=employerim.findAll();
		System.out.println(ferier);
		List<String>list=new ArrayList<String>();
		for(Employer e:employers) {
			list.add(e.getId());
			
			
		}


		Taches tache=new Taches();
		model.addAttribute("matricules", list);
		
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
				int ht=imetier.NombreHeureJours(imetier.ConverteDate(dureeb),imetier.ConverteDate(dureef));
				tache.setTotal_Heure(ht);
				
				tache.setEmployer(em);
				Taches tv=null;
				Taches tach=tachesim.SommeJous(tache.getEmployer().getId());
				if(ferier.equals("ferie")) {
					int mv=imetier.NombreHeureJours(imetier.ConverteDate(dureeb),imetier.ConverteDate(dureef));
					tache.settHs(mv);
					tache.setNbreSup100(mv);
					
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
					
					
				
				
				
				else if(tache.getEmployer().getFonction().equals("securiter")) {
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

		return "pointage";
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

			if(taches.size()>0) {
			ByteArrayInputStream stream = exel.contactListToExcelFile(taches);
			IOUtils.copy(stream, response.getOutputStream());}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("oui");


	}
	@RequestMapping(value = "/downloadTota",method = RequestMethod.POST)
	public void FichierExcelTotal(HttpServletResponse response,@RequestParam(name = "d1") String d1, @RequestParam(name = "d2") String d2) {
	//	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		List<ModelRapportTotal>modelRapportTotals=new ArrayList<ModelRapportTotal>();
		Date date =null;
		Date date2=null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		List<Employer>em=employerim.findAll();
		try {
			 date=formatter.parse(d1);
			 date2=formatter.parse(d2);
		//	List<Taches>taches=tachesim.findAll();
		System.out.println(date);
			Mois ms =new Mois();
			

		//	ModelRapportTotal m=new ModelRapportTotal();
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(date2);
	
			for(Employer e:em) {
				 ms=moisRepository.findByMoisAndAnneAndEmployerId(calendar.get(calendar.MONTH)+1, calendar.get(calendar.YEAR),e.getId());
					//System.out.println("size e"+e.getTachCollection().size());
				if(e.getTachCollection()!=null) {
					
					if(ms!=null) {
						System.out.println("ms !=null");
				//	System.out.println(tachesim.SommeHN(date, date2, e.getId()));
					
					ModelRapportTotal modelRapportTotal=new ModelRapportTotal();
					modelRapportTotal.setMatricule(e.getId());
					modelRapportTotal.setNom(e.getNom());
					modelRapportTotal.setFonction(e.getFonction());
		
				modelRapportTotal.setSommeHn(tachesim.SommeHN(date, date2, e.getId()));
				modelRapportTotal.setSommeHs15(tachesim.SommeSup15(date, date2, e.getId()));
				modelRapportTotal.setSommeHs40(tachesim.SommeSup40(date, date2, e.getId()));
				modelRapportTotal.setSomme50(tachesim.SommeSup50(date, date2, e.getId()));
				modelRapportTotal.setSomme100(tachesim.SommeSup100(date, date2, e.getId()));
				modelRapportTotal.setSommeHt(tachesim.SommeHt(date, date2, e.getId()));
				modelRapportTotal.setSommepaniers(tachesim.SommePanier(date, date2,e.getId()));
				
					modelRapportTotal.setRendement(ms.getRendement());
				modelRapportTotals.add(modelRapportTotal);
					}
					else {
						System.out.println("ms==null");
						ModelRapportTotal modelRapportTotal=new ModelRapportTotal();
						modelRapportTotal.setMatricule(e.getId());
						modelRapportTotal.setNom(e.getNom());
						
						modelRapportTotal.setFonction(e.getFonction());
						System.out.println(e.getTachCollection().size());
						if(e.getTachCollection().size()>0) {
							
					System.out.println("ms null et taches not null"+"matricule"+e.getId()+"size"+e.getTachCollection().size());
			
					modelRapportTotal.setSommeHn(tachesim.SommeHN(date, date2, e.getId()));
					modelRapportTotal.setSommeHs15(tachesim.SommeSup15(date, date2, e.getId()));
					modelRapportTotal.setSommeHs40(tachesim.SommeSup40(date, date2, e.getId()));
					modelRapportTotal.setSomme50(tachesim.SommeSup50(date, date2, e.getId()));
					modelRapportTotal.setSomme100(tachesim.SommeSup100(date, date2, e.getId()));
					modelRapportTotal.setSommeHt(tachesim.SommeHt(date, date2, e.getId()));
					modelRapportTotal.setSommepaniers(tachesim.SommePanier(date, date2,e.getId()));
					
						modelRapportTotal.setRendement(0);
					modelRapportTotals.add(modelRapportTotal);
					}
						else {
							modelRapportTotal.setRendement(0);
							modelRapportTotals.add(modelRapportTotal);
							
						}
						
						}
		//		modelRapportTotal.setRendement((moisRepository.findByMoisAndAnneAndEmployerId(, calendar.get(calendar.YEAR), t.getEmployer().getId())).getRendement());
	
				
				
			}
				else {
					ModelRapportTotal modelRapportTotal=new ModelRapportTotal();
					modelRapportTotal.setMatricule(e.getId());
					modelRapportTotal.setNom(e.getNom());
					modelRapportTotal.setFonction(e.getFonction());
					if(ms!=null) {
					//	ModelRapportTotal modelRapportTotal=new ModelRapportTotal();
						
						modelRapportTotal.setRendement(ms.getRendement());
					//	modelRapportTotal.setSommeHn(tachesim.SommeHN(date, date2, e.getId()));
					//	modelRapportTotal.setSommeHs15(tachesim.SommeSup15(date, date2, e.getId()));
					//	modelRapportTotal.setSommeHs40(tachesim.SommeSup40(date, date2, e.getId()));
					//	modelRapportTotal.setSomme50(tachesim.SommeSup50(date, date2, e.getId()));
				//		modelRapportTotal.setSomme100(tachesim.SommeSup100(date, date2, e.getId()));
				//		modelRapportTotal.setSommeHt(tachesim.SommeHt(date, date2, e.getId()));
				//		modelRapportTotal.setSommepaniers(tachesim.SommePanier(dat, date2,e.getId()));
						modelRapportTotals.add(modelRapportTotal);
							
					}
				
					
		
						
			
				
						
					}
					
				
			
				
				
				
			}
	
			try {
				
					response.setContentType("application/octet-stream");
					response.setHeader("Content-Disposition", "attachment; filename=taches.xlsx");
					ByteArrayInputStream stream = exTotal.contactListToExcelFile(modelRapportTotals);
				IOUtils.copy(stream, response.getOutputStream());
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		
		
	
		
		
		
	}
	




	
	

		
		
		
	

		
	}
	


