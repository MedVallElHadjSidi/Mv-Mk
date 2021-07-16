package com.example.demo.Metier;
  
  import java.text.ParseException;
  import java.text.SimpleDateFormat;
  import java.util.Calendar;
  import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.stereotype.Service;
  import  org.springframework.transaction.annotation.Transactional;

import com.example.demo.DAO.ParametreRepository;
import com.example.demo.DAO.TachesIm;
import com.example.demo.entity.Parametre;
import com.example.demo.entity.Taches;
  
  @Service
  
  @Transactional 
  public class MetierImp implements IMetier{
		@Autowired
		private TachesIm tachesim;
		
		

	@Override
	public Taches TacheCompletJour(Taches t) {
		// TODO Auto-generated method stub
		int n = NombreHeureJours(ConverteDate(t.getDatedebut()), ConverteDate(t.getDatefint()));
		t.setTotal_Heure(n);
		if(n<=9){
			t.setNbreHtNormale(n);
		
		}
		else {
		
			t.settHs(n-9);
			if(t.getDatedebut().getHours()>=5){
				if(t.getDatefint().getHours()>t.getDatedebut().getHours()){
					if(t.getDatefint().getHours()<23){

						t.setNbreHtNormale(9);
						t.setNbreSup15(n-9);


					}
					else{

						t.setNbreHtNormale(9);
						t.setNbreSup50(1);
						t.setNbreSup15(n-9-1);

					}

				}
				else{
					if(t.getDatefint().getHours()<=5){
						t.setNbreHtNormale(9);
						t.setNbreSup50(NombreHeureJours(79200,ConverteDate(t.getDatefint())));
						t.setNbreSup15(n-9-t.getNbreSup50());
					}
					else{
						t.setNbreHtNormale(9);
						t.setNbreSup50(NombreHeureJours(79200,18000));
						t.setNbreSup15(n-9-t.getNbreSup50());

					}




				}




			}
			else{
				if(t.getDatefint().getHours()<=5){
					t.setNbreHtNormale(9);
					t.setNbreSup50(NombreHeureJours(ConverteDate(t.getDatedebut()),18000)+NombreHeureJours(79200,ConverteDate(t.getDatefint())));
					t.setNbreSup15(n-9-t.getNbreSup50());


				}
				else{
					if(t.getDatefint().getHours()==23){
						t.setNbreHtNormale(9);
						t.setNbreSup50(NombreHeureJours(ConverteDate(t.getDatedebut()),18000)+NombreHeureJours(79200,ConverteDate(t.getDatefint())));
						t.setNbreSup15(n-9-t.getNbreSup50());
					}
					else{
						t.setNbreHtNormale(9);
						t.setNbreSup50(NombreHeureJours(ConverteDate(t.getDatedebut()),18000));
						t.setNbreSup15(n-9-t.getNbreSup50());

					}

				}

			}




		}




		return  t;
	}

	  @Override
	  public Taches TACHESCOMPLETVendredi(Taches t1,Taches t2) throws ParseException {
		  SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd");

		  int n = NombreHeureJours(ConverteDate(t1.getDatedebut()), ConverteDate(t1.getDatefint()));
		  t1.setTotal_Heure(n);
		 // Date debutwekend = tachesim.DebutSemaine(t1.getDateday());
		  String d1=formatter6.format(DebutWeekend(t1.getDateday()));
		  Date debutwekend=formatter6.parse(d1);
		  int regarde=tachesim.SommeSup15(debutwekend,t1.getDateday(),t1.getEmployer().getId());
		  int regarde2=tachesim.SommeSup40(debutwekend,t1.getDateday(),t1.getEmployer().getId());
		  System.out.println("Nombre15"+":"+regarde);
		  System.out.println("Nombre40"+":"+regarde2);
		  if(n<=4){
			  t1.setNbreHtNormale(n);
			


		  }


		  else {


			  t1.settHs(n - 4);
			 
			  if (!IdentiqueWeekend(t1.getDateday(), debutwekend)) {

				  if (t1.getDatedebut().getHours() >= 5) {
					  if (t1.getDatefint().getHours() > t1.getDatedebut().getHours()) {
						  if (t1.getDatefint().getHours() < 23) {

							  t1.setNbreHtNormale(4);
							  
							  
							  if (regarde < 8) {
								  t1.setNbreSup15(n - 4);
									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(n - 4);

									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n - 4);
								 }


						  } else {

							  t1.setNbreHtNormale(4);
							  t1.setNbreSup50(1);
							
							  
							  
							  if (regarde < 8) {
								  t1.setNbreSup15(n - 4 - 1);
									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									  t1.setNbreSup40(n - 4 - 1);

									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n - 4);
								 }
							  
							  
							  
							 

						  }

					  } else {
						  if (t1.getDatefint().getHours() <= 5) {
							  t1.setNbreHtNormale(4);
							  t1.setNbreSup50(NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
							  
							  if (regarde < 8) {
								  t1.setNbreSup15(n - 4 - t1.getNbreSup50());
									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(n - 4 - t1.getNbreSup50());

									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n - 4);
								 }
							  
							  
						  } else {
							  t1.setNbreHtNormale(4);
							  t1.setNbreSup50(NombreHeureJours(79200, 18000));
							 
							  if (regarde < 8) {
								  t1.setNbreSup15(n - 4 - t1.getNbreSup50());
									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(n - 4 - t1.getNbreSup50());

									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n - 4);
								 }

						  }


					  }


				  } else {
					  if (t1.getDatefint().getHours() <= 5) {
						  t1.setNbreHtNormale(4);
						  t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000) + NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
					
						  
						  if (regarde < 8) {
							  t1.setNbreSup15(n - 4 - t1.getNbreSup50());
								 if (regarde + t1.getNbreSup15() > 8) {
									 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
									 t1.setNbreSup15(8 - regarde);
									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }

								 }

							 } else if (regarde == 8 && regarde2 < 6) {
								 t1.setNbreSup40(n - 4 - t1.getNbreSup50());

								 if (regarde2 + t1.getNbreSup40() > 6) {
									 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
									 t1.setNbreSup40(6 - regarde2);
								 }


							 } else {

								 t1.setNbreSup50(n - 4);
							 }


					  } else {
						  if (t1.getDatefint().getHours() == 23) {
							  t1.setNbreHtNormale(4);
							  t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000) + NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
							
							  if (regarde < 8) {
								  t1.setNbreSup15(n - 4 - t1.getNbreSup50());
									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(n - 4 - t1.getNbreSup50());

									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n - 4);
								 }
						  } else {
							  t1.setNbreHtNormale(4);
							  t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000));
							  if (regarde < 8) {
								  t1.setNbreSup15(n - 4 - t1.getNbreSup50());
									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(n - 4 - t1.getNbreSup50());

									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n - 4);
								 }

						  }

					  }

				  }


			  }
			  else{
				  if (t1.getDatedebut().getHours() >= 5) {
					  if (t1.getDatefint().getHours() > t1.getDatedebut().getHours()) {
						  if (t1.getDatefint().getHours() < 23) {

							  t1.setNbreHtNormale(4);


							  if (regarde < 8) {
								  t1.setNbreSup15(n - 4);

								  if (regarde + t1.getNbreSup15() > 8) {
									  t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
									  t1.setNbreSup15(8 - regarde);
									  if (regarde2 + t1.getNbreSup40() > 6) {
										  t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										  t1.setNbreSup40(6 - regarde2);
									  }

								  }

							  } else if (regarde == 8 && regarde2 < 6) {
								  t1.setNbreSup40(n - 4);

								  if (regarde2 + t1.getNbreSup40() > 6) {
									  t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
									  t1.setNbreSup40(6 - regarde2);
								  }


							  } else {

								  t1.setNbreSup50(n - 4);
							  }



						  } else {

							  t1.setNbreHtNormale(4);
							  t1.setNbreSup50(1);



							  if (regarde < 8) {
								  t1.setNbreSup15(n - 4 - 1);
								  if (regarde + t1.getNbreSup15() > 8) {
									  t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
									  t1.setNbreSup15(8 - regarde);
									  if (regarde2 + t1.getNbreSup40() > 6) {
										  t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										  t1.setNbreSup40(6 - regarde2);
									  }

								  }

							  } else if (regarde == 8 && regarde2 < 6) {
								  t1.setNbreSup40(n - 4 - 1);

								  if (regarde2 + t1.getNbreSup40() > 6) {
									  t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
									  t1.setNbreSup40(6 - regarde2);
								  }


							  } else {

								  t1.setNbreSup50(n - 4);
							  }

						  }

					  } else {
						  if (t1.getDatefint().getHours() <= 5) {
							  t1.setNbreHtNormale(4);
							  t1.setNbreSup50(NombreHeureJours(79200, ConverteDate(t1.getDatefint())));

							  if (regarde < 8) {
								  t1.setNbreSup15(n - 4 - t1.getNbreSup50());


								  if (regarde + t1.getNbreSup15() > 8) {
									  t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
									  t1.setNbreSup15(8 - regarde);
									  if (regarde2 + t1.getNbreSup40() > 6) {
										  t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										  t1.setNbreSup40(6 - regarde2);
									  }

								  }

							  } else if (regarde == 8 && regarde2 < 6) {
								  t1.setNbreSup40(n - 4 - t1.getNbreSup50());



								  if (regarde2 + t1.getNbreSup40() > 6) {
									  t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
									  t1.setNbreSup40(6 - regarde2);
								  }


							  } else {

								  t1.setNbreSup50(n - 4);
							  }

						  } else {
							  t1.setNbreHtNormale(4);
							  t1.setNbreSup50(NombreHeureJours(79200, 18000));



							  if (regarde < 8) {
								  t1.setNbreSup15(n - 4 - t1.getNbreSup50());

								  if (regarde + t1.getNbreSup15() > 8) {
									  t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
									  t1.setNbreSup15(8 - regarde);
									  if (regarde2 + t1.getNbreSup40() > 6) {
										  t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										  t1.setNbreSup40(6 - regarde2);
									  }

								  }

							  } else if (regarde == 8 && regarde2 < 6) {
								  t1.setNbreSup40(n - 4 - t1.getNbreSup50());



								  if (regarde2 + t1.getNbreSup40() > 6) {
									  t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
									  t1.setNbreSup40(6 - regarde2);
								  }


							  } else {

								  t1.setNbreSup50(n - 4);
							  }

						  }


					  }


				  } else {
					  if (t1.getDatefint().getHours() <= 5) {
						  t1.setNbreHtNormale(4);
						  t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000) + NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
						  if (regarde < 8) {
							  t1.setNbreSup15(n - 4 - t1.getNbreSup50());

							  if (regarde + t1.getNbreSup15() > 8) {
								  t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
								  t1.setNbreSup15(8 - regarde);
								  if (regarde2 + t1.getNbreSup40() > 6) {
									  t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
									  t1.setNbreSup40(6 - regarde2);
								  }

							  }

						  } else if (regarde == 8 && regarde2 < 6) {
							  t1.setNbreSup40(n - 4 - t1.getNbreSup50());



							  if (regarde2 + t1.getNbreSup40() > 6) {
								  t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
								  t1.setNbreSup40(6 - regarde2);
							  }


						  } else {

							  t1.setNbreSup50(n - 4);
						  }


					  } else {
						  if (t1.getDatefint().getHours() == 23) {
							  t1.setNbreHtNormale(4);
							  t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000) + NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
							  if (regarde < 8) {
								  t1.setNbreSup15(n - 4 - t1.getNbreSup50());

								  if (regarde + t1.getNbreSup15() > 8) {
									  t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
									  t1.setNbreSup15(8 - regarde);
									  if (regarde2 + t1.getNbreSup40() > 6) {
										  t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										  t1.setNbreSup40(6 - regarde2);
									  }

								  }

							  } else if (regarde == 8 && regarde2 < 6) {
								  t1.setNbreSup40(n - 4 - t1.getNbreSup50());



								  if (regarde2 + t1.getNbreSup40() > 6) {
									  t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
									  t1.setNbreSup40(6 - regarde2);
								  }


							  } else {

								  t1.setNbreSup50(n - 4);
							  }
						  } else {
							  t1.setNbreHtNormale(4);
							  t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000));
							  if (regarde < 8) {
								  t1.setNbreSup15(n - 4 - t1.getNbreSup50());

								  if (regarde + t1.getNbreSup15() > 8) {
									  t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
									  t1.setNbreSup15(8 - regarde);
									  if (regarde2 + t1.getNbreSup40() > 6) {
										  t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										  t1.setNbreSup40(6 - regarde2);
									  }

								  }

							  } else if (regarde == 8 && regarde2 < 6) {
								  t1.setNbreSup40(n - 4 - t1.getNbreSup50());



								  if (regarde2 + t1.getNbreSup40() > 6) {
									  t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
									  t1.setNbreSup40(6 - regarde2);
								  }


							  } else {

								  t1.setNbreSup50(n - 4);
							  }

						  }

					  }

				  }



			  }
		  }




		  return  t1;
	  }

	  

	  @Override
	  public Taches TACHESCOMPLETSUPVendredi(Taches t1) {
		  
		  SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd");

		  int n = NombreHeureJours(ConverteDate(t1.getDatedebut()), ConverteDate(t1.getDatefint()));
		  t1.setTotal_Heure(n);
		 // Date debutwekend = tachesim.DebutSemaine(t1.getDateday());
		 
		  if(n<=4){
			  t1.setNbreHtNormale(n);
			 


		  }


		  else {


			  t1.settHs(n - 4);
			
		

				  if (t1.getDatedebut().getHours() >= 5) {
					  if (t1.getDatefint().getHours() > t1.getDatedebut().getHours()) {
						  if (t1.getDatefint().getHours() < 23) {

							  t1.setNbreHtNormale(4);
							  
							  
							  if (t1.getNbreSup15() < 8) {
								  t1.setNbreSup15(n - 4);
									 if (t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 );
										 if (t1.getNbreSup40() > 6) {
											 t1.setNbreSup50( t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 );
										 }

									 }

								 } else if (t1.getNbreSup15() == 8 && t1.getNbreSup40() < 6) {
									 t1.setNbreSup40(n - 4);

									 if (t1.getNbreSup40() > 6) {
										 t1.setNbreSup50( t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6);
									 }


								 } else {

									 t1.setNbreSup50(n - 4);
								 }


						  } else {

							  t1.setNbreHtNormale(4);
							  t1.setNbreSup50(1);
							
							  
							  
							  if (t1.getNbreSup15() < 8) {
								  t1.setNbreSup15(n - 4 - 1);
									 if ( t1.getNbreSup15() > 8) {
										 t1.setNbreSup40( t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 );
										 if (t1.getNbreSup40() > 6) {
											 t1.setNbreSup50( t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 );
										 }

									 }

								 } else if (t1.getNbreSup15() == 8 && t1.getNbreSup40() < 6) {
									  t1.setNbreSup40(n - 4 - 1);

									 if ( t1.getNbreSup40() > 6) {
										 t1.setNbreSup50( t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 );
									 }


								 } else {

									 t1.setNbreSup50(n - 4);
								 }
							  
							  
							  
							 

						  }

					  } else {
						  if (t1.getDatefint().getHours() <= 5) {
							  t1.setNbreHtNormale(4);
							  t1.setNbreSup50(NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
							  
							  if (t1.getNbreSup15() < 8) {
								  t1.setNbreSup15(n - 4 - t1.getNbreSup50());
									 if (t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 );
										 if (t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 );
										 }

									 }

								 } else if (t1.getNbreSup15() == 8 && t1.getNbreSup40()< 6) {
									 t1.setNbreSup40(n - 4 - t1.getNbreSup50());

									 if (t1.getNbreSup40() > 6) {
										 t1.setNbreSup50( t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6);
									 }


								 } else {

									 t1.setNbreSup50(n - 4);
								 }
							  
							  
						  } else {
							  t1.setNbreHtNormale(4);
							  t1.setNbreSup50(NombreHeureJours(79200, 18000));
							 
							  if (t1.getNbreSup15() < 8) {
								  t1.setNbreSup15(n - 4 - t1.getNbreSup50());
									 if (t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 );
										 if (t1.getNbreSup40() > 6) {
											 t1.setNbreSup50( t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 );
										 }

									 }

								 } else if (t1.getNbreSup15() == 8 && t1.getNbreSup40()< 6) {
									 t1.setNbreSup40(n - 4 - t1.getNbreSup50());

									 if ( t1.getNbreSup40() > 6) {
										 t1.setNbreSup50( t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6);
									 }


								 } else {

									 t1.setNbreSup50(n - 4);
								 }

						  }


					  }


				  } else {
					  if (t1.getDatefint().getHours() <= 5) {
						  t1.setNbreHtNormale(4);
						  t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000) + NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
					
						  
						  if (t1.getNbreSup15() < 8) {
							  t1.setNbreSup15(n - 4 - t1.getNbreSup50());
								 if ( t1.getNbreSup15() > 8) {
									 t1.setNbreSup40( t1.getNbreSup15() - 8);
									 t1.setNbreSup15(8 );
									 if (t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6);
									 }

								 }

							 } else if (t1.getNbreSup15() == 8 && t1.getNbreSup50() < 6) {
								 t1.setNbreSup40(n - 4 - t1.getNbreSup50());

								 if (t1.getNbreSup40() > 6) {
									 t1.setNbreSup50(t1.getNbreSup40() - 6 + t1.getNbreSup50());
									 t1.setNbreSup40(6);
								 }


							 } else {

								 t1.setNbreSup50(n - 4);
							 }


					  } else {
						  if (t1.getDatefint().getHours() == 23) {
							  t1.setNbreHtNormale(4);
							  t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000) + NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
							
							  if (t1.getNbreSup15() < 8) {
								  t1.setNbreSup15(n - 4 - t1.getNbreSup50());
									 if (t1.getNbreSup15() > 8) {
										 t1.setNbreSup40( t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8);
										 if (t1.getNbreSup40() > 6) {
											 t1.setNbreSup50( t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6);
										 }

									 }

								 } else if (t1.getNbreSup15() == 8 && t1.getNbreSup40() < 6) {
									 t1.setNbreSup40(n - 4 - t1.getNbreSup50());

									 if ( t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 );
									 }


								 } else {

									 t1.setNbreSup50(n - 4);
								 }
						  } else {
							  t1.setNbreHtNormale(4);
							  t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000));
							  if (t1.getNbreSup15()< 8) {
								  t1.setNbreSup15(n - 4 - t1.getNbreSup50());
									 if (t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 );
										 if ( t1.getNbreSup40() > 6) {
											 t1.setNbreSup50( t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 );
										 }

									 }

								 } else if (t1.getNbreSup15() == 8 && t1.getNbreSup40() < 6) {
									 t1.setNbreSup40(n - 4 - t1.getNbreSup50());

									 if (t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 );
									 }


								 } else {

									 t1.setNbreSup50(n - 4);
								 }

						  }

					  }

				  }


			  }
			 




		  return  t1;
		  

	  }


	  @Override
	public int NombreHeureJours(int t1, int t2) {
		// TODO Auto-generated method stub
		  int rs=0;
			
		  if(t2>t1) {
		  
		  rs=(t2-t1)/3600;}
		  
		  
		  else { rs=(24)-((t1-t2)/3600); }
		  
		return rs;
	}

	@Override
	public Taches MemeJours(Taches t1, Taches t2) throws ParseException {
		
		 SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd");


		  int n = 0;
		  int regarde=0;
		  int regarde2=0;
		  String d1=formatter6.format(DebutWeekend(t1.getDateday()));
		  Date debutwekend=formatter6.parse(d1);
		  regarde=tachesim.SommeSup15(debutwekend,t1.getDateday(),t1.getEmployer().getId());
		  regarde2=tachesim.SommeSup40(debutwekend,t1.getDateday(),t1.getEmployer().getId());
		  System.out.println("Nombre15"+":"+regarde);
		  System.out.println("Nombre40"+":"+regarde2);



		  n = NombreHeureJours(ConverteDate(t1.getDatedebut()), ConverteDate(t1.getDatefint()));
		  t1.setTotal_Heure(n);
		//  int p=CalulePanier(t1);

		 
		
		//	  t1.setPanier(p);
			  t1.settHs(n);
			 // Date debutwekend = tachesim.DebutSemaine(t1.getDateday());


				 //System.out.println(IdentiqueWeekend(t1.getDateday(), debutwekend));
			  System.out.println("identique tache-tach"+IdentiqueWeekend(t1.getDateday(), debutwekend));
			
					 if (t1.getDatedebut().getHours() >= 5) {
						 if (t1.getDatefint().getHours() > t1.getDatedebut().getHours()) {
							 if (t1.getDatefint().getHours() < 23) {

								 t1.setNbreSup15(n);


								 if (regarde < 8) {
									
									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(n);

									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n);
								 }


							 } else {

								 
								 t1.setNbreSup50(1);
								 t1.setNbreSup15(n-1);

								 if (regarde < 8) {
									
									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(n-1);

									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n);
								 }

							 }

						 } else {
							 if (t1.getDatefint().getHours() <= 5) {
								
								 t1.setNbreSup50(NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
								 t1.setNbreSup15(n-t1.getNbreSup50());

								 if (regarde < 8) {
									
									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(n - t1.getNbreSup50());

									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n);
								 }


							 } else {
					
								 t1.setNbreSup50(NombreHeureJours(79200, 18000));
								 t1.setNbreSup15(n-t1.getNbreSup50());

								 if (regarde < 8) {
							
									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(n-t1.getNbreSup50());

									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n);
								 }

							 }


						 }


					 } else {
						 if (t1.getDatefint().getHours() <= 5) {
							
							 t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000) + NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
							 t1.setNbreSup15(n - t1.getNbreSup50());

							 if (regarde < 8) {
								


								 if (regarde + t1.getNbreSup15() > 8) {
									 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
									 t1.setNbreSup15(8 - regarde);
									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }

								 }

							 } else if (regarde == 8 && regarde2 < 6) {
								 t1.setNbreSup40(n-t1.getNbreSup50());


								 if (regarde2 + t1.getNbreSup40() > 6) {
									 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
									 t1.setNbreSup40(6 - regarde2);
								 }


							 } else {

								 t1.setNbreSup50(n);
							 }


						 } else {
							 if (t1.getDatefint().getHours() == 23) {
								
								 t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000) + NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
								 t1.setNbreSup15(n- t1.getNbreSup50());

								 if (regarde < 8) {
									

									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(n  - t1.getNbreSup50());

									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n);
								 }
							 } else {
							
								 t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000));
								 t1.setNbreSup15(n - t1.getNbreSup50());

								 if (regarde < 8) {
									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(n  - t1.getNbreSup50());


									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n);
								 }


							 }

						 }

					 }


				



		  




		  return t1;

	}

	

	@Override
	  public  Boolean IdentiqueDate(Date d1,Date d2){
		  Calendar calendar=Calendar.getInstance();
		  calendar.setTime(d1);
		  Calendar calendar1=Calendar.getInstance();
		  calendar1.setTime(d2);

		  if (d1.getDay()==d2.getDay()&&d1.getMonth()==d2.getMonth()&&d1.getYear()==d2.getYear()&&calendar.DAY_OF_WEEK_IN_MONTH==calendar1.DAY_OF_WEEK_IN_MONTH){

			  return  true;
		  }

		  return false;


	  }

	  @Override
	  public Boolean IdentiqueWeekend(Date d1, Date d2) {
		  Boolean  b=false;
		  Calendar calendar=Calendar.getInstance();
		  calendar.setTime(d1);
		  System.out.println(calendar);
		  Calendar calendar1=Calendar.getInstance();
		  calendar1.setTime(d2);
		  if (d1.getYear()==d2.getYear()&&calendar.get(calendar.WEEK_OF_YEAR)==calendar1.get(calendar1.WEEK_OF_YEAR)){
			  b=true;

		
		  }

		  return b;
	  }

	  @Override
	  public Date DebutWeekend(Date date) throws ParseException {
		  SimpleDateFormat formatter5 = new SimpleDateFormat("yyyy-MM-dd");
		  Calendar calendar1=Calendar.getInstance();
		  calendar1.setTime(date);
		  System.out.println("NumeroWeeknd tache"+calendar1.get(calendar1.WEEK_OF_YEAR));
		  Calendar first=(Calendar) calendar1.clone();
		  first.add(calendar1.DAY_OF_WEEK,first.getFirstDayOfWeek()-first.get(Calendar.DAY_OF_WEEK));
		  System.out.print("debut week"+formatter5.format(first.getTime()));
		  
		  return first.getTime();
	  }

	  @Override
	  public Taches TachesCompletJoursSup(Taches t1, Taches t2) throws ParseException {
		  SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd");


		  int n = 0;
		  int regarde=0;
		  int regarde2=0;
		  String d1=formatter6.format(DebutWeekend(t1.getDateday()));
		  Date debutwekend=formatter6.parse(d1);
		  regarde=tachesim.SommeSup15(debutwekend,t1.getDateday(),t1.getEmployer().getId());
		  regarde2=tachesim.SommeSup40(debutwekend,t1.getDateday(),t1.getEmployer().getId());
		  System.out.println("Nombre15"+":"+regarde);
		  System.out.println("Nombre40"+":"+regarde2);



		  n = NombreHeureJours(ConverteDate(t1.getDatedebut()), ConverteDate(t1.getDatefint()));
		  t1.setTotal_Heure(n);

		  if(n<=9){
			  t1.setNbreHtNormale(n);
			

		  }
		  else {
	
			  t1.settHs(n - 9);
			 // Date debutwekend = tachesim.DebutSemaine(t1.getDateday());


				 //System.out.println(IdentiqueWeekend(t1.getDateday(), debutwekend));
			  System.out.println("identique tache-tach"+IdentiqueWeekend(t1.getDateday(), debutwekend));
			
					 if (t1.getDatedebut().getHours() >= 5) {
						 if (t1.getDatefint().getHours() > t1.getDatedebut().getHours()) {
							 if (t1.getDatefint().getHours() < 23) {

								 t1.setNbreHtNormale(9);


								 if (regarde < 8) {
									 t1.setNbreSup15(n - 9);
									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(n - 9);

									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n - 9);
								 }


							 } else {

								 t1.setNbreHtNormale(9);
								 t1.setNbreSup50(1);

								 if (regarde < 8) {
									 t1.setNbreSup15(n - 9 - 1);
									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(n - 9 - 1);

									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n - 9);
								 }

							 }

						 } else {
							 if (t1.getDatefint().getHours() <= 5) {
								 t1.setNbreHtNormale(9);
								 t1.setNbreSup50(NombreHeureJours(79200, ConverteDate(t1.getDatefint())));


								 if (regarde < 8) {
									 t1.setNbreSup15(n - 9 - t1.getNbreSup50());
									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(n - 9 - t1.getNbreSup50());

									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n - 9);
								 }


							 } else {
								 t1.setNbreHtNormale(9);
								 t1.setNbreSup50(NombreHeureJours(79200, 18000));


								 if (regarde < 8) {
									 t1.setNbreSup15(n - 9 - t1.getNbreSup50());
									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(n - 9 - t1.getNbreSup50());

									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n - 9);
								 }

							 }


						 }


					 } else {
						 if (t1.getDatefint().getHours() <= 5) {
							 t1.setNbreHtNormale(9);
							 t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000) + NombreHeureJours(79200, ConverteDate(t1.getDatefint())));


							 if (regarde < 8) {
								 t1.setNbreSup15(n - 9 - t1.getNbreSup50());


								 if (regarde + t1.getNbreSup15() > 8) {
									 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
									 t1.setNbreSup15(8 - regarde);
									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }

								 }

							 } else if (regarde == 8 && regarde2 < 6) {
								 t1.setNbreSup40(n - 9 - t1.getNbreSup50());


								 if (regarde2 + t1.getNbreSup40() > 6) {
									 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
									 t1.setNbreSup40(6 - regarde2);
								 }


							 } else {

								 t1.setNbreSup50(n - 9);
							 }


						 } else {
							 if (t1.getDatefint().getHours() == 23) {
								 t1.setNbreHtNormale(9);
								 t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000) + NombreHeureJours(79200, ConverteDate(t1.getDatefint())));


								 if (regarde < 8) {
									 t1.setNbreSup15(n - 9 - t1.getNbreSup50());

									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(n - 9 - t1.getNbreSup50());

									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n - 9);
								 }
							 } else {
								 t1.setNbreHtNormale(9);
								 t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000));


								 if (regarde < 8) {
									 t1.setNbreSup15(n - 9 - t1.getNbreSup50());

									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(n - 9 - t1.getNbreSup50());


									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n - 9);
								 }


							 }

						 }

					 }


				



		  }




		  return t1;

	}
