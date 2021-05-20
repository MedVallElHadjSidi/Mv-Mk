package com.example.demo.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DAO.MoisRepository;
import com.example.demo.DAO.TachesIm;
import com.example.demo.entity.Mois;
import com.example.demo.entity.Taches;
import com.example.demo.model.ModelRapportTotal;

@Service
@Transactional 
public class ExcelFileRapportTotal {
	@Autowired TachesIm tachesim;
	@Autowired MoisRepository moisRepository;
	
	private String matricule;
	Date date=new Date();

	

	public  ByteArrayInputStream contactListToExcelFile(List<ModelRapportTotal> taches) {
		Row dataRow = null ;
	/*	Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);

		  
		int annes=calendar.get(calendar.YEAR);
		 int mois=calendar.get(calendar.MONTH)+1;*/
		
		   ModelRapportTotal modelTotal=taches.get(taches.size()-1);

	   //     System.out.println(tf.getDateday());
	    //    System.out.println(taches.get(0).getDateday());
	    //    System.out.println(tf.getEmployer().getId());
	    //    System.out.println(tachesim.SommeSup15(taches.get(0).getDateday(), tf.getDateday(), tf.getEmployer().getId()));

		try(Workbook workbook = new XSSFWorkbook()){

			Sheet sheet = workbook.createSheet("Customers");
			
			Row row = sheet.createRow(0);
	        CellStyle headerCellStyle = workbook.createCellStyle();
	        CellStyle CellStyle = workbook.createCellStyle();
	        CellStyle CellStyle2 = workbook.createCellStyle();
	        headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
	        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        // Creating header
	        Cell cell = row.createCell(0);
	        cell.setCellValue("Matricule");
	        cell.setCellStyle(headerCellStyle);
	        cell=row.createCell(1);
	    	cell.setCellValue("S-HN");
			cell.setCellStyle(headerCellStyle);
			cell=row.createCell(2);
			cell.setCellValue("S-HT");
			cell.setCellStyle(headerCellStyle);
			

			cell = row.createCell(3);
			cell.setCellValue("S-HS15%");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(4);
			cell.setCellValue("S-HS40%");
			cell.setCellStyle(headerCellStyle);


			cell = row.createCell(5);
			cell.setCellValue("S-HS50%");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(6);
			cell.setCellValue("S-HS100%");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(7);
			cell.setCellValue("S-PANIER");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(8);
			cell.setCellValue("Rendement");
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(9);
			cell.setCellValue("Nom");
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(10);
			cell.setCellValue("Fonction");
			cell.setCellStyle(headerCellStyle);
			 
		
	        // Creating data rows for each customer
	        for(int i =0; i < taches.size(); i++) {
	     //   matricule=taches.get(i).getEmployer().getId();
	        dataRow= sheet.createRow(i+1);
	        	// Row dataRow = sheet.createRow(i+1);
	        	dataRow.createCell(0).setCellValue(taches.get(i).getMatricule());
	        	dataRow.getCell(0).setCellStyle(CellStyle2);
	        	dataRow.createCell(1).setCellValue(taches.get(i).getSommeHn());
	        	dataRow.getCell(1).setCellStyle(CellStyle);
	        	
	        	
	        	
	        	dataRow.createCell(2).setCellValue(taches.get(i).getSommeHt());
	        	dataRow.getCell(2).setCellStyle(CellStyle);
	        	dataRow.createCell(3).setCellValue(taches.get(i).getSommeHs15());
				dataRow.createCell(4).setCellValue(taches.get(i).getSommeHs40());
				dataRow.createCell(5).setCellValue(taches.get(i).getSomme50());
				dataRow.createCell(6).setCellValue(taches.get(i).getSomme100());
				dataRow.createCell(7).setCellValue(taches.get(i).getSommepaniers());
				dataRow.createCell(8).setCellValue(taches.get(i).getRendement());
				dataRow.createCell(9).setCellValue(taches.get(i).getNom());
				dataRow.createCell(10).setCellValue(taches.get(i).getFonction());
				
				//dataRow.createCell(9).setCellValue(taches.get(i).getTotal_Heure());
				//date=taches.get(i).getDateday();



	        }
		/*	Calendar calendar=Calendar.getInstance();
			calendar.setTime(date);

			  
			int annes=calendar.get(calendar.YEAR);
			 int mois=calendar.get(calendar.MONTH)+1;
	        Mois moi=moisRepository.findByMoisAndAnneAndEmployerId(mois, annes, matricule);
	        if(moi!=null) {
	    	dataRow.createCell(10).setCellValue(moi.getRendement());}
	        else {
	        	dataRow.createCell(10).setCellValue(0);
	        }*/
	        
	   

















	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        workbook.write(outputStream);
	        return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static Taches donnerListe(List<Taches> taches){
		Taches t=new Taches();
		for (Taches ti:taches){
			t=ti;
		}
		return  t;
	}

	public  static  int SizeTaches(List<Taches>taches){
		return  taches.size();
	}


}
