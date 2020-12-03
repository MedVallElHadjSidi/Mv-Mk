/*
 * package com.example.demo.Metier;
 * 
 * import java.util.Date;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Service; import
 * org.springframework.transaction.annotation.Transactional; import
 * com.example.demo.DAO.TachesIm; import com.example.demo.entity.Taches;
 * 
 * 
 * 
 * 
 * @Service
 * 
 * @Transactional public class MetierIm implements IMetier{
 * 
 * @Autowired TachesIm tachesim;
 * 
 * 
 * @Override public Taches TacheCompletJour(Taches t) { // TODO Auto-generated
 * method stub
 * 
 * int
 * n=NombreHeureJours(ConverteDate(t.getDatedebut()),ConverteDate(t.getDatefint(
 * ))); t.setTotal_Heure(n);
 * 
 * if(t.getDatefint().getHours()<=23&&t.getDatedebut().getHours()<t.getDatefint(
 * ).getHours()) {
 * 
 * if(t.getDatefint().getHours()<=17) { t.setNbreHtNormale(n);
 * 
 * }
 * 
 * 
 * else if(t.getDatefint().getHours()<=22) {
 * 
 * 
 * System.out.println("tester  avant 22");
 * t.setNbreHtNormale(NombreHeureJours(ConverteDate(t.getDatedebut()),61200));
 * t.setNbreSup15(NombreHeureJours(61200,ConverteDate(t.getDatefint())));
 * 
 * } else { System.out.println("tester  jusqu'a 23");
 * 
 * t.setNbreHtNormale(NombreHeureJours(ConverteDate(t.getDatedebut()),61200));
 * t.setNbreSup15(NombreHeureJours(61200,79200));
 * t.setNbreSup50(NombreHeureJours(79200,ConverteDate(t.getDatefint())));
 * 
 * 
 * }
 * 
 * 
 * } else {
 * 
 * 
 * if(t.getDatefint().getHours()<=5) { System.out.println("tester  avant 05");
 * t.setNbreHtNormale(NombreHeureJours(ConverteDate(t.getDatedebut()),61200));
 * t.setNbreSup15(NombreHeureJours(61200,79200));
 * t.setNbreSup50(NombreHeureJours(79200,ConverteDate(t.getDatefint())));
 * 
 * }
 * 
 * else { System.out.println("tester  res 05");
 * 
 * t.setNbreHtNormale(NombreHeureJours(ConverteDate(t.getDatedebut()),61200));
 * t.setNbreSup15(NombreHeureJours(61200,79200));
 * t.setNbreSup50(NombreHeureJours(79200,18000));
 * t.setNbreSup15(t.getNbreSup15()+(NombreHeureJours(18000,ConverteDate(t.
 * getDatefint())))); if(t.getNbreSup15()>8) {
 * t.setNbreSup40(t.getNbreSup15()-8); t.setNbreSup15(8);
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
 * 
 * 
 * 
 * if(n>9) { t.setTotal_Heure(n); t.setNbreHtNormale(9); if(t.getNbreSup15()<8)
 * { t.setNbreSup15(n-9); if(t.getNbreSup15()>8) { t.setNbreSup15(8);
 * t.setNbreSup40(n-9-8); if(t.getNbreSup40()>6) { t.setNbreSup40(6);
 * t.setNbreSup50(n-9-8-6); }
 * 
 * }
 * 
 * }
 * 
 * } if(n==9) { t.setNbreHtNormale(9); t.setTotal_Heure(n);
 * 
 * }
 * 
 * 
 * 
 * return t; }
 * 
 * @Override public int NombreHeureJours(int t1,int t2) { int rs=0;
 * 
 * if(t2>t1) {
 * 
 * rs=(t2-t1)/3600;}
 * 
 * 
 * else { rs=(24)-((t1-t2)/3600); }
 * 
 * 
 * return rs;
 * 
 * 
 * }
 * 
 * 
 * @Override public Taches MemeJours(Taches t1, Taches t2) { // TODO
 * Auto-generated method stub
 * 
 * int n=0; //int regarde=tachesim.SommeSup15(t2.getDateday(), t1.getDateday(),
 * t1.getEmployer().getId()); Date
 * debutwekend=tachesim.DebutSemaine(t1.getDateday());
 * 
 * int regarde=tachesim.SommeSup15(debutwekend, t1.getDateday(),
 * t1.getEmployer().getId()); int regarde2=tachesim.SommeSup40(debutwekend,
 * t1.getDateday(), t1.getEmployer().getId());
 * n=NombreHeureJours(ConverteDate(t1.getDatedebut()),ConverteDate(t1.
 * getDatefint())); t1.setTotal_Heure(n);
 * 
 * if(t2.getDatefint().getHours()>0) {
 * if(t2.getDatefint().getHours()>=17&&t2.getDatefint().getHours()<=23) {
 * System.out.println("ona rentrere  t2.fin>1=7&&t2.din<=23");
 * if(t2.getDatefint().getHours()==23) {
 * System.out.println("ona rentrere  t2.fin==23");
 * if(t1.getDatedebut().getHours()>=23&&t1.getDatefint().getHours()<=8||(t1.
 * getDatedebut().getHours()<=8&&t1.getDatefint().getHours()<=8)&&t1.getDatefint
 * ().getHours()>0) { System.out.println("ona rentrere t1.debut==23");
 * if(t1.getDatefint().getHours()<=8) { if(t1.getDatefint().getHours()<=5) {
 * System.out.println("ona rentrere t1.debut==23&&t1.fin<=05");
 * t1.setNbreSup50(n); } else {
 * System.out.println("ona rentrere t1.debut==23&&t1.fin>=05"); if(regarde<8) {
 * t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000));
 * t1.setNbreSup15(NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
 * if(regarde+t1.getNbreSup15()>8) {
 * t1.setNbreSup40(t1.getNbreSup15()+regarde-8); t1.setNbreSup15(8-regarde);
 * if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } } } else if(regarde==8&&regarde2<6) {
 * t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000));
 * t1.setNbreSup40(NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
 * if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } } else { t1.setNbreSup50(n); }
 * 
 * }}
 * 
 * } else { System.out.println("ona rentrere t1.debut	08<23"); return null; }}
 * else if(t2.getDatefint().getHours()==22) {
 * if(t1.getDatedebut().getHours()>=22&t1.getDatefint().getHours()<=8||(t1.
 * getDatedebut().getHours()>=0&&t1.getDatefint().getHours()<=8&&t1.getDatedebut
 * ().getHours()<8)) {
 * System.out.println("ona rentrere t1.debut<23&&t1.debur<=22&&t1.fin<=8");
 * if(t1.getDatefint().getHours()<=5) { t1.setNbreSup50(n); } else {
 * if(regarde<8) {
 * t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000));
 * t1.setNbreSup15(NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
 * if(regarde+t1.getNbreSup15()>8) {
 * t1.setNbreSup40(t1.getNbreSup15()+regarde-8); t1.setNbreSup15(8-regarde);
 * if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); }}} else if(regarde==8&&regarde2<6) {
 * t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000));
 * t1.setNbreSup40(NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
 * if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } } else { t1.setNbreSup50(n); }}} else {
 * System.out.println("ona rentrere t2.fin=22&&t1.debut[0-22]"); t1=vider(t1);
 * return t1; } } else {
 * if(t1.getDatedebut().getHours()>=t2.getDatefint().getHours()&&t1.getDatefint(
 * ).getHours()<=23&&t1.getDatefint().getHours()>t2.getDatefint().getHours()) {
 * System.out.println("ona rentrere t1.debut>t2.fin	t1.fin<23");
 * if(t1.getDatefint().getHours()==23) { if(regarde<8) {
 * t1.setNbreSup15(NombreHeureJours(ConverteDate(t1.getDatedebut()), 79200));
 * t1.setNbreSup50(NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
 * if(regarde+t1.getNbreSup15()>8) {
 * t1.setNbreSup40(t1.getNbreSup15()+regarde-8); t1.setNbreSup15(8-regarde);
 * if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } } } else if(regarde==8&&regarde2<6) {
 * t1.setNbreSup40(NombreHeureJours(ConverteDate(t1.getDatedebut()), 79200));
 * t1.setNbreSup50(NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
 * if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } } else { t1.setNbreSup50(n); }
 * 
 * } else { if(regarde<8) {
 * 
 * t1.setNbreSup15(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * ConverteDate(t1.getDatefint()))); if(regarde+t1.getNbreSup15()>8) {
 * t1.setNbreSup40(t1.getNbreSup15()+regarde-8); t1.setNbreSup15(8-regarde);
 * if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6); t1.setNbreSup40(6-regarde2); }
 * } } else if(regarde==8&&regarde2<6) {
 * t1.setNbreSup40(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * ConverteDate(t1.getDatefint()))); if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6); t1.setNbreSup40(6-regarde2); }
 * } else { t1.setNbreSup50(n); } }
 * 
 * } else
 * if(t1.getDatedebut().getHours()>=t2.getDatefint().getHours()&&t1.getDatefint(
 * ).getHours()<=8) {
 * System.out.println("ona rentrere t1.debut>t2.fin	t1.fin<=08");
 * if(t1.getDatedebut().getHours()==22) { if(t1.getDatefint().getHours()<=5) {
 * t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
 * t1.setNbreSup50(n); } else { if(regarde<8) {
 * t1.setNbreSup50(NombreHeureJours(79200,18000));
 * t1.setNbreSup15(NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
 * if(regarde+t1.getNbreSup15()>8) {
 * t1.setNbreSup40(t1.getNbreSup15()+regarde-8); t1.setNbreSup15(8-regarde);
 * if(t1.getNbreSup40()+regarde2>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); }
 * 
 * }
 * 
 * }else if(regarde==8&&regarde2<6) {
 * t1.setNbreSup50(NombreHeureJours(79200,18000));
 * t1.setNbreSup40(NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
 * if(t1.getNbreSup40()+regarde2>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } } else { t1.setNbreSup50(n); } } } else{
 * if(t1.getDatefint().getHours()<=5) {
 * System.out.println("ona rentrere t1.debut>t2.fin	t1.fin<=05");
 * 
 * if(regarde<8) {
 * t1.setNbreSup15(NombreHeureJours(ConverteDate(t1.getDatedebut()), 79200));
 * t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
 * if(regarde+t1.getNbreSup15()>8) {
 * t1.setNbreSup40(t1.getNbreSup15()+regarde-8); t1.setNbreSup15(8-regarde);
 * if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } }
 * 
 * 
 * } else if(regarde==8&&regarde2<6) {
 * t1.setNbreSup40(NombreHeureJours(ConverteDate(t1.getDatedebut()), 79200));
 * t1.setNbreSup50(NombreHeureJours(79200,ConverteDate(t1.getDatefint())));
 * if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } } else { t1.setNbreSup50(n); } } else {
 * if(regarde<8) {
 * t1.setNbreSup15(NombreHeureJours(ConverteDate(t1.getDatedebut()), 79200));
 * t1.setNbreSup50(NombreHeureJours(79200,18000));
 * t1.setNbreSup15(NombreHeureJours(18000,ConverteDate(t1.getDatefint()))+t1.
 * getNbreSup15()); if(regarde+t1.getNbreSup15()>8) {
 * t1.setNbreSup40(t1.getNbreSup15()+regarde-8); t1.setNbreSup15(8-regarde);
 * if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } }
 * 
 * } else if(regarde==8&&regarde2<6) {
 * t1.setNbreSup40(NombreHeureJours(ConverteDate(t1.getDatedebut()), 79200));
 * t1.setNbreSup50(NombreHeureJours(79200,18000));
 * t1.setNbreSup40(NombreHeureJours(18000,ConverteDate(t1.getDatefint()))+t1.
 * getNbreSup40()); if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } } else { t1.setNbreSup50(n); } } } } else
 * if(t1.getDatedebut().getHours()>=0&&t1.getDatefint().getHours()<=8&&t1.
 * getDatedebut().getHours()<8) {
 * 
 * System.out.println("ona rentrere t1.debut<t2.fin	t1.fin<=08[00-08]");
 * if(t1.getDatefint().getHours()<=5) { t1.setNbreSup50(n); } else {
 * if(regarde<8) {
 * t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()),18000));
 * t1.setNbreSup15(NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
 * if(regarde+t1.getNbreSup15()>8) {
 * t1.setNbreSup40(t1.getNbreSup15()+regarde-8); t1.setNbreSup15(8-regarde);
 * if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup40(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } } } else if(regarde==8&&regarde2<6) {
 * t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()),18000));
 * t1.setNbreSup40(NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
 * if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup40(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } } else{ t1.setNbreSup50(n); } } } else {
 * System.out.println("mauvaise"); t1=vider(t1); return t1; } }
 * 
 * 
 * } else { System.out.println("t2.fin<=01<=08");
 * if(t1.getDatedebut().getHours()<=8&&t1.getDatefint().getHours()<=8&&t1.
 * getDatedebut().getHours()>0&t1.getDatedebut().getHours()>=t2.getDatefint().
 * getHours()&&t1.getDatefint().getHours()>0) {
 * System.out.println("t2.fin<=08");
 * 
 * if(t1.getDatefint().getHours()<=8) { if(t1.getDatefint().getHours()<=5) {
 * System.out.println("t2.fin<01<=05"); t1.setNbreSup50(n); }else {
 * System.out.println("t2.fin<01>=05"); if(t1.getDatedebut().getHours()>=5) {
 * 
 * if(regarde<8) {
 * t1.setNbreSup15(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * ConverteDate(t1.getDatefint()))); if(regarde+t1.getNbreSup15()>8) {
 * t1.setNbreSup40(t1.getNbreSup15()+regarde-8); t1.setNbreSup15(8-regarde);
 * if(t1.getNbreSup40()+regarde2>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6); t1.setNbreSup40(6-regarde2); }
 * } } else if(regarde==8&&regarde2<6) {
 * t1.setNbreSup40(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * ConverteDate(t1.getDatefint()))); if(t1.getNbreSup40()+regarde2>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6); t1.setNbreSup40(6-regarde2); }
 * } else { t1.setNbreSup50(n); } } else { System.out.println("heuredebut<=05");
 * if(regarde<8) {
 * 
 * t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000));
 * t1.setNbreSup15(NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
 * if(regarde+t1.getNbreSup15()>8) {
 * t1.setNbreSup40(t1.getNbreSup15()+regarde-8); t1.setNbreSup15(8-regarde);
 * if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } } } else if(regarde==8&&regarde2<6) {
 * t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000));
 * t1.setNbreSup40(NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
 * if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup50(t1.getNbreSup40()-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } } else { t1.setNbreSup50(n); } }}}
 * 
 * else { System.out.println("t2.fdebut>0&&<0t.fin<8"); t1=vider(t1); return t1;
 * } } else { System.out.println("t2.fdebut|t.debut>08||t.fin>08");
 * t1=vider(t1); return t1; }
 * 
 * }
 * 
 * 
 * } else { System.out.println("t2.fin==0");
 * if(t1.getDatedebut().getHours()>=0&&t1.getDatefint().getHours()<=8&&t1.
 * getDatedebut().getHours()<=8) {
 * System.out.println("t2.fin==0&&t1.debut&&t1.finvalider");
 * 
 * if(t1.getDatefint().getHours()<=8) { if(t1.getDatefint().getHours()<=5) {
 * System.out.println("ona rentrere t1.debut==23&&t1.fin<=05");
 * t1.setNbreSup50(n); } else {
 * System.out.println("ona rentrere t1.debut==23&&t1.fin>=05"); if(regarde<8) {
 * t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000));
 * t1.setNbreSup15(NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
 * if(regarde+t1.getNbreSup15()>8) {
 * t1.setNbreSup40(t1.getNbreSup15()+regarde-8); t1.setNbreSup15(8-regarde);
 * if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } } } else if(regarde==8&&regarde2<6) {
 * t1.setNbreSup50(NombreHeureJours(ConverteDate(t1.getDatedebut()), 18000));
 * t1.setNbreSup40(NombreHeureJours(18000,ConverteDate(t1.getDatefint())));
 * if(regarde2+t1.getNbreSup40()>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } } else { t1.setNbreSup50(n); }
 * 
 * }} } else { System.out.println("t2.fin==0&&t1.debut&&t1.fin	no	valide");
 * t1=vider(t1); return t1; }
 * 
 * } return t1; }
 * 
 * 
 * @Override public int ConverteDate(Date t) { // TODO Auto-generated method
 * stub int nb=(t.getHours()*3600)+(t.getMinutes()*60);
 * 
 * return nb; }
 * 
 * @Override public Taches TachesCompletJoursSup(Taches t1, Taches t2) { // TODO
 * Auto-generated method stub Date
 * debutwekend=tachesim.DebutSemaine(t1.getDateday());
 * 
 * int regarde=tachesim.SommeSup15(debutwekend, t1.getDateday(),
 * t1.getEmployer().getId()); int regarde2=tachesim.SommeSup40(debutwekend,
 * t1.getDateday(), t1.getEmployer().getId()); int
 * n=NombreHeureJours(ConverteDate(t1.getDatedebut()),ConverteDate(t1.
 * getDatefint())); t1.setTotal_Heure(n); if(t1.getDatefint().getHours()>0) {
 * 
 * if(t1.getDatefint().getHours()<=8) { System.out.println("date fin <8");
 * 
 * if(t1.getDatefint().getHours()<=5) { System.out.println("date fin <=05");
 * if(regarde<8) {
 * t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * 61200)); t1.setNbreSup15(NombreHeureJours(61200, 79200));
 * t1.setNbreSup50(NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
 * if(t1.getNbreSup15()+regarde>8) {
 * t1.setNbreSup40(t1.getNbreSup15()+regarde-8); t1.setNbreSup15(8-regarde);
 * if(t1.getNbreSup40()+regarde2>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } } } else if(regarde==8&&regarde2<6) {
 * 
 * t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * 61200)); t1.setNbreSup40(NombreHeureJours(61200, 79200));
 * t1.setNbreSup50(NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
 * if(t1.getNbreSup40()+regarde2>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); }
 * 
 * } else {
 * t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * 61200)); t1.setNbreSup50(t1.getTotal_Heure()-t1.getNbreHtNormale());
 * 
 * 
 * } }
 * 
 * else { System.out.println("date fin>=5"); if(regarde<8) {
 * t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * 61200)); t1.setNbreSup15(NombreHeureJours(61200, 79200));
 * t1.setNbreSup50(NombreHeureJours(79200, 18000));
 * t1.setNbreSup15(NombreHeureJours(18000,ConverteDate(t1.getDatefint()))+t1.
 * getNbreSup15()); if(t1.getNbreSup15()+regarde>8) {
 * t1.setNbreSup40(t1.getNbreSup40()+regarde-8); t1.setNbreSup15(8-regarde);
 * if(t1.getNbreSup40()+regarde2>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2);
 * 
 * } } } else if(regarde==8&&regarde2<6) {
 * t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * 61200)); t1.setNbreSup40(NombreHeureJours(61200, 79200));
 * t1.setNbreSup50(NombreHeureJours(79200, 18000));
 * t1.setNbreSup40(NombreHeureJours(18000,ConverteDate(t1.getDatefint()))+t1.
 * getNbreSup40()); if(t1.getNbreSup40()+regarde2>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2);
 * 
 * } } else {
 * 
 * t1.setNbreSup50(t1.getTotal_Heure()-t1.getNbreHtNormale()); }
 * 
 * }
 * 
 * } else if(t1.getDatefint().getHours()>17&&t1.getDatefint().getHours()<=23) {
 * System.out.println("date fin <23"); if(t1.getDatefint().getHours()==23) {
 * System.out.println("date fin =23"); if(regarde<8) {
 * t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * 61200)); t1.setNbreSup15(NombreHeureJours(61200, 79200));
 * t1.setNbreSup50(NombreHeureJours(79200, 82800));
 * if(regarde+t1.getNbreSup15()>8) {
 * t1.setNbreSup40(t1.getNbreSup15()+regarde-8); t1.setNbreSup15(8-regarde);
 * if(t1.getNbreSup40()+regarde2>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2);
 * 
 * } }
 * 
 * } else if(regarde==8&&regarde2<6) {
 * t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * 61200)); t1.setNbreSup40(NombreHeureJours(61200, 79200));
 * t1.setNbreSup50(NombreHeureJours(79200, 82800));
 * if(t1.getNbreSup40()+regarde2>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2);
 * 
 * } } else { t1.setNbreSup50(t1.getTotal_Heure()-t1.getNbreHtNormale()); } }
 * else { System.out.println("date fin <23"); if(regarde<8) {
 * t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * 61200));
 * t1.setNbreSup15(NombreHeureJours(61200,ConverteDate(t1.getDatefint())));
 * if(regarde+t1.getNbreSup15()>8) {
 * t1.setNbreSup40(t1.getNbreSup15()+regarde-8); t1.setNbreSup15(8-regarde);
 * if(t1.getNbreSup40()+regarde2>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6); t1.setNbreSup40(6-regarde2); }
 * }
 * 
 * } else if(regarde==8&&regarde2<6) {
 * t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * 61200));
 * t1.setNbreSup40(NombreHeureJours(61200,ConverteDate(t1.getDatefint())));
 * if(t1.getNbreSup40()+regarde2>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6); t1.setNbreSup40(6-regarde2); }
 * } else {
 * t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * 61200)); t1.setNbreSup50(t1.getTotal_Heure()-t1.getNbreHtNormale()); }
 * 
 * } } else { if(t1.getDatefint().getHours()==17) {
 * System.out.println("date fin =17");
 * t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * 61200)); } else { System.out.println("date fin <17"); t1=null; }
 * 
 * }
 * 
 * 
 * 
 * } else { System.out.println("date fin =0"); if(regarde<8) {
 * System.out.println("date fin &&regarde<8");
 * t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * 61200)); t1.setNbreSup15(NombreHeureJours(61200, 79200));
 * t1.setNbreSup50(NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
 * if(regarde+t1.getNbreSup15()>8) {
 * t1.setNbreSup40(regarde+t1.getNbreSup15()-8); t1.setNbreSup15(8-regarde);
 * if(t1.getNbreSup40()+regarde2>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } }
 * 
 * } else if(regarde==8&&regarde2<6) {
 * t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * 61200)); t1.setNbreSup40(NombreHeureJours(61200, 79200));
 * t1.setNbreSup50(NombreHeureJours(79200, ConverteDate(t1.getDatefint())));
 * if(t1.getNbreSup40()+regarde2>6) {
 * t1.setNbreSup50(t1.getNbreSup40()+regarde2-6+t1.getNbreSup50());
 * t1.setNbreSup40(6-regarde2); } } else {
 * t1.setNbreHtNormale(NombreHeureJours(ConverteDate(t1.getDatedebut()),
 * 61200)); t1.setNbreSup50(t1.getTotal_Heure()-t1.getNbreHtNormale()); } }
 * return t1; }
 * 
 * @Override public Taches TacheWeekend(Taches t) { // TODO Auto-generated
 * method stub return null; }
 * 
 * @Override public Taches vider(Taches t) { // TODO Auto-generated method stub
 * return null; }
 * 
 * }
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */