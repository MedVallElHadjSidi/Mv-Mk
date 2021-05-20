package com.example.demo.excel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.demo.DAO.MoisRepository;
import com.example.demo.DAO.TachesIm;
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

import com.example.demo.entity.Mois;
import com.example.demo.entity.Taches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional 
public class ExcelFileExporter {
	@Autowired TachesIm tachesim;
	@Autowired MoisRepository moisRepository;
	
	private String matricule;
	Date date=new Date();

	

	public  ByteArrayInputStream contactListToExcelFile(List<Taches> taches) {
		Row dataRow = null ;
		   Taches tf=taches.get(taches.size()-1);

	        System.out.println(tf.getDateday());
	        System.out.println(taches.get(0).getDateday());
	        System.out.println(tf.getEmployer().getId());
	       System.out.println(tachesim.SommeSup15(taches.get(0).getDateday(), tf.getDateday(), tf.getEmployer().getId()));

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
	        cell.setCellValue("Date");
	        cell.setCellStyle(headerCellStyle);
	        CreationHelper createHelper = workbook.getCreationHelper();
	        CellStyle2.setDataFormat(
	          createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
	        cell = row.createCell(1);
	        cell.setCellValue("DB");
	        cell.setCellStyle(headerCellStyle);
	        XSSFDataFormat xssfDataFormat = (XSSFDataFormat) workbook.createDataFormat();
	       
	        
	        CellStyle.setDataFormat(xssfDataFormat.getFormat("hh:mm"));
	      
	
	        cell = row.createCell(2);
	        cell.setCellValue("DF");
	        cell.setCellStyle(headerCellStyle);
	
	        cell = row.createCell(3);
	        cell.setCellValue("HeureNormale");
	        cell.setCellStyle(headerCellStyle);

			cell = row.createCell(4);
			cell.setCellValue("HS15%");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(5);
			cell.setCellValue("HS40%");
			cell.setCellStyle(headerCellStyle);


			cell = row.createCell(6);
			cell.setCellValue("HS50%");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(7);
			cell.setCellValue("HS100%");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(8);
			cell.setCellValue("PANIER");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(9);
			cell.setCellValue("H-Total");
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(10);
			cell.setCellValue("Rendement");
			cell.setCellStyle(headerCellStyle);
			 
			cell = row.createCell(11);
			cell.setCellValue("Matricule");
			cell.setCellStyle(headerCellStyle);
		
	        // Creating data rows for each customer
	        for(int i =0; i < taches.size(); i++) {
	        matricule=taches.get(i).getEmployer().getId();
	        dataRow= sheet.createRow(i+1);
	        	// Row dataRow = sheet.createRow(i+1);
	        	dataRow.createCell(0).setCellValue(taches.get(i).getDateday());
	        	dataRow.getCell(0).setCellStyle(CellStyle2);
	        	dataRow.createCell(1).setCellValue(taches.get(i).getDatedebut());
	        	dataRow.getCell(1).setCellStyle(CellStyle);
	        	
	        	
	        	
	        	dataRow.createCell(2).setCellValue(taches.get(i).getDatefint());
	        	dataRow.getCell(2).setCellStyle(CellStyle);
	        	dataRow.createCell(3).setCellValue(taches.get(i).getNbreHtNormale());
				dataRow.createCell(4).setCellValue(taches.get(i).getNbreSup15());
				dataRow.createCell(5).setCellValue(taches.get(i).getNbreSup40());
				dataRow.createCell(6).setCellValue(taches.get(i).getNbreSup50());
				dataRow.createCell(7).setCellValue(taches.get(i).getNbreSup100());
				dataRow.createCell(8).setCellValue(taches.get(i).getPanier());
				dataRow.createCell(9).setCellValue(taches.get(i).getTotal_Heure());
				dataRow.createCell(11).setCellValue(taches.get(i).getEmployer().getId());
				date=taches.get(i).getDateday();



	        }
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(date);

			  
			int annes=calendar.get(calendar.YEAR);
			 int mois=calendar.get(calendar.MONTH)+1;
	        Mois moi=moisRepository.findByMoisAndAnneAndEmployerId(mois, annes, matricule);
	        if(moi!=null) {
	    	dataRow.createCell(10).setCellValue(moi.getRendement());}
	        else {
	        	dataRow.createCell(10).setCellValue(0);
	        }
	        
	   














			Row dataRow1 = sheet.createRow(taches.size()+2);
			dataRow1.createCell(2).setCellValue("sommes");
		
				dataRow1.createCell(3).setCellValue(tachesim.SommeHN(taches.get(0).getDateday(), tf.getDateday(), tf.getEmployer().getId()));
				dataRow1.createCell(4).setCellValue(tachesim.SommeSup15(taches.get(0).getDateday(), tf.getDateday(), tf.getEmployer().getId()));
				dataRow1.createCell(5).setCellValue(tachesim.SommeSup40(taches.get(0).getDateday(), tf.getDateday(), tf.getEmployer().getId()));
				dataRow1.createCell(6).setCellValue(tachesim.SommeSup50(taches.get(0).getDateday(), tf.getDateday(), tf.getEmployer().getId()));
				dataRow1.createCell(7).setCellValue(tachesim.SommeSup100(taches.get(0).getDateday(), tf.getDateday(), tf.getEmployer().getId()));
				dataRow1.createCell(8).setCellValue(tachesim.SommePanier(taches.get(0).getDateday(), tf.getDateday(), tf.getEmployer().getId()));
				dataRow1.createCell(9).setCellValue(tachesim.SommeHt(taches.get(0).getDateday(), tf.getDateday(), tf.getEmployer().getId()));


	        // Making size of column auto resize to fit with data
	        sheet.autoSizeColumn(0);
	        sheet.autoSizeColumn(1);
	        sheet.autoSizeColumn(2);
	        sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);
			sheet.autoSizeColumn(6);
			sheet.autoSizeColumn(7);
			sheet.autoSizeColumn(8);
			sheet.autoSizeColumn(9);




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
