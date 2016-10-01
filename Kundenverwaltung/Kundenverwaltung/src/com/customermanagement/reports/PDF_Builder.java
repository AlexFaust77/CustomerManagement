package com.customermanagement.reports;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.customermanagement.entities.Obj_Customer;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDF_Builder {

	    private static final String str_File ="C:/Users/Alex/Desktop/Ratenplan.pdf";
	    private static final Font catFont = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD);
	    
	    private ArrayList<String> lst_Months = new ArrayList<String>();
	    private ArrayList<String> lst_Monthly_Rate = new ArrayList<String>();
	    private Obj_Customer  obj_Customer = new Obj_Customer();
	    
	    public void create_PDF_Export(Logger logger) {						 // create new PDF File
	        try {
	            Document pdf_Document = new Document();
	            PdfWriter.getInstance(pdf_Document, new FileOutputStream(str_File));
	            pdf_Document.open();			
	           
	            getMetaData(pdf_Document);									// Method for adding Meta Data to Document / PDF 
	            getContent(pdf_Document,logger);
	            pdf_Document.close();									    // Close PDF Finished
	          	            
	        } catch (DocumentException ex) {
	        	logger.error("Create Document " + ex.getLocalizedMessage());
	        } catch (FileNotFoundException ex) {
	        	logger.error("Create Document - File Not Found " + ex.getLocalizedMessage());
	        }
	    }
	    
	    private void getContent(Document pdf_Document, Logger logger) {
	      try {
	        Anchor anker = new Anchor("Ratenplan fuer :\n" + 
	                                  "Kundennummer : " + obj_Customer.getCustNo().toString() + "\n" 
	                                  + obj_Customer.getLastname() + ", " + obj_Customer.getFirstname() + "\n\n\n",catFont);
	               anker.setName("Ratenplan");
	               
	        Chapter chapter = new Chapter(new Paragraph(anker),1); 				// Anchor and Number of Chapters
	             
	        create_Content_Table(chapter);										// create Table for PDF
	        pdf_Document.add(chapter);               							// Document add chapter - with created content
	       
	        } catch (DocumentException ex) {
	        	logger.error("Document - get Content " + ex.getLocalizedMessage());
	        }
	    }
	    
	    private static void getMetaData(Document pdf_Document) {		  // Meta Data for add to the Document
	    	pdf_Document.addTitle("Ratenplan");
	    	pdf_Document.addSubject("Ratenplan erstellt mit IText");
	    	pdf_Document.addKeywords("Java,PDF,IText");
	    	pdf_Document.addAuthor("Kundenverwaltung+");
	    	pdf_Document.addCreator("Kundenverwaltung+");
	    }
	 
	    private void create_Content_Table(Chapter chapter) {
	        PdfPTable pdf_Table = new PdfPTable(2);
	        
	        PdfPCell cell_Table = new PdfPCell(new Phrase("Monat"));	// Create Cell for Table 
	                 cell_Table.setHorizontalAlignment(Element.ALIGN_CENTER);
	        pdf_Table.addCell(cell_Table);
	        
	        cell_Table = new PdfPCell(new Phrase("Rate"));
	        cell_Table.setHorizontalAlignment(Element.ALIGN_CENTER);
	        pdf_Table.addCell(cell_Table);
	                
	        pdf_Table.setHeaderRows(1);
	        
	        for(int monthly_Rate = 0; monthly_Rate < lst_Months.size(); monthly_Rate++){				// adding values to Table Cells
	        	pdf_Table.addCell(lst_Months.get(monthly_Rate).toString());
	        	pdf_Table.addCell(lst_Monthly_Rate.get(monthly_Rate).toString() + " €");    
	        }
	            chapter.add(pdf_Table);
	    }
	    
	    private static void getEmptyLine(Paragraph paragraph,int needed_Empty_Lines) {					// Not uses now - only for Extension
	        for(int line_Counter = 0; line_Counter < needed_Empty_Lines;line_Counter++) {
	            paragraph.add(new Paragraph(" ")); 														// add a empty Paragraph - to have a empty line in PDF
	        }
	    }
	    
	    public void setCustomer(Obj_Customer obj_Customer) { this.obj_Customer = obj_Customer; }
	    public void setListMonths(ArrayList<String> lst_Months) { this.lst_Months = lst_Months; }
	    public void setListMonthlyRate(ArrayList<String>lst_Monthly_Rate) { this.lst_Monthly_Rate = lst_Monthly_Rate; }
	
	
}