/*
	@Override
	public Taches TachesCompletJoursSup(Taches t1, Taches t2) {
	
		 int n=0; 
		 Date	 debutwekend=tachesim.DebutSemaine(t1.getDateday());
		 
		 int regarde=tachesim.SommeSup15(debutwekend, t1.getDateday(), t1.getEmployer().getId());
		 int regarde2=tachesim.SommeSup40(debutwekend, t1.getDateday(), t1.getEmployer().getId());
		 n=NombreHeureJours(ConverteDate(t1.getDatedebut()),ConverteDate(t1.getDatefint()));
		 t1.setTotal_Heure(n);
		 System.out.println("on rentre avec db>0");
		 if(t1.getDatedebut().getHours()>0) {
			 if(t1.getDatedebut().getHours()<5) {
				 
				 
				  if(n<=9) {
					  t1.setNbreHtNormale(n);
				  }
				  else if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=17&&t1.getDatedebut().getHours()<t1.getDatefint().getHours())
				  {
					  
					  
					  if(n<=9) {
						  t1.setNbreHtNormale(n);
						  
					  }
					  else {
						  
						  if(regarde<8) {
							  t1.setNbreHtNormale(9);
							  t1.setNbreSup15(n-9);
							  if(regarde+t1.getNbreSup15()>8) {
									t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
									t1.setNbreSup15(8-regarde);
									if(regarde2+t1.getNbreSup40()>6) {
										t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
										t1.setNbreSup40(6-regarde2);
									}
									
								}
							  
						  }
						  else if(regarde==8&&regarde2<6) {
							  t1.setNbreHtNormale(9);
							  t1.setNbreSup40(n-9);
								if(regarde2+t1.getNbreSup40()>6) {
									t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
									t1.setNbreSup40(6-regarde2);
								}
								
							  
						  }
						  else {
							  t1.setNbreHtNormale(9);
							  t1.setNbreSup50(n-9);
						  }
						  
						  
					
					  }
					  
				  }
				  else if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=23&&t1.getDatedebut().getHours()<t1.getDatefint().getHours()&&t1.getDatefint().getHours()>17)
				  {
					  if(t1.getDatefint().getHours()==23) {
						  if(regarde<8) {
							  t1.setNbreHtNormale(9);
							  t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
							  t1.setNbreSup15(n-9-t1.getNbreSup50());
							  if(regarde+t1.getNbreSup15()>8) {
								  t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
								  t1.setNbreSup15(8-regarde);
								  if(regarde2+t1.getNbreSup40()>6) {
									  t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
									  t1.setNbreSup40(6-regarde2);
								  }

							  }


						  }
						  else if(regarde==8&&regarde2<6) {
							  t1.setNbreHtNormale(9);
							  t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
							  t1.setNbreSup40(n-9-t1.getNbreSup50());
							  if(regarde2+t1.getNbreSup40()>6) {
								  t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
								  t1.setNbreSup40(6-regarde2);
							  }

						  }
						  else {
							  t1.setNbreHtNormale(9);
							  t1.setNbreSup50(n-9);


						  }


					  }
					  else {
						  if(regarde<8) {
							  t1.setNbreHtNormale(9);
							  t1.setNbreSup15(n-9);
							  if(regarde+t1.getNbreSup15()>8) {
								  t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
								  t1.setNbreSup15(8-regarde);
								  if(regarde2+t1.getNbreSup40()>6) {
									  t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
									  t1.setNbreSup40(6-regarde2);
								  }
							  }


						  }
						  else if(regarde==8&&regarde<6) {
							  t1.setNbreHtNormale(9);
							  t1.setNbreSup40(n-9);
							  if(regarde2+t1.getNbreSup40()>6) {
								  t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
								  t1.setNbreSup40(6-regarde2);
							  }
						  }
						  else {
							  t1.setNbreHtNormale(9);
							  t1.setNbreSup50(n-9);
						  }
					  }


				  }
				  else if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=t1.getDatedebut().getHours()) {
					  if(t1.getDatefint().getHours()<=8) {
						  if(t1.getDatefint().getHours()<=5) {
							  if(n<=9) {
								  t1.setNbreHtNormale(n);

							  }
							  else {
								  if(regarde<8) {
									  t1.setNbreHtNormale(9);

									  t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
									  t1.setNbreSup15(n-(9+t1.getNbreSup50()));
									  if(regarde+t1.getNbreSup15()>8) {
										  t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
										  t1.setNbreSup15(8-regarde);
										  if(regarde2+t1.getNbreSup40()>6) {
											  t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
											  t1.setNbreSup40(6-regarde2);
										  }

									  }

								  }
								  else if(regarde==8&&regarde2<6) {
									  t1.setNbreHtNormale(9);

									  t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
									  t1.setNbreSup40(n-(9+t1.getNbreSup50()));
									  if(regarde2+t1.getNbreSup40()>6) {
										  t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
										  t1.setNbreSup40(6-regarde2);
									  }

								  }
								  else {
									  t1.setNbreHtNormale(9);

									  t1.setNbreSup50(n-9);
								  }

							  }
						  }
						  else {
							  if(n<=9) {
								  t1.setNbreHtNormale(n);
							  }
							  else {
								  if(regarde<8) {
									  t1.setNbreHtNormale(9);
									  t1.setNbreSup50(NombreHeureJours(79200,18000));
									  t1.setNbreSup15((n-9-t1.getNbreSup50())+(NombreHeureJours(18000,ConverteDate(t1.getDatefint()))));

									  if(regarde+t1.getNbreSup15()>8) {
										  t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
										  t1.setNbreSup15(8-regarde);
										  if(regarde2+t1.getNbreSup40()>6) {
											  t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
											  t1.setNbreSup40(6-regarde2);
										  }}

								  }
								  else if(regarde==8&&regarde2<6) {
									  t1.setNbreHtNormale(9);
									  t1.setNbreSup50(NombreHeureJours(79200,18000));
									  t1.setNbreSup40((n-9-t1.getNbreSup50())+(NombreHeureJours(18000,ConverteDate(t1.getDatefint()))));
									  if(regarde2+t1.getNbreSup40()>6) {
										  t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
										  t1.setNbreSup40(6-regarde2);
									  }
								  }
								  else {

									  t1.setNbreHtNormale(9);

									  t1.setNbreSup50(n-9);
								  }


							  }



						  }

					  }
				  }

				  else {
					  if(regarde<8) {

						  t1.setNbreHtNormale(9);

						  t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
						  t1.setNbreSup15(n-(9+t1.getNbreSup50()));
						  if(regarde+t1.getNbreSup15()>8) {
							  t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
							  t1.setNbreSup15(8-regarde);
							  if(regarde2+t1.getNbreSup40()>6) {
								  t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
								  t1.setNbreSup40(6-regarde2);
							  }

						  }

					  }
					  else  if(regarde==8&& regarde2<6) {

						  t1.setNbreHtNormale(9);

						  t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
						  t1.setNbreSup40(n-(9+t1.getNbreSup50()));
						  if(regarde2+t1.getNbreSup40()>6) {
							  t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
							  t1.setNbreSup40(6-regarde2);
						  }

					  }
					  else {
						  t1.setNbreHtNormale(9);
						  t1.setNbreSup50(n-9);

					  }



				  }
				 
			 }
			 else  if(t1.getDatedebut().getHours()==8) {
				 if(n<=9) {
					 t1.setNbreHtNormale(n);
				 }
				 else if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=23&&t1.getDatedebut().getHours()<t1.getDatefint().getHours()&&t1.getDatefint().getHours()>17)
				 {
					 if(t1.getDatefint().getHours()==23) {
					 	if(regarde<8){
							t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),61200));
							t1.setNbreSup15(NombreHeureJours(61200,79200));
							t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
							if(regarde+t1.getNbreSup15()>8) {
								t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
								t1.setNbreSup15(8-regarde);
								if(regarde2+t1.getNbreSup40()>6) {
									t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
									t1.setNbreSup40(6-regarde2);
								}

							}

						}
					 	else if(regarde==8&&regarde2<6){
							t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),61200));
							t1.setNbreSup40(NombreHeureJours(61200,79200));
							t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
							if(regarde2+t1.getNbreSup40()>6) {
								t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
								t1.setNbreSup40(6-regarde2);
							}
						}
					 	else{
					 		t1.setNbreHtNormale(9);
					 		t1.setNbreSup50(n-9);
						}



					 }
					 else {
					 	if(regarde<8){
							t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),61200));
							t1.setNbreSup15(NombreHeureJours(61200,ConverteDate(t1.getDatefint())));
							if(regarde+t1.getNbreSup15()>8) {
								t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
								t1.setNbreSup15(8-regarde);
								if(regarde2+t1.getNbreSup40()>6) {
									t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
									t1.setNbreSup40(6-regarde2);
								}

							}

						}
					 	else if(regarde==8&&regarde2<6){
							t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),61200));
							t1.setNbreSup40(NombreHeureJours(61200,ConverteDate(t1.getDatefint())));
							if(regarde2+t1.getNbreSup40()>6) {
								t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
								t1.setNbreSup40(6-regarde2);
							}
						}
					 	else {
					 		t1.setNbreHtNormale(9);
					 		t1.setNbreSup50(n-9);
						}


					 }



				 }

				 else if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=t1.getDatedebut().getHours())
				 {
				 	if (n<=9){
				 		t1.setNbreHtNormale(n);
					}
					else if(t1.getDatefint().getHours()<=8) {
						 if(t1.getDatefint().getHours()<=5) {
						 	if (regarde<8){
								t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),61200));
								t1.setNbreSup15(NombreHeureJours(61200,79200));
								t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
								if(regarde+t1.getNbreSup15()>8) {
									t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
									t1.setNbreSup15(8-regarde);
									if(regarde2+t1.getNbreSup40()>6) {
										t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
										t1.setNbreSup40(6-regarde2);
									}

								}
							}
						 	else if(regarde==8&&regarde2<6){
								t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),61200));
								t1.setNbreSup40(NombreHeureJours(61200,79200));
								t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
								if(regarde2+t1.getNbreSup40()>6) {
									t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
									t1.setNbreSup40(6-regarde2);
								}
							}
						 	else{
						 		t1.setNbreHtNormale(9);
						 		t1.setNbreSup50(n-9);
							}


						 }
						 else {
						 	if (regarde<8) {
								t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()), 61200));
								t1.setNbreSup15(NombreHeureJours(61200, 79200));
								t1.setNbreSup50(NombreHeureJours(79200, 18000));
								t1.setNbreSup15(t1.getNbreSup15() + (NombreHeureJours(18000, ConverteDate(t1.getDatefint()))));
								if (regarde+t1.getNbreSup15() > 8) {
									t1.setNbreSup40(regarde+t1.getNbreSup15() - 8);
									t1.setNbreSup15(8-regarde);
									if (regarde2+t1.getNbreSup40() > 6) {
										t1.setNbreSup50(regarde2+t1.getNbreSup40() - 6 + t1.getNbreSup50());
										t1.setNbreSup40(6-regarde2);
									}

								}
							}
						 	else if (regarde==8&&regarde2<6){
								t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()), 61200));
								t1.setNbreSup40(NombreHeureJours(61200, 79200));
								t1.setNbreSup50(NombreHeureJours(79200, 18000));
								t1.setNbreSup40(t1.getNbreSup40() + (NombreHeureJours(18000, ConverteDate(t1.getDatefint()))));
								if (regarde2+t1.getNbreSup40() > 6) {
									t1.setNbreSup50(regarde2+t1.getNbreSup40() - 6 + t1.getNbreSup50());
									t1.setNbreSup40(6-regarde2);
								}
							}
						 	else{
						 		t1.setNbreHtNormale(9);
						 		t1.setNbreSup50(n-9);
							}



						 }

					 }
				 }
				 else {
				 	if (regarde<8){
						t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),61200));
						t1.setNbreSup15(NombreHeureJours(61200,79200));
						t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));

						if(regarde+t1.getNbreSup15()>8) {
							t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
							t1.setNbreSup15(8-regarde);
							if(regarde2+t1.getNbreSup40()>6) {
								t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
								t1.setNbreSup40(6-regarde2);
							}

						}
					}
				 	else if (regarde==8&&regarde2<6){
						t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),61200));
						t1.setNbreSup40(NombreHeureJours(61200,79200));
						t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
						if(regarde2+t1.getNbreSup40()>6) {
							t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
							t1.setNbreSup40(6-regarde2);
						}


					}
				 	else{
				 		t1.setNbreHtNormale(9);
				 		t1.setNbreSup50(n-9);
					}



				 }

			 }
			 else if(t1.getDatedebut().getHours()==5) {
				 if(n<=9) {
					 t1.setNbreHtNormale(n);
				 }
				 else if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=17&&t1.getDatedebut().getHours()<t1.getDatefint().getHours())
				 {

					 if(n<=9) {
						 t1.setNbreHtNormale(n);

					 }
					 else {
					 	if (regarde<8) {
							t1.setNbreHtNormale(9);
							t1.setNbreSup15(n - 9);
							if (regarde + t1.getNbreSup15() > 8) {
								t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
								t1.setNbreSup15(8 - regarde);
								if (regarde2 + t1.getNbreSup40() > 6) {
									t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
									t1.setNbreSup40(6 - regarde2);
								}

							}
						}
					 	else  if (regarde==8&&regarde2<6){
							t1.setNbreHtNormale(9);
							t1.setNbreSup40(n - 9);
							if (regarde2 + t1.getNbreSup40() > 6) {
								t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
								t1.setNbreSup40(6 - regarde2);
							}

						}
					 	else{
							t1.setNbreHtNormale(9);
							t1.setNbreSup50(n - 9);
						}
					 }

				 }
				 else if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=23&&t1.getDatedebut().getHours()<t1.getDatefint().getHours()&&t1.getDatefint().getHours()>17)
				 {
					 if(t1.getDatefint().getHours()==23) {
					 	if (regarde<8){
							t1.setNbreHtNormale(9);

							t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
							t1.setNbreSup15(n-9-t1.getNbreSup50());
							if(regarde+t1.getNbreSup15()>8) {
								t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
								t1.setNbreSup15(8-regarde);
								if(regarde2+t1.getNbreSup40()>6) {
									t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
									t1.setNbreSup40(6-regarde2);
								}

							}
						}
					 	else  if (regarde==8&&regarde2<6){
							t1.setNbreHtNormale(9);

							t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
							t1.setNbreSup40(n-9-t1.getNbreSup50());
							if(regarde2+t1.getNbreSup40()>6) {
								t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
								t1.setNbreSup40(6-regarde2);
							}

						}
					 	else{
							t1.setNbreHtNormale(9);
							t1.setNbreSup50(n-9);
						}


					 }
					 else {
						 if (regarde < 8) {

							 t1.setNbreHtNormale(9);
							 t1.setNbreSup15(n - 9);
							 if (t1.getNbreSup15() > 8) {
								 t1.setNbreSup40(t1.getNbreSup15() - 8);
								 t1.setNbreSup15(8);
								 if (t1.getNbreSup40() > 6) {
									 t1.setNbreSup50(t1.getNbreSup40() - 6 + t1.getNbreSup50());
									 t1.setNbreSup40(6);
								 }

							 }
						 }
						 else if (regarde==8&&regarde2<6){
							 t1.setNbreHtNormale(9);
							 t1.setNbreSup40(n - 9);
							 if (t1.getNbreSup40() > 6) {
								 t1.setNbreSup50(t1.getNbreSup40() - 6 + t1.getNbreSup50());
								 t1.setNbreSup40(6);
							 }
						 }
						 else
						 {
						 	t1.setNbreHtNormale(9);
						 	t1.setNbreSup50(n-9);
						 }
					 }

				 }
				 else if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=t1.getDatedebut().getHours())
				 {
					 if(t1.getDatefint().getHours()<=8) {
						 if(t1.getDatefint().getHours()<=5) {
						 	if (regarde<8){
								t1.setNbreHtNormale(9);

								t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
								t1.setNbreSup15(n-(9+t1.getNbreSup50()));
								if(regarde+t1.getNbreSup15()>8) {
									t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
									t1.setNbreSup15(8-regarde);
									if(regarde2+t1.getNbreSup40()>6) {
										t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
										t1.setNbreSup40(6-regarde2);
									}

								}
							}
						 	else if (regarde==8&&regarde2<6){
								t1.setNbreHtNormale(9);

								t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
								t1.setNbreSup40(n-(9+t1.getNbreSup50()));
								if(regarde2+t1.getNbreSup40()>6) {
									t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
									t1.setNbreSup40(6-regarde2);
								}
							}
						 	else
							{
								t1.setNbreHtNormale(9);
								t1.setNbreSup50(n-9);
							}


						 }
						 else {
						 	if (regarde<8){
							 t1.setNbreHtNormale(9);

							 t1.setNbreSup50(NombreHeureJours(79200,18000));

							 t1.setNbreSup15(n-(9+t1.getNbreSup50())+NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
							 if(regarde+t1.getNbreSup15()>8) {
								 t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
								 t1.setNbreSup15(8-regarde);
								 if(regarde2+t1.getNbreSup40()>6) {
									 t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
									 t1.setNbreSup40(6-regarde2);
								 }

							 }

						 	}
						 	else if (regarde==8&&regarde2<8){
								t1.setNbreHtNormale(9);

								t1.setNbreSup50(NombreHeureJours(79200,18000));

								t1.setNbreSup40(n-(9+t1.getNbreSup50())+NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
								if(regarde2+t1.getNbreSup40()>6) {
									t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
									t1.setNbreSup40(6-regarde2);
								}

							}
						 	else
							{
								t1.setNbreHtNormale(9);
								t1.setNbreSup50(n-9);
							}



						 }

					 }
				 }
				 else {
				 	if (regarde<8) {
						t1.setNbreHtNormale(9);

						t1.setNbreSup50(NombreHeureJours(79200, 18000));

						t1.setNbreSup15(n - (9 + t1.getNbreSup50()) + NombreHeureJours(18000, ConverteDate(t1.getDatefint())));
						if (regarde+t1.getNbreSup15() > 8) {
							t1.setNbreSup40(regarde+t1.getNbreSup15() - 8);
							t1.setNbreSup15(8-regarde);
							if (regarde2+t1.getNbreSup40() > 6) {
								t1.setNbreSup50(regarde2+t1.getNbreSup40() - 6 + t1.getNbreSup50());
								t1.setNbreSup40(6-regarde2);
							}

						}
					}
				 	else if (regarde==8&&regarde2<6){
						t1.setNbreHtNormale(9);

						t1.setNbreSup50(NombreHeureJours(79200, 18000));

						t1.setNbreSup40(n - (9 + t1.getNbreSup50()) + NombreHeureJours(18000, ConverteDate(t1.getDatefint())));
						if (regarde2+t1.getNbreSup40() > 6) {
							t1.setNbreSup50(regarde2+t1.getNbreSup40() - 6 + t1.getNbreSup50());
							t1.setNbreSup40(6-regarde2);
						}
					}
				 	else
					{
						t1.setNbreHtNormale(9);
						t1.setNbreSup50(n-9);
					}


				 }


			 }
			 else {
				 if(n<=9) {
					 t1.setNbreHtNormale(n);
				 }
				 else if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=23&&t1.getDatedebut().getHours()<t1.getDatefint().getHours()&&t1.getDatefint().getHours()>17)
				 {
					 if(t1.getDatefint().getHours()==23) {
					 	if (regarde<8) {
							t1.setNbreHtNormale(9);

							t1.setNbreSup50(NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
							t1.setNbreSup15(n - 9 - t1.getNbreSup50());
							if (regarde+t1.getNbreSup15() > 8) {
								t1.setNbreSup40(regarde+t1.getNbreSup15() - 8);
								t1.setNbreSup15(8-regarde);
								if (regarde2+t1.getNbreSup40() > 6) {
									t1.setNbreSup50(regarde2+t1.getNbreSup40() - 6 + t1.getNbreSup50());
									t1.setNbreSup40(6-regarde2);
								}

							}
						}
					 	else if(regarde==8&&regarde2<6){
							t1.setNbreHtNormale(9);

							t1.setNbreSup50(NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
							t1.setNbreSup40(n - 9 - t1.getNbreSup50());
							if (regarde2+t1.getNbreSup40() > 6) {
								t1.setNbreSup50(regarde2+t1.getNbreSup40() - 6 + t1.getNbreSup50());
								t1.setNbreSup40(6-regarde2);
							}

						}
					 	else
						{
							t1.setNbreHtNormale(9);
							t1.setNbreSup50(n-9);
						}

					 }
					 else {
					 	if (regarde<8){
							t1.setNbreHtNormale(9);
							t1.setNbreSup15(n-9);
							if(regarde+t1.getNbreSup15()>8) {
								t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
								t1.setNbreSup15(8-regarde);
								if(regarde2+t1.getNbreSup40()>6) {
									t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
									t1.setNbreSup40(6-regarde2);
								}

							}
						}
					 	else if (regarde==8&&regarde2<6){
							t1.setNbreHtNormale(9);
							t1.setNbreSup40(n-9);
							if(regarde2+t1.getNbreSup40()>6) {
								t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
								t1.setNbreSup40(6-regarde2);
							}

						}
					 	else{
					 		t1.setNbreHtNormale(9);
					 		t1.setNbreSup50(n-9);
						}


					 }

				 }
				 else if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=t1.getDatedebut().getHours())
				 {
					 if(t1.getDatefint().getHours()<=8) {
						 if(t1.getDatefint().getHours()<=5) {
						 	if (regarde<8){
								t1.setNbreHtNormale(9);

								t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
								t1.setNbreSup15(n-(9+t1.getNbreSup50()));
								if(regarde+t1.getNbreSup15()>8) {
									t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
									t1.setNbreSup15(8-regarde);
									if(regarde2+t1.getNbreSup40()>6) {
										t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
										t1.setNbreSup40(6-regarde2);
									}

								}
							}
						 	else if (regarde==8&&regarde2<6){
								t1.setNbreHtNormale(9);

								t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
								t1.setNbreSup40(n-(9+t1.getNbreSup50()));
								if(regarde2+t1.getNbreSup40()>6) {
									t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
									t1.setNbreSup40(6-regarde2);
								}
							}
						 	else {
						 		t1.setNbreHtNormale(9);
						 		t1.setNbreSup50(n-9);
							}


						 }
						 else {
						 	if (regarde<8){
								t1.setNbreHtNormale(9);

								t1.setNbreSup50(NombreHeureJours(79200,18000));

								t1.setNbreSup15(n-(9+t1.getNbreSup50())+NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
								if(regarde+t1.getNbreSup15()>8) {
									t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
									t1.setNbreSup15(8-regarde);
									if(regarde2+t1.getNbreSup40()>6) {
										t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
										t1.setNbreSup40(6-regarde2);
									}

								}
							}
						 	else if (regarde==8&&regarde2<6){
								t1.setNbreHtNormale(9);

								t1.setNbreSup50(NombreHeureJours(79200,18000));

								t1.setNbreSup40(n-(9+t1.getNbreSup50())+NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
								if(regarde2+t1.getNbreSup40()>6) {
									t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
									t1.setNbreSup40(6-regarde2);
								}
							}
						 	else {
						 		t1.setNbreHtNormale(9);
						 		t1.setNbreSup50(n-9);
							}




						 }

					 }
				 }
				 else if(t1.getDatefint().getHours()==0) {
					 if(n<=9) {
						 t1.setNbreHtNormale(n);
					 }
					 else {
					 	if (regarde<8){

							t1.setNbreHtNormale(9);

							t1.setNbreSup50(NombreHeureJours(79200,0));
							t1.setNbreSup15(n-9-t1.getNbreSup50());
							if(regarde+t1.getNbreSup15()>8) {
								t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
								t1.setNbreSup15(8-regarde);
								if(regarde2+t1.getNbreSup40()>6) {
									t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
									t1.setNbreSup40(6-regarde2);
								}

							}

						}
					 	else if (regarde==8&&regarde2<6){
							t1.setNbreHtNormale(9);

							t1.setNbreSup50(NombreHeureJours(79200,0));
							t1.setNbreSup40(n-9-t1.getNbreSup50());
							if(regarde2+t1.getNbreSup40()>6) {
								t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
								t1.setNbreSup40(6-regarde2);
							}

						}
					 	else {
					 		t1.setNbreHtNormale(9);
					 		t1.setNbreSup50(n-9);
						}

					 }

				 }
				 else {
					 if(n<=9) {
						 t1.setNbreHtNormale(n);

					 }
					 else {
						 if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=23&&t1.getDatedebut().getHours()<t1.getDatefint().getHours()&&t1.getDatefint().getHours()>17)
						 {
							 if(t1.getDatefint().getHours()==23) {
							 	if (regarde<8){
									t1.setNbreHtNormale(9);

									t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
									t1.setNbreSup15(n-9-t1.getNbreSup50());
									if(regarde+t1.getNbreSup15()>8) {
										t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
										t1.setNbreSup15(8-regarde);
										if(regarde2+t1.getNbreSup40()>6) {
											t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
											t1.setNbreSup40(6-regarde2);
										}

									}
								}
							 	else if (regarde==8&&regarde2<6){
									t1.setNbreHtNormale(9);

									t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
									t1.setNbreSup40(n-9-t1.getNbreSup50());
									if(regarde2+t1.getNbreSup40()>6) {
										t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
										t1.setNbreSup40(6-regarde2);
									}
								}
							 	else
							 		{
							 			t1.setNbreHtNormale(9);
							 			t1.setNbreSup50(n-9);
									}



							 }
							 else {
							 	if (regarde<8){
									t1.setNbreHtNormale(9);
									t1.setNbreSup15(n-9);
									if(regarde+t1.getNbreSup15()>8) {
										t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
										t1.setNbreSup15(8-regarde);
										if(regarde2+t1.getNbreSup40()>6) {
											t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
											t1.setNbreSup40(6-regarde2);
										}

									}
								}
							 	else  if (regarde==8&&regarde2<6){
									t1.setNbreHtNormale(9);
									t1.setNbreSup40(n-9);
									if(regarde2+t1.getNbreSup40()>6) {
										t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
										t1.setNbreSup40(6-regarde2);
									}

								}
							 	else {
							 		t1.setNbreSup50(n-9);
							 		t1.setNbreHtNormale(9);
								}


							 }



						 }
						 else if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=t1.getDatedebut().getHours())
						 {
							 if(t1.getDatefint().getHours()<=8) {
								 if(t1.getDatefint().getHours()<=5) {
								 	if (regarde<8){

										t1.setNbreHtNormale(9);

										t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
										t1.setNbreSup15(n-(9+t1.getNbreSup50()));
										if(regarde+t1.getNbreSup15()>8) {
											t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
											t1.setNbreSup15(8-regarde);
											if(regarde2+t1.getNbreSup40()>6) {
												t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
												t1.setNbreSup40(6-regarde2);
											}

										}
									}
								 	else if (regarde==8&&regarde2<6){
										t1.setNbreHtNormale(9);

										t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
										t1.setNbreSup40(n-(9+t1.getNbreSup50()));
										if(regarde2+t1.getNbreSup40()>6) {
											t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
											t1.setNbreSup40(6-regarde2);
										}

									}
								 	else {
								 		t1.setNbreHtNormale(9);
								 		t1.setNbreSup50(n-9);
									}

								 }
								 else {
								 	if (regarde<8){
										t1.setNbreHtNormale(9);

										t1.setNbreSup50(NombreHeureJours(79200,18000));

										t1.setNbreSup15(n-(9+t1.getNbreSup50())+NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
										if(regarde+t1.getNbreSup15()>8) {
											t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
											t1.setNbreSup15(8-regarde);
											if(regarde2+t1.getNbreSup40()>6) {
												t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
												t1.setNbreSup40(6-regarde2);
											}

										}
									}
								 	else  if (regarde==8&&regarde2<6){
										t1.setNbreHtNormale(9);

										t1.setNbreSup50(NombreHeureJours(79200,18000));

										t1.setNbreSup40(n-(9+t1.getNbreSup50())+NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
										if(regarde2+t1.getNbreSup40()>6) {
											t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
											t1.setNbreSup40(6-regarde2);
										}
									}
								 	else {
								 		t1.setNbreSup50(n-9);
								 		t1.setNbreHtNormale(9);
									}




								 }

							 }
						 }
					 }
				 }
			 }

			 
			 
		 }
		 else {
			 if(n<=9) {
				 t1.setNbreHtNormale(n);

			 }
			 else {
				 if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=17&&t1.getDatedebut().getHours()<t1.getDatefint().getHours())
				 {
					 if(n<=9) {
						 t1.setNbreHtNormale(n);

					 }
					 else {
					 	if (regarde<8){
							t1.setNbreHtNormale(9);
							t1.setNbreSup15(n-9);
							if(regarde+t1.getNbreSup15()>8) {
								t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
								t1.setNbreSup15(8-regarde);
								if(regarde2+t1.getNbreSup40()>6) {
									t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
									t1.setNbreSup40(6-regarde2);
								}

							}
						}
					 	else if (regarde==8&&regarde2<6){
							t1.setNbreHtNormale(9);
							t1.setNbreSup40(n-9);
							if(regarde2+t1.getNbreSup40()>6) {
								t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
								t1.setNbreSup40(6-regarde2);
							}
						}
					 	else {
					 		t1.setNbreHtNormale(9);
					 		t1.setNbreSup50(n-9);
						}

					 }

				 }
				 else if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=23&&t1.getDatedebut().getHours()<t1.getDatefint().getHours()&&t1.getDatefint().getHours()>17)
				 {
					 if(t1.getDatefint().getHours()==23) {
					 	if (regarde<8){
							t1.setNbreHtNormale(9);

							t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
							t1.setNbreSup15(n-9-t1.getNbreSup50());
							if(regarde+t1.getNbreSup15()>8) {
								t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
								t1.setNbreSup15(8-regarde);
								if(regarde2+t1.getNbreSup40()>6) {
									t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
									t1.setNbreSup40(6-regarde2);
								}

							}
						}
					 	else if (regarde==8&&regarde2<6){
							t1.setNbreHtNormale(9);

							t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
							t1.setNbreSup40(n-9-t1.getNbreSup50());
							if(regarde2+t1.getNbreSup40()>6) {
								t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
								t1.setNbreSup40(6-regarde2);
							}
						}
					 	else {
					 		t1.setNbreHtNormale(9);
					 		t1.setNbreSup50(n-9);
						}


					 }
					 else {
					 	if (regarde<8){
							t1.setNbreHtNormale(9);
							t1.setNbreSup15(n-9);
							if(regarde+t1.getNbreSup15()>8) {
								t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
								t1.setNbreSup15(8-regarde);
								if(regarde2+t1.getNbreSup40()>6) {
									t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
									t1.setNbreSup40(6-regarde2);
								} }

						}
					 	else if (regarde==8&&regarde2<6){
							t1.setNbreHtNormale(9);
							t1.setNbreSup40(n-9);

							if(regarde2+t1.getNbreSup40()>6) {
								t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
								t1.setNbreSup40(6-regarde2);
							}
						}
					 	else {
					 		t1.setNbreSup50(n-9);
					 		t1.setNbreHtNormale(9);
						}


					 }
				 }
				 else {
				 	if (regarde<8){
						t1.setNbreHtNormale(9);
						t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
						t1.setNbreSup15(n-9-t1.getNbreSup50());
						if(regarde+t1.getNbreSup15()>8) {
							t1.setNbreSup40(regarde+t1.getNbreSup15()-8);
							t1.setNbreSup15(8-regarde);
							if(regarde2+t1.getNbreSup40()>6) {
								t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
								t1.setNbreSup40(6-regarde2);
							}
						}
					}
				 	else if (regarde==8&&regarde2<6){
						t1.setNbreHtNormale(9);
						t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
						t1.setNbreSup40(n-9-t1.getNbreSup50());
						if(regarde2+t1.getNbreSup40()>6) {
							t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
							t1.setNbreSup40(6-regarde2);
						}
					}
				 	else {
				 		t1.setNbreHtNormale(9);
				 		t1.setNbreSup50(n-9);
					}


				 }


			 }
		 }
		 
		 
		return t1;
	}
*/

	@Override
	public Taches vider(Taches t) {
		// TODO Auto-generated method stub
		return null;
	}
