package com.customermanagement.reports;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.customermanagement.entities.Obj_Customer;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;



public class Excel_Export {

	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	private String output_File;										
	private int int_Row;																	// vorher int_aktZeile
	private int int_Column;																	// vorher int_aktSpalte
	private Obj_Customer obj_Customer;														// vorher obj_Kunde
	private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
	Date current_Date = new Date(); 														// vorher currentTime
	private ArrayList<String> lst_Month = new ArrayList<String>();						    // vorher monatsliste
	private ArrayList<String> lst_Monthly_Rate = new ArrayList<String>();					// vorher monaticheRate
	
	
	
	 public void write_Excel_Export() throws IOException, WriteException {
		 
		    File file = new File(output_File);												// create File Object => outputFile
		    WorkbookSettings wbSettings = new WorkbookSettings();
		    				 wbSettings.setLocale(new Locale("de", "DE"));
		    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		    				 workbook.createSheet("Ratenplan", 0);
		    
		    WritableSheet excelSheet = workbook.getSheet(0);
		 
		    createLabel(excelSheet);
		    createContent(excelSheet);

		    workbook.write();
		    workbook.close();
		  }

		  private void createLabel(WritableSheet sheet) throws WriteException {
			  
			  WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);     // Lets create a times font
			  
			  times = new WritableCellFormat(times10pt);							 // Define the cell format
		   	  times.setWrap(true);													 // Automatic Cell Wrapping
			  
			  WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,				//  create a bold font with underlines
					  												 UnderlineStyle.SINGLE);
			  timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
			  timesBoldUnderline.setWrap(true);                                     // Automatic Cell Wrapping

			  CellView cell_view = new CellView();
			  		   cell_view.setFormat(times);
			  		   cell_view.setFormat(timesBoldUnderline);
			  		   cell_view.setAutosize(true);
		    
			 
			  addCaption(sheet, 0, 0, "Kundennummer : ");						    // Create Head of Table  // Column // Row
			  addCaption(sheet, 1, 0, obj_Customer.getCustNo());
		    
			  addCaption(sheet, 0, 1, "Name : ");
			  addCaption(sheet, 1, 1, obj_Customer.getLastname());
		    
			  addCaption(sheet, 0, 2, "Vorname : ");
			  addCaption(sheet, 1, 2, obj_Customer.getFirstname());
		    
		      addCaption(sheet, 0, 3, "Datum : ");
		      addCaption(sheet, 1, 3, formatter.format(current_Date));
		    
		      int_Row = 5;
		      int_Column = 0;
		}

		  private void createContent(WritableSheet sheet) throws WriteException,RowsExceededException {
		     		    
		    
		    int int_compLines = lst_Month.size() / 12;						// complete Lines
		    int int_incompLines = lst_Month.size()%12;						// not completed Lines
		    
		    int int_Index = 0;
		  
		    DecimalFormat format = new DecimalFormat("#######0.00"); 
		       
		    ArrayList<Double> ratenliste = new ArrayList<Double>();
		    
		    for(int value = 0; value < lst_Monthly_Rate.size();value++) {			// convert String list to Double List
		        ratenliste.add(Double.parseDouble(lst_Monthly_Rate.get(value)));
		    }
		    
		    for(int lines = 0; lines < int_compLines;lines++) {    					// Fill values into Table
		        for (int column = int_Column; column < 12; column++) {
		  
		            addCaption(sheet,column,int_Row,lst_Month.get(int_Index));
		            String str_output_Rate = format.format(ratenliste.get(int_Index)) + " €";
		            addCaption(sheet,column,(int_Row + 1),str_output_Rate);
		            int_Index++;
		        }
		    int_Row = int_Row + 3;
		    int_Column = 0;
		    }
		    
		    
		    
		  }

		  private void addCaption(WritableSheet sheet, int column, int row, String s)
		                          throws RowsExceededException, WriteException {
		    Label label;
		    label = new Label(column, row, s, timesBoldUnderline);
		    sheet.addCell(label);
		  }

		  private void addLabel(WritableSheet sheet, int column, int row, String s)
		                        throws WriteException, RowsExceededException {
		    Label label;
		    label = new Label(column, row, s, times);
		    sheet.addCell(label);
		  }
		 
		  
		public void setLst_Month(ArrayList<String>lst_Month) { this.lst_Month = lst_Month; }  
		public void setLstMonthlyRate(ArrayList<String>lst_Monthly_Rate) { this.lst_Monthly_Rate = lst_Monthly_Rate; }
		public void setOutputFile(String output_File) { this.output_File = output_File; }
		public void setObj_Customer(Obj_Customer obj_Customer) { this.obj_Customer = obj_Customer; }
		//public void setOrder(Obj_Order obj_Order) { this.obj_Order = obj_Order; }
	
	
	
}