/*******************************************************************************************************************************/
	  @Override
	  public Taches HeureSupSecuriter(Taches t1, Taches t2) {
		/*
		 * int n=NombreHeureJours(ConverteDate(t1.getDatedebut()),ConverteDate(t1.
		 * getDatefint()));
		 * System.out.println("ona rentrer dans la fonction HeureSupSecuriter");
		 * t1.setTotal_Heure(n); if (t1!=null &&t2==null){
		 * System.out.println("tach2==null"); t1.setNbreHtNormale(n);
		 * 
		 * 
		 * } else if (t1!=null&&tachesim.DebutSemaine(t1.getDateday())==null){
		 * System.out.println("tdebut wekkend"); t1.setNbreHtNormale(n);
		 * 
		 * } else {System.out.println("dans le plus part ");
		 * 
		 * Date debutwekend=tachesim.DebutSemaine(t1.getDateday()); int
		 * regarde=tachesim.SommeSup15(debutwekend, t1.getDateday(),
		 * t1.getEmployer().getId()); int regarde2=tachesim.SommeSup40(debutwekend,
		 * t1.getDateday(), t1.getEmployer().getId()); int
		 * nbreHeureSecurity=tachesim.SommeHeureNormaleSecuriter(t1.getEmployer().getId(
		 * ),debutwekend,t1.getDateday());
		 * 
		 * t1.setTotal_Heure(n); if ((nbreHeureSecurity+n)<=40){ t1.setNbreHtNormale(n);
		 * 
		 * } else { if (nbreHeureSecurity==40){
		 * t1=this.HeureSupSecuriterparticuliers(t1,t2);
		 * 
		 * 
		 * } else { System.out.println("dans la plus part "); if
		 * (t1.getDatedebut().getHours()>0){ System.out.println("commence >0 "); if
		 * (t1.getDatedebut().getHours()<5){ System.out.println("commence <5"); if
		 * (t1.getDatefint().getHours()<=23&&t1.getDatefint().getHours()>t1.getDatedebut
		 * ().getHours()) { if (t1.getDatefint().getHours()==23){ if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),79200)>40
		 * ){ t1.setNbreSup50(1); t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreSup50()-t1.getNbreHtNormale());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } } else { t1.setNbreSup50(1); t1.setNbreHtNormale(n-1);
		 * 
		 * } } else { t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreHtNormale()); if(t1.getNbreSup15()>8){
		 * t1.setNbreSup40(t1.getNbreSup15()-8); t1.setNbreSup15(8); if
		 * (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * }
		 * 
		 * }
		 * 
		 * }
		 * 
		 * 
		 * else if
		 * (t1.getDatefint().getHours()<=8&&t1.getDatedebut().getHours()<t1.getDatefint(
		 * ).getHours()) { if (t1.getDatefint().getHours()<=5) { t1.setNbreHtNormale(40
		 * - nbreHeureSecurity); t1.setNbreSup50(n - t1.getNbreHtNormale()); } else { if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),18000)<40
		 * ){ t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreHtNormale()); if(t1.getNbreSup15()>8){
		 * t1.setNbreSup40(t1.getNbreSup15()-8); t1.setNbreSup15(8); if
		 * (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } } else if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),18000)==
		 * 40){
		 * 
		 * t1.setNbreSup15(NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
		 * t1.setNbreHtNormale(n-t1.getNbreSup15()); if(t1.getNbreSup15()>8){
		 * t1.setNbreSup40(t1.getNbreSup15()-8); t1.setNbreSup15(8); if
		 * (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } } else {
		 * t1.setNbreSup15(NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
		 * t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup50(n-t1.getNbreSup15()-t1.getNbreSup50());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * }
		 * 
		 * }
		 * 
		 * }
		 * 
		 * 
		 * 
		 * } else { System.out.println("on a df=0"); if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),79200)<40
		 * ){ System.out.println("on a apre 22 on a 40");
		 * t1.setNbreHtNormale(40-nbreHeureSecurity) ; t1.setNbreSup50(n-
		 * t1.getNbreHtNormale());
		 * 
		 * 
		 * } else if
		 * (nbreHeureSecurity+NombreHeureJours(
t1.getDatedebut()),79200)==
		 * 40){ t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
		 * t1.setNbreHtNormale(n-t1.getNbreSup50());
		 * System.out.println("on a  22 on a 40");
		 * 
		 * } else { System.out.println("nbreheure"+nbreHeureSecurity);
		 * t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
		 * t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreSup50()-t1.getNbreHtNormale());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } System.out.println("on a avant 22 on a 40"); }
		 * 
		 * 
		 * 
		 * }
		 * 
		 * } else if (t1.getDatedebut().getHours()==5){
		 * System.out.println("commence de 5"); if (nbreHeureSecurity+n<=40){
		 * t1.setNbreHtNormale(n); } else{ if (nbreHeureSecurity==40){
		 * t1=this.HeureSupSecuriterparticuliers(t1,t2);
		 * 
		 * } else {
		 * if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=23&&t1.
		 * getDatedebut().getHours()<t1.getDatefint().getHours()) { if
		 * (t1.getDatefint().getHours()==23){ if
		 * ((nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),79200)>
		 * 40)){ t1.setNbreSup50(1); t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreSup50()-t1.getNbreHtNormale());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * }
		 * 
		 * } else{ t1.setNbreSup50(1); t1.setNbreHtNormale(n-1); } } else{
		 * t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreHtNormale()); if(t1.getNbreSup15()>8){
		 * t1.setNbreSup40(t1.getNbreSup15()-8); t1.setNbreSup15(8); if
		 * (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * }
		 * 
		 * } } else if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=t1.
		 * getDatedebut().getHours()) {
		 * 
		 * if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),79200)>40
		 * ){ t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
		 * t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreSup50()-t1.getNbreHtNormale());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } } else{ t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup50(n-t1.getNbreHtNormale()); }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * } else { System.out.println("commence de 05 -00"); if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),79200)>40
		 * ){ t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
		 * t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreSup50()-t1.getNbreHtNormale());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } } else{ t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreHtNormale()); if(t1.getNbreSup15()>8){
		 * t1.setNbreSup40(t1.getNbreSup15()-8); t1.setNbreSup15(8); if
		 * (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } } }
		 * 
		 * } }
		 * 
		 * 
		 * 
		 * } else if (t1.getDatedebut().getHours()==8){
		 * System.out.println("commence de 08"); if (nbreHeureSecurity+n<=40){
		 * t1.setNbreHtNormale(n); } else { if (nbreHeureSecurity==40){
		 * 
		 * 
		 * return HeureSupSecuriterparticuliers(t1,t2); } else {
		 * if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=23&&t1.
		 * getDatedebut().getHours()<t1.getDatefint().getHours()) { if
		 * (t1.getDatefint().getHours() == 23) { if ((nbreHeureSecurity +
		 * NombreHeureJours(ConverteDate(t1.getDatedebut()), 79200) > 40)) {
		 * t1.setNbreSup50(1); t1.setNbreHtNormale(40 - nbreHeureSecurity);
		 * t1.setNbreSup15(n - t1.getNbreSup50() - t1.getNbreHtNormale());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } } else { t1.setNbreSup50(1); t1.setNbreHtNormale(n - 1); } } else {
		 * 
		 * t1.setNbreHtNormale(40 - nbreHeureSecurity); t1.setNbreSup15(n -
		 * t1.getNbreHtNormale()); if (t1.getNbreSup15() > 8) {
		 * t1.setNbreSup40(t1.getNbreSup15() - 8); t1.setNbreSup15(8); if
		 * (t1.getNbreSup40() > 6) { t1.setNbreSup50(t1.getNbreSup40() - 6 +
		 * t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * }
		 * 
		 * } } else if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=t1.
		 * getDatedebut().getHours()) { if (t1.getDatefint().getHours()<=5){ if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),79200)>40
		 * ){ t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
		 * t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreSup50()-t1.getNbreHtNormale());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } }
		 * 
		 * else{ t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreHtNormale()); if(t1.getNbreSup15()>8){
		 * t1.setNbreSup40(t1.getNbreSup15()-8); t1.setNbreSup15(8); if
		 * (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } }
		 * 
		 * 
		 * 
		 * } else { if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),79200)>40
		 * ){ t1.setNbreSup50(NombreHeureJours(79200,18000));
		 * t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreSup50()-t1.getNbreHtNormale());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } } else{ if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),18000)>40
		 * ){ t1.setNbreSup15(NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
		 * t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup50(n-t1.getNbreSup15()-t1.getNbreHtNormale());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } } else { t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreHtNormale()); if(t1.getNbreSup15()>8){
		 * t1.setNbreSup40(t1.getNbreSup15()-8); t1.setNbreSup15(8); if
		 * (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } }
		 * 
		 * }
		 * 
		 * }
		 * 
		 * } else { System.out.println("commence de 08-00"); if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),79200)>40
		 * ){ t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
		 * t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreSup50()-t1.getNbreHtNormale());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } } else{ t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup50(n-t1.getNbreHtNormale());
		 * 
		 * }
		 * 
		 * 
		 * }
		 * 
		 * 
		 * 
		 * 
		 * }
		 * 
		 * }
		 * 
		 * 
		 * } else { System.out.println("commence autre cas "); if
		 * (nbreHeureSecurity+n<=40){ t1.setNbreHtNormale(n);
		 * 
		 * } else { if (nbreHeureSecurity==40){
		 * t1=this.HeureSupSecuriterparticuliers(t1,t2);
		 * 
		 * } else { if
		 * (t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=23&&t1.
		 * getDatefint().getHours()>t1.getDatedebut().getHours()){ if
		 * (t1.getDatefint().getHours()==23){ if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),79200)>40
		 * ){ t1.setNbreHtNormale(40-nbreHeureSecurity); t1.setNbreSup50(1);
		 * t1.setNbreSup15(n-t1.getNbreHtNormale()-1); if(t1.getNbreSup15()>8){
		 * t1.setNbreSup40(t1.getNbreSup15()-8); t1.setNbreSup15(8); if
		 * (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } } else{ t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup50(n-t1.getNbreHtNormale()); }
		 * 
		 * } else {
		 * 
		 * t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * 
		 * t1.setNbreSup15(n-t1.getNbreHtNormale()); if(t1.getNbreSup15()>8){
		 * t1.setNbreSup40(t1.getNbreSup15()-8); t1.setNbreSup15(8); if
		 * (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * }
		 * 
		 * 
		 * 
		 * }
		 * 
		 * } else if (t1.getDatefint().getHours()==0){ if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),79200)>40
		 * ){ t1.setNbreHtNormale(40-nbreHeureSecurity); t1.setNbreSup50(2);
		 * t1.setNbreSup15(n-t1.getNbreHtNormale()-2); if(t1.getNbreSup15()>8){
		 * t1.setNbreSup40(t1.getNbreSup15()-8); t1.setNbreSup15(8); if
		 * (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } } else{ t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup50(n-t1.getNbreHtNormale()); }
		 * 
		 * 
		 * 
		 * } else if (t1.getDatefint().getHours()<=5){ if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),79200)>40
		 * ){ t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
		 * t1.setNbreSup15(n-t1.getNbreHtNormale()-t1.getNbreSup50());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } } else{ t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup50(n-t1.getNbreHtNormale()); }
		 * 
		 * 
		 * } else { if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),79200)>40
		 * ){ t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
		 * t1.setNbreSup15(n-t1.getNbreHtNormale()-t1.getNbreSup50());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * }
		 * 
		 * } else if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),18000)>40
		 * ){ t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
		 * t1.setNbreSup50(n-t1.getNbreSup15()-t1.getNbreHtNormale());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * }
		 * 
		 * 
		 * } else { t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreHtNormale()); if(t1.getNbreSup15()>8){
		 * t1.setNbreSup40(t1.getNbreSup15()-8); t1.setNbreSup15(8); if
		 * (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } }
		 * 
		 * 
		 * }
		 * 
		 * 
		 * }
		 * 
		 * }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * }
		 * 
		 * } else { System.out.println("commence de 0"); if (nbreHeureSecurity+n<=40){
		 * t1.setNbreHtNormale(n); } else { if (nbreHeureSecurity==40){
		 * t1=this.HeureSupSecuriterparticuliers(t1,t2);
		 * 
		 * } else {
		 * if(t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=23&&t1.
		 * getDatedebut().getHours()<t1.getDatefint().getHours()&&t1.getDatefint().
		 * getHours()>5) { if (t1.getDatefint().getHours()==23){ if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),18000)>40
		 * ){ t1.setNbreSup15(NombreHeureJours(18000,79200));
		 * t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup50(n-t1.getNbreHtNormale()-t1.getNbreSup15());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * }
		 * 
		 * } else { if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),79200)>40
		 * ){ t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
		 * t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreHtNormale()-t1.getNbreSup50());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * }
		 * 
		 * } else {
		 * 
		 * t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup50(n-t1.getNbreHtNormale()); }
		 * 
		 * 
		 * }
		 * 
		 * 
		 * } else { if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),18000)>40
		 * ){ t1.setNbreSup15(NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
		 * t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup50(n-t1.getNbreHtNormale()-t1.getNbreSup15());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * }
		 * 
		 * } else { t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreHtNormale()); if(t1.getNbreSup15()>8){
		 * t1.setNbreSup40(t1.getNbreSup15()-8); t1.setNbreSup15(8); if
		 * (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * }
		 * 
		 * }
		 * 
		 * 
		 * 
		 * }
		 * 
		 * } else { if (t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=5){
		 * t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup50(n-t1.getNbreHtNormale()); } else{ if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),18000)>
		 * 40) { t1.setNbreSup15(NombreHeureJours(18000,79200));
		 * t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup50(n-t1.getNbreHtNormale()-t1.getNbreSup15());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * } } else if
		 * (nbreHeureSecurity+NombreHeureJours(ConverteDate(t1.getDatedebut()),79200)>
		 * 40) {
		 * t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
		 * t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup15(n-t1.getNbreSup50()-t1.getNbreHtNormale());
		 * if(t1.getNbreSup15()>8){ t1.setNbreSup40(t1.getNbreSup15()-8);
		 * t1.setNbreSup15(8); if (t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50()); t1.setNbreSup40(6); }
		 * 
		 * }
		 * 
		 * 
		 * 
		 * } else { t1.setNbreHtNormale(40-nbreHeureSecurity);
		 * t1.setNbreSup50(n-t1.getNbreSup50());
		 * 
		 * 
		 * }
		 * 
		 * 
		 * }
		 * 
		 * 
		 * }
		 * 
		 * }
		 * 
		 * 
		 * }
		 * 
		 * 
		 * 
		 * }
		 * 
		 * }
		 * 
		 * 
		 * 
		 * }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * return t1;
		 */
		  return null;
	  }

	  @Override
	  public Taches HeureSupSecuriterparticuliers(Taches t1, Taches t2) {
		/*
		 * int n=NombreHeureJours(ConverteDate(t1.getDatedebut()),ConverteDate(t1.
		 * getDatefint())); System.out.
		 * println("ona rentrer dans la fonction HeureSupSecuriterparticuliers");
		 * t1.setTotal_Heure(n); Date
		 * debutwekend=tachesim.DebutSemaine(t1.getDateday());
		 * 
		 * int regarde=tachesim.SommeSup15(debutwekend, t1.getDateday(),
		 * t1.getEmployer().getId()); int regarde2=tachesim.SommeSup40(debutwekend,
		 * t1.getDateday(), t1.getEmployer().getId()); if
		 * (t1.getDatedebut().getHours()>0){ if (t1.getDatedebut().getHours()<5){ if
		 * (t1.getDatefint().getHours()>0&&t1.getDatedebut().getHours()<=23&&t1.
		 * getDatedebut().getHours()<t1.getDatefint().getHours()) { if
		 * (t1.getDatefint().getHours()==23){ if (regarde<8) {
		 * t1.setNbreSup15(NombreHeureJours(18000, 79200)); t1.setNbreSup50(n -
		 * t1.getNbreSup15()); if (regarde+t1.getNbreSup15()>8){
		 * t1.setNbreSup40(regarde+t1.getNbreSup15()-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); } } } else if (regarde==8&&regarde2<6){
		 * t1.setNbreSup40(NombreHeureJours(18000, 79200)); t1.setNbreSup50(n -
		 * t1.getNbreSup15()); if (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); } } else { t1.setNbreSup50(n); } } else { if
		 * (regarde<8) {
		 * t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000));
		 * t1.setNbreSup15(n - t1.getNbreSup50()); if (regarde+t1.getNbreSup15()>8){
		 * t1.setNbreSup40(regarde+t1.getNbreSup15()-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup40(t1.getNbreSup50()+regarde2-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); } } } else if (regarde==8&&regarde2<6){
		 * t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000));
		 * t1.setNbreSup40(n - t1.getNbreSup50()); if (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); } } else { t1.setNbreSup50(n); } }
		 * 
		 * } else if (t1.getDatefint().getHours()<=5&&t1.getDatefint().getHours()>0){
		 * t1.setNbreSup50(n);
		 * 
		 * } else { if (regarde<8){ t1.setNbreSup15(NombreHeureJours(18000,79200));
		 * t1.setNbreSup50(n-t1.getNbreSup15()); if (regarde+t1.getNbreSup15()>8){
		 * t1.setNbreSup40(t1.getNbreSup15()+regarde-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t2.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2);
		 * 
		 * 
		 * } }
		 * 
		 * } else if (regarde==8&&regarde2<6){
		 * t1.setNbreSup40(NombreHeureJours(18000,79200));
		 * t1.setNbreSup50(n-t1.getNbreSup15()); if (regarde2+t2.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2);
		 * 
		 * 
		 * } } else { t1.setNbreSup50(n); }
		 * 
		 * 
		 * }
		 * 
		 * } else if (t1.getDatedebut().getHours()==5){ if
		 * (t1.getDatefint().getHours()>0&&t1.getDatedebut().getHours()<=23&&t1.
		 * getDatedebut().getHours()<t1.getDatefint().getHours()) { if
		 * (t1.getDatefint().getHours()==23){ if (regarde<8) {
		 * t1.setNbreSup15(NombreHeureJours(18000, 79200)); t1.setNbreSup50(n -
		 * t1.getNbreSup15()); if (regarde+t1.getNbreSup15()>8){
		 * t1.setNbreSup40(t1.getNbreSup15()+regarde-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); } } } else if (regarde==8&&regarde2<6){
		 * t1.setNbreSup40(NombreHeureJours(18000, 79200)); t1.setNbreSup50(n -
		 * t1.getNbreSup15()); if (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); }
		 * 
		 * } else { t1.setNbreSup50(n); } } else { if (regarde<8) {
		 * 
		 * t1.setNbreSup15(n); if (t1.getNbreSup15()+regarde>8){
		 * t1.setNbreSup40(regarde+t1.getNbreSup15()-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6); t1.setNbreSup40(6-regarde2); }
		 * } } else if(regarde==8&&regarde2<6){ t1.setNbreSup40(n); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6); t1.setNbreSup40(6-regarde2); }
		 * } else { t1.setNbreSup50(n); }
		 * 
		 * }
		 * 
		 * } else if (t1.getDatefint().getHours()<=5&&t1.getDatefint().getHours()>0){ if
		 * (t1.getDatefint().getHours()==5){ if (regarde<8) {
		 * t1.setNbreSup50(NombreHeureJours(79200, 18000)); t1.setNbreSup15(n -
		 * t1.getNbreSup50()); if (regarde+t1.getNbreSup15()>8){
		 * t1.setNbreSup40(t1.getNbreSup15()+regarde-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2);
		 * 
		 * } } } else if (regarde==8&&regarde2<6){
		 * t1.setNbreSup50(NombreHeureJours(79200, 18000)); t1.setNbreSup40(n -
		 * t1.getNbreSup50()); if (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2);
		 * 
		 * }
		 * 
		 * } else { t1.setNbreSup50(n); } } else { if (regarde<8) {
		 * t1.setNbreSup50(NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
		 * 
		 * t1.setNbreSup15(n - t1.getNbreSup50()); if (regarde+t1.getNbreSup15()>8){
		 * t1.setNbreSup40(regarde+t1.getNbreSup15()-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * 
		 * t1.setNbreSup40(6-regarde2);
		 * 
		 * }
		 * 
		 * 
		 * 
		 * 
		 * } } else if (regarde==8&&regarde2>6){ t1.setNbreSup50(NombreHeureJours(79200,
		 * ConverteDate(t1.getDatefint())));
		 * 
		 * t1.setNbreSup40(n - t1.getNbreSup50()); if (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * 
		 * t1.setNbreSup40(6-regarde2);
		 * 
		 * } } else { t1.setNbreSup50(n); } }
		 * 
		 * } else { if (regarde<8){
		 * t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
		 * 
		 * t1.setNbreSup15(n-t1.getNbreSup50()); if (regarde+t1.getNbreSup15()>8){
		 * t1.setNbreSup40(regarde+t1.getNbreSup15()-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); } } } else if (regarde==8&&regarde2<6){
		 * t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
		 * 
		 * t1.setNbreSup40(n-t1.getNbreSup50()); if (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); }
		 * 
		 * } else { t1.setNbreSup50(n); }
		 * 
		 * }
		 * 
		 * 
		 * } else if (t1.getDatedebut().getHours()==8){ if
		 * (t1.getDatefint().getHours()>0&&t1.getDatedebut().getHours()<=23&&t1.
		 * getDatedebut().getHours()<t1.getDatefint().getHours()) { if
		 * (t1.getDatefint().getHours()==23){ if (regarde<8) { t1.setNbreSup50(1);
		 * t1.setNbreSup15(n - 1); if (regarde+t1.getNbreSup15()>8){
		 * t1.setNbreSup40(regarde+t1.getNbreSup15()-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2);
		 * 
		 * }
		 * 
		 * }
		 * 
		 * } else if (regarde==8&&regarde2<6){ t1.setNbreSup50(1); t1.setNbreSup40(n -
		 * 1); if (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2);
		 * 
		 * }
		 * 
		 * } else { t1.setNbreSup50(n); } } else { if (regarde<8) {
		 * 
		 * t1.setNbreSup15(n); if (t1.getNbreSup15()+regarde>8){
		 * t1.setNbreSup40(regarde+t1.getNbreSup15()-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6); t1.setNbreSup40(6-regarde2);
		 * 
		 * }
		 * 
		 * } } else if (regarde==8&&regarde2<6){ t1.setNbreSup40(n); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6); t1.setNbreSup40(6-regarde2);
		 * 
		 * }
		 * 
		 * } else { t1.setNbreSup50(n); }
		 * 
		 * }
		 * 
		 * } else if (t1.getDatefint().getHours()<=5&&t1.getDatefint().getHours()>0){ if
		 * (t1.getDatefint().getHours()==5){ if (regarde<8) {
		 * t1.setNbreSup50(NombreHeureJours(79200, 18000)); t1.setNbreSup15(n -
		 * t1.getNbreSup50()); if (regarde+t1.getNbreSup15()>8){
		 * t1.setNbreSup40(regarde+t1.getNbreSup15()-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); } }
		 * 
		 * } else if (regarde==8&&regarde2<6){ t1.setNbreSup50(NombreHeureJours(79200,
		 * 18000)); t1.setNbreSup40(n - t1.getNbreSup50()); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); }
		 * 
		 * }
		 * 
		 * else{ t1.setNbreSup50(n); }
		 * 
		 * } else { if (regarde<8) { t1.setNbreSup50(NombreHeureJours(79200,
		 * ConverteDate(t1.getDatefint())));
		 * 
		 * t1.setNbreSup15(n - t1.getNbreSup50()); if (regarde+t1.getNbreSup15()>8){
		 * t1.setNbreSup40(regarde+t1.getNbreSup15()-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); } }
		 * 
		 * } else if (regarde==8&&regarde2<6){ t1.setNbreSup50(NombreHeureJours(79200,
		 * ConverteDate(t1.getDatefint())));
		 * 
		 * t1.setNbreSup40(n - t1.getNbreSup50()); if (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); }
		 * 
		 * } else { t1.setNbreSup50(n); }
		 * 
		 * }
		 * 
		 * } else if (t1.getDatefint().getHours()==0){ if (regarde<8) {
		 * t1.setNbreSup50(2); t1.setNbreSup15(n - 2); if (regarde+t1.getNbreSup15()>8){
		 * t1.setNbreSup40(regarde+t1.getNbreSup15()-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); } }
		 * 
		 * } else if (regarde==8&&regarde2<6){ t1.setNbreSup50(2); t1.setNbreSup40(n -
		 * 2); if (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); }
		 * 
		 * } else { t1.setNbreSup50(n); }
		 * 
		 * }
		 * 
		 * 
		 * }
		 * 
		 * 
		 * 
		 * else { if (t1.getDatefint().getHours()>0&&t1.getDatefint().getHours()<=5){ if
		 * (regarde<8) { t1.setNbreSup50(NombreHeureJours(79200,
		 * ConverteDate(t1.getDatefint())));
		 * 
		 * t1.setNbreSup15(n - t1.getNbreSup50()); if (regarde+t1.getNbreSup15()>8){
		 * t1.setNbreSup40(regarde+t1.getNbreSup15()-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); } }
		 * 
		 * 
		 * } else if (regarde==8&&regarde2<6){ t1.setNbreSup50(NombreHeureJours(79200,
		 * ConverteDate(t1.getDatefint())));
		 * 
		 * t1.setNbreSup40(n - t1.getNbreSup50()); if (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); }
		 * 
		 * } else { t1.setNbreSup50(n); }
		 * 
		 * } else if
		 * (t1.getDatefint().getHours()>0&&t1.getDatedebut().getHours()<=23&&t1.
		 * getDatedebut().getHours()<t1.getDatefint().getHours()) { if
		 * (t1.getDatefint().getHours()==23){ if (regarde<8) {
		 * 
		 * t1.setNbreSup50(1); t1.setNbreSup15(n - 1); if (regarde+t1.getNbreSup15()>8){
		 * t1.setNbreSup40(regarde+t1.getNbreSup15()-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); } } } else if (regarde==8&&regarde2<6){
		 * t1.setNbreSup50(1); t1.setNbreSup40(n - 1); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); }
		 * 
		 * } else { t1.setNbreSup50(n); } } else {
		 * 
		 * t1.setNbreSup15(n);
		 * 
		 * }
		 * 
		 * }
		 * 
		 * else { if (regarde<8) { t1.setNbreSup50(NombreHeureJours(79200, 18000));
		 * 
		 * t1.setNbreSup15(n - t1.getNbreSup50()); if (regarde+t1.getNbreSup15()>8){
		 * t1.setNbreSup40(regarde+t1.getNbreSup15()-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); } } } else if (regarde==8&&regarde2<6){
		 * t1.setNbreSup50(NombreHeureJours(79200, 18000));
		 * 
		 * t1.setNbreSup40(n - t1.getNbreSup50()); if (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); }
		 * 
		 * 
		 * 
		 * } else { t1.setNbreSup50(n); } } }
		 * 
		 * 
		 * } else { if
		 * (t1.getDatefint().getHours()>0&&t1.getDatedebut().getHours()<=23&&t1.
		 * getDatedebut().getHours()<t1.getDatefint().getHours()) { if
		 * (t1.getDatefint().getHours()==23){ if (regarde<8) {
		 * t1.setNbreSup15(NombreHeureJours(18000, 79200)); t1.setNbreSup50(n -
		 * t1.getNbreSup15()); if (regarde+t1.getNbreSup15()>8){
		 * t1.setNbreSup40(regarde+t1.getNbreSup15()-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); } } } else if (regarde==8&&regarde2<6){
		 * t1.setNbreSup40(NombreHeureJours(18000, 79200)); t1.setNbreSup50(n -
		 * t1.getNbreSup15()); if (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2); }
		 * 
		 * 
		 * 
		 * 
		 * } else { t1.setNbreSup50(n); }
		 * 
		 * 
		 * } else if (t1.getDatefint().getHours()<=5&&t1.getDatefint().getHours()>0){
		 * 
		 * t1.setNbreSup50(n); } else { System.out.println("on a rentre de 0 "); if
		 * (regarde<8){ t1.setNbreSup50(NombreHeureJours(0,18000));
		 * t1.setNbreSup15(n-t1.getNbreSup50()); if (regarde+t1.getNbreSup15()>8){
		 * t1.setNbreSup40(regarde+t1.getNbreSup15()-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2);
		 * 
		 * }
		 * 
		 * }
		 * 
		 * } else if (regarde==8&&regarde2<6){
		 * t1.setNbreSup50(NombreHeureJours(0,18000));
		 * t1.setNbreSup40(n-t1.getNbreSup50()); if (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2);
		 * 
		 * }
		 * 
		 * 
		 * } else { t1.setNbreSup50(n); }
		 * 
		 * }
		 * 
		 * 
		 * } else { if (regarde<8) { t1.setNbreSup15(NombreHeureJours(18000, 79200));
		 * t1.setNbreSup50(n - t1.getNbreSup15()); if (regarde+t1.getNbreSup15()>8){
		 * t1.setNbreSup40(regarde+t1.getNbreSup15()-8); t1.setNbreSup15(8-regarde); if
		 * (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2);
		 * 
		 * } } } else if (regarde==8&&regarde2<6){
		 * t1.setNbreSup40(NombreHeureJours(18000, 79200)); t1.setNbreSup50(n -
		 * t1.getNbreSup15()); if (regarde2+t1.getNbreSup40()>6){
		 * t1.setNbreSup50(regarde2+t1.getNbreSup40()-6+t1.getNbreSup50());
		 * t1.setNbreSup40(6-regarde2);
		 * 
		 * }
		 * 
		 * } else { t1.setNbreSup50(n); } }
		 * 
		 * 
		 * }
		 * 
		 */
		  return t1;
	  }

	  @Override
	  public int CalulePanier(Taches t) {
	  	int n=0;
	  	if(t.getDateday().getDay()==5) {
	  		if(NombreHeureJours(ConverteDate(t.getDatedebut()),ConverteDate(t.getDatefint()))>=9) {
	  			n=n+1;
	  		}
	  		
	  		
	  	}
	  	else if (NombreHeureJours(ConverteDate(t.getDatedebut()),ConverteDate(t.getDatefint()))>=9)
	  	{
	  		n=n+1;
		}
	  	else {
	  		n=0;
	  	}
		  return n;
	  }

	  @Override
	public Taches TacheWeekend(Taches t) {
		// TODO Auto-generated method stub
          int n=NombreHeureJours(ConverteDate(t.getDatedebut()),ConverteDate(t.getDatefint()));
          System.out.println("ona rentrer dans la fonction TacheWeekend");
          t.setTotal_Heure(n);
      	if(t.getDatedebut().getHours()>=5){
			if(t.getDatefint().getHours()>t.getDatedebut().getHours()){
				if(t.getDatefint().getHours()<23){

					t.setNbreSup50(n);


				}
				else{

				
					t.setNbreSup100(1);
					t.setNbreSup50(n-1);

				}

			}
			else{
				if(t.getDatefint().getHours()<=5){
					
					t.setNbreSup100(NombreHeureJours(79200,ConverteDate(t.getDatefint())));
					t.setNbreSup50(n-t.getNbreSup100());
				}
				else{
				
					t.setNbreSup100(NombreHeureJours(79200,18000));
					t.setNbreSup50(n-t.getNbreSup100());

				}




			}




		}
		else{
			if(t.getDatefint().getHours()<=5){
				
				t.setNbreSup100(NombreHeureJours(ConverteDate(t.getDatedebut()),18000)+NombreHeureJours(79200,ConverteDate(t.getDatefint())));
				t.setNbreSup50(n-t.getNbreSup100());


			}
			else{
				if(t.getDatefint().getHours()==23){
				
					t.setNbreSup100(NombreHeureJours(ConverteDate(t.getDatedebut()),18000)+NombreHeureJours(79200,ConverteDate(t.getDatefint())));
					t.setNbreSup50(n-t.getNbreSup100());
				}
				else{
				
					t.setNbreSup100(NombreHeureJours(ConverteDate(t.getDatedebut()),18000));
					t.setNbreSup50(n-t.getNbreSup100());

				}

			}

		}
          
          
        

		return t;
	}

	@Override
	public int ConverteDate(Date t) {
		// TODO Auto-generated method stub
		int nb=(t.getHours()*3600)+(t.getMinutes()*60);
		
		return nb;
	}

	@Override
	public int NUmeroweekend(Date d1) {
		// TODO Auto-generated method stub
		  Calendar calendar=Calendar.getInstance();
		  calendar.setTime(d1);
		  
		return calendar.get(calendar.WEEK_OF_YEAR);
	}

	@Override
	public Taches SecuriryDebut(Taches t1) {
		// TODO Auto-generated method stub
		int n=NombreHeureJours(ConverteDate(t1.getDatedebut()), ConverteDate(t1.getDatefint()));
		t1.setTotal_Heure(n);
		
		return t1;
	}

	@Override
	public Taches SecuritySuplementaire(Taches t1, Taches t2) throws ParseException {
		// TODO Auto-generated method stub

		 SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd");


		  int n=NombreHeureJours(ConverteDate(t1.getDatedebut()), ConverteDate(t1.getDatefint()));
		 
			
		  int regarde=0;
		  int regarde2=0;
		  String d1=formatter6.format(DebutWeekend(t2.getDateday()));
		  //int p=CalulePanier(t1);
		  Date debutwekend=formatter6.parse(d1);
		  System.out.println("debuweekend"+d1);
		  regarde=tachesim.SommeSup15(debutwekend,t1.getDateday(),t1.getEmployer().getId());
		  regarde2=tachesim.SommeSup40(debutwekend,t1.getDateday(),t1.getEmployer().getId());
		  int ht=tachesim.SommeHt(debutwekend, t1.getDateday(), t1.getEmployer().getId());
		  System.out.println("Nombre15"+":"+regarde);
		  System.out.println("Nombre40"+":"+regarde2);
		  t1.setTotal_Heure(n);
		//  t1.setPanier(p);
		  
		  if(ht+n<=40) {
			  t1.setTotal_Heure(n);
		  }
		  else {
			
			  if(ht>=40) {
				  System.out.println("on est avec ht>=40");
				  if(t1.getDateday().getDay()==6||t1.getDateday().getDay()==0) {
					
					  return  TacheWeekend(t1);
					 
				  }
				  else {
					  t1=MemeJours(t1, t2);
					  t1.setNbreHtNormale(0);
					  
				  }
				  
			  }
			  else {
				  
				  if(t1.getDateday().getDay()==6||t1.getDateday().getDay()==0) {
					  
					  
						
						 if (t1.getDatedebut().getHours() >= 5) {
							 if (t1.getDatefint().getHours() > t1.getDatedebut().getHours()) {
								 if (t1.getDatefint().getHours() < 23) {
		
										 t1.setNbreSup50(ht+n-40);
						
								 } else {

									
									 t1.setNbreSup100(1);

									
										 t1.setNbreSup50(ht+n-40 - 1);
									

								 }

							 } else {
								 if (t1.getDatefint().getHours() <= 5) {
									
									 t1.setNbreSup100(NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
									 if(ht+n-40- t1.getNbreSup100()>0) {
										 t1.setNbreSup50(ht+n-40- t1.getNbreSup100());}
									 else {
										 
										 t1.setNbreSup100(ht+n-40);
									 }
									


								 } else {
									
									 t1.setNbreSup100(NombreHeureJours(79200, 18000));

                                          if(ht+n-40 - t1.getNbreSup100()>0) {
										 t1.setNbreSup15(ht+n-40 - t1.getNbreSup100());
                                          }
                                          else {
                                        	  t1.setNbreSup100(ht+n-40);
                                          }
										

								 }


							 }


						 } else {
							 if (t1.getDatefint().getHours() <= 5) {
								
								 t1.setNbreSup100(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000) + NombreHeureJours(79200, ConverteDate(t1.getDatefint())));


								  if(ht+n-40 - t1.getNbreSup100()>0) {
										 t1.setNbreSup15(ht+n-40 - t1.getNbreSup100());
                                       }
                                       else {
                                     	  t1.setNbreSup100(ht+n-40);
                                       }

								


							 } else {
								 if (t1.getDatefint().getHours() == 23) {
									
									 t1.setNbreSup100(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000) + NombreHeureJours(79200, ConverteDate(t1.getDatefint())));

									  if(ht+n-40 - t1.getNbreSup100()>0) {
											 t1.setNbreSup15(ht+n-40 - t1.getNbreSup100());
	                                          }
	                                          else {
	                                        	  t1.setNbreSup100(ht+n-40);
	                                          }
									
								 } else {
								
									 t1.setNbreSup100(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000));

									  if(ht+n-40 - t1.getNbreSup100()>0) {
											 t1.setNbreSup15(ht+n-40 - t1.getNbreSup100());
	                                          }
	                                          else {
	                                        	  t1.setNbreSup100(ht+n-40);
	                                          }


								 }

							 }

						 }
					  
				  }
				  else {
				  
			
				
						 if (t1.getDatedebut().getHours() >= 5) {
							 if (t1.getDatefint().getHours() > t1.getDatedebut().getHours()) {
								 if (t1.getDatefint().getHours() < 23) {

									

									 if (regarde < 8) {
										 
										 t1.setNbreSup15(ht+n-40);
										 
										
										 if (regarde + t1.getNbreSup15() > 8) {
											 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
											 t1.setNbreSup15(8 - regarde);
											 if (regarde2 + t1.getNbreSup40() > 6) {
												 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
												 t1.setNbreSup40(6 - regarde2);
											 }

										 }

									 } else if (regarde == 8 && regarde2 < 6) {
										 t1.setNbreSup40(ht+n-40);

										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }


									 } else {

										 t1.setNbreSup50(n);
									 }


								 } else {

									
									 t1.setNbreSup50(1);

									 if (regarde < 8) {
										 t1.setNbreSup15(ht+n-40 - 1);
										 if (regarde + t1.getNbreSup15() > 8) {
											 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
											 t1.setNbreSup15(8 - regarde);
											 if (regarde2 + t1.getNbreSup40() > 6) {
												 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
												 t1.setNbreSup40(6 - regarde2);
											 }

										 }

									 } else if (regarde == 8 && regarde2 < 6) {
										 t1.setNbreSup40(ht+n-40 - 1);

										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }


									 } else {

										 t1.setNbreSup50(n);
									 }

								 }

							 } else {
								 if (t1.getDatefint().getHours() <= 5) {
									
									 t1.setNbreSup50(NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
									 
									 if(ht+n-40- t1.getNbreSup50()>0) {
										

									 if (regarde < 8) {
										 t1.setNbreSup15(ht+n-40- t1.getNbreSup50());
										 if (regarde + t1.getNbreSup15() > 8) {
											 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
											 t1.setNbreSup15(8 - regarde);
											 if (regarde2 + t1.getNbreSup40() > 6) {
												 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
												 t1.setNbreSup40(6 - regarde2);
											 }

										 }

									 } else if (regarde == 8 && regarde2 < 6) {
										 t1.setNbreSup40(ht+n-40 - t1.getNbreSup50());

										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }


									 } else {

										 t1.setNbreSup50(n);
									 }
									 }
									 else {
										 t1.setNbreSup50(ht+n-40);
										 
										 
									 }


								 }
								 
								 
								 else {
									 
									
									 t1.setNbreSup50(NombreHeureJours(79200, 18000));
									 if(ht+n-40- t1.getNbreSup50()>0) {
											


									 if (regarde < 8) {
										 t1.setNbreSup15(ht+n-40 - t1.getNbreSup50());
										 if (regarde + t1.getNbreSup15() > 8) {
											 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
											 t1.setNbreSup15(8 - regarde);
											 if (regarde2 + t1.getNbreSup40() > 6) {
												 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
												 t1.setNbreSup40(6 - regarde2);
											 }

										 }

									 } else if (regarde == 8 && regarde2 < 6) {
										 t1.setNbreSup40(ht+n-40 - t1.getNbreSup50());

										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }


									 } else {

										 t1.setNbreSup50(n);
									 }

								 }
									 else {
										 t1.setNbreSup15(ht+n-40);
										 t1.setNbreSup50(0);
										 
									 }


							 }
							 }


						 }/* fin*/ 
						 else {
							 if (t1.getDatefint().getHours() <= 5) {
								
								 t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000) + NombreHeureJours(79200, ConverteDate(t1.getDatefint())));

                                 if(ht+n-40-t1.getNbreSup50()>0) {
								 if (regarde < 8) {
									 t1.setNbreSup15(ht+n-40 - t1.getNbreSup50());


									 if (regarde + t1.getNbreSup15() > 8) {
										 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
										 t1.setNbreSup15(8 - regarde);
										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }

									 }

								 } else if (regarde == 8 && regarde2 < 6) {
									 t1.setNbreSup40(ht+n-40 - t1.getNbreSup50());


									 if (regarde2 + t1.getNbreSup40() > 6) {
										 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
										 t1.setNbreSup40(6 - regarde2);
									 }


								 } else {

									 t1.setNbreSup50(n);
								 }
                                 }
                                 else {
                                	 t1.setNbreSup50(ht+n-40);
                                 }


							 } else {
								 if (t1.getDatefint().getHours() == 23) {
									
									 t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000) + NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
                                      if(ht+n-40- t1.getNbreSup50()>0) {

									 if (regarde < 8) {
										 t1.setNbreSup15(ht+n-40- t1.getNbreSup50());

										 if (regarde + t1.getNbreSup15() > 8) {
											 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
											 t1.setNbreSup15(8 - regarde);
											 if (regarde2 + t1.getNbreSup40() > 6) {
												 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
												 t1.setNbreSup40(6 - regarde2);
											 }

										 }

									 } else if (regarde == 8 && regarde2 < 6) {
										 t1.setNbreSup40(ht+n-40 - t1.getNbreSup50());

										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }


									 } else {

										 t1.setNbreSup50(n);
									 }
									 
                                      }
                                      else {
                                    	 t1.setNbreSup50(ht+n-40); 
                                    	  
                                      }
								 } else {
								
									 t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000));

                                  if(ht+n-40 - t1.getNbreSup50()>0) {
									 if (regarde < 8) {
										 t1.setNbreSup15(ht+n-40 - t1.getNbreSup50());

										 if (regarde + t1.getNbreSup15() > 8) {
											 t1.setNbreSup40(regarde + t1.getNbreSup15() - 8);
											 t1.setNbreSup15(8 - regarde);
											 if (regarde2 + t1.getNbreSup40() > 6) {
												 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
												 t1.setNbreSup40(6 - regarde2);
											 }

										 }

									 } else if (regarde == 8 && regarde2 < 6) {
										 t1.setNbreSup40(ht+n-40 - t1.getNbreSup50());


										 if (regarde2 + t1.getNbreSup40() > 6) {
											 t1.setNbreSup50(regarde2 + t1.getNbreSup40() - 6 + t1.getNbreSup50());
											 t1.setNbreSup40(6 - regarde2);
										 }


									 } else {

										 t1.setNbreSup50(n );
									 }
                                  }
                                  else {
                                	  t1.setNbreSup15(ht+n-40);
                                	  t1.setNbreSup50(0);
                                	  
                                  }


								 }

							 }

						 }
				  
				  
				  
				  
			  }
			  
			  }
			  
		  }
		  
		  
		  
		
		return t1;
	}

	@Override
	public double CalculeRendementMois(double note1, double note2, double note3, double note4) {
		// TODO Auto-generated method stub
		double r=(note1*1.5 +(note2*1.15)+(note3*1.1)+(note4*1.25))/100;
		
		return  (double) Math.round(r * 10000) / 10000;
	}
  
 
  }
 