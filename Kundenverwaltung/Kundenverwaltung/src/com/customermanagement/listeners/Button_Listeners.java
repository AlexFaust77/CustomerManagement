package com.customermanagement.listeners;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.customermanagement.database.SQL_Statements;
import com.customermanagement.entities.Obj_Customer;
import com.customermanagement.entities.Obj_Order;
import com.customermanagement.gui.All_Customers_View;
import com.customermanagement.gui.Chart_fx;
import com.customermanagement.gui.Table_fx;
import com.customermanagement.helpers.Calculator;
import com.customermanagement.helpers.Clear_Data;
import com.customermanagement.helpers.Gui_States;
import com.customermanagement.helpers.Save_Database_Information;
import com.customermanagement.inputchecks.InputChecks;
import com.customermanagement.main.Cust_Gui;
import com.customermanagement.reports.Excel_Export;
import com.customermanagement.reports.PDF_Builder;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jxl.write.WriteException;

public class Button_Listeners {

	 private boolean dataBase_Result;										     // vorher DBKontakt
	
	 Obj_Customer obj_Customer =new Obj_Customer();						         // vorher objDaten
	 // Obj_Order obj_Order = new Obj_Order();	         					     // vorher objBestellung
	 Obj_Order obj_Order = new Obj_Order("","","","",0,0.0,0.0,0.0,0.0);;	     // test 22.09.2016
	 Calculator calculate = new Calculator();									 // vorher rechnen
	 Clear_Data data_cleaner = new Clear_Data();							     // vorher datenleeren
	 SQL_Statements dataBase_Request = new SQL_Statements();			         // vorher databasecon
	 FileChooser select_Database = new FileChooser();						     // vorher chooseDB
	 Excel_Export excel_Export;				     								 // vorher excelexp
	 InputChecks checkInput = new InputChecks();
	 
	 private ArrayList<String> lst_month = new ArrayList<String>();			     // vorher monate
	 private ArrayList<String> lst_monthly_Rate = new ArrayList<String>();       // vorher monatlicheRate
	 private ArrayList<Obj_Order> orderlist = new ArrayList<Obj_Order>();        // vorher obj_Bestellliste
	 private ArrayList<String>lst_All_Customers = new ArrayList<String>();       // vorher liste_Kundenummern
	 private ObservableList<Obj_Customer>lst_Obj_Customer = FXCollections.observableArrayList(); // vorher liste_Obj_Kunde
	 
	 public Button_Listeners(Gui_States gui_State,Cust_Gui Obj_Cust_Gui,Logger logger, Stage primaryStage) {
	
		 // 1 ***************** Finished !!!!! ********************************************************************************************************
		 Obj_Cust_Gui.btn_Cust_Search.setOnAction(new EventHandler<ActionEvent>() {                                     								    // Search one Customer with Customernumber
	            @Override
	            public void handle(ActionEvent e) {
	            	  obj_Customer = data_cleaner.cleanObjCustomer(obj_Customer);
	                  				 data_cleaner.cleanLists(lst_month,orderlist,lst_Obj_Customer,lst_All_Customers,lst_monthly_Rate);
	                  				
	                  obj_Customer = dataBase_Request.getCustomer_AND_Orders(obj_Customer,Obj_Cust_Gui.getCustNr(),Obj_Cust_Gui.getActiveDB(),logger);  	// Database request
	                  logger.info("Database Request - Done");
	                  
	                  Obj_Cust_Gui.setCustLastName(obj_Customer.getLastname());                															    // Filling the values into the Gui Fields
	                  Obj_Cust_Gui.setCustName(obj_Customer.getFirstname());
	                  Obj_Cust_Gui.setCustStreet(obj_Customer.getStreet());
	                  Obj_Cust_Gui.setCustHNr(Integer.toString(obj_Customer.getHouseNo()));
	                  Obj_Cust_Gui.setCustPc(Integer.toString(obj_Customer.getPostcode()));
	                  Obj_Cust_Gui.setCustRes(obj_Customer.getResidenz());
	                  Obj_Cust_Gui.setBestellNummernListe(obj_Customer.getOrderlist());
	                  Obj_Cust_Gui.setOrderCount(obj_Customer.getOrderlist().size());
	                  Obj_Cust_Gui.setTotal(Double.toString(obj_Customer.getCustTotal()));
	                              
	                  			  calculate.fill_month_lst(Obj_Cust_Gui,dataBase_Request);
	                  lst_month = calculate.getLstMonth();
	                  logger.info("Monthlist filled - Done");
	                  
	                  orderlist = calculate.getOrder_Objects(Obj_Cust_Gui, obj_Order,dataBase_Request);
	                  			  calculate.setLstMonth(lst_month);
	                  			  calculate.getAll_Rates();
	                              
	                  lst_monthly_Rate = calculate.getMonthlyRate();
	                  logger.info("All Rates calculated - Done");                         
	               
	                  Chart_fx rate_Chart = new Chart_fx();																									// create chart for monthly Rates
	                           rate_Chart.setMonthlyRate(lst_monthly_Rate);
	                           rate_Chart.setMonthslist(lst_month);
	                           rate_Chart.createChartWithToolTips();
	                  Obj_Cust_Gui.setLineData(rate_Chart.getChartData()); 
	                  
	                  logger.info("ratechart created - Done"); 
	                
	                  TableView<Obj_Order> view_fx_Table = new TableView<Obj_Order>();																		//Table for Order Overview
	                  Table_fx table = new Table_fx();
	                           table.setOrderlist(orderlist);
	                           view_fx_Table = table.erstelleFXTableView();
	                           Obj_Cust_Gui.tb_Table.setContent(view_fx_Table);
	                  
	                  logger.info("order Table created - Done");          
	            }
	        });
	
		 // 2 ***************** FINISHED !!!!! - create New Customer ********************************************************************************************************
		 Obj_Cust_Gui.btn_Cust_New.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent e) {
	                   gui_State.create_Customer(Obj_Cust_Gui);																							// GUI State New Customer
	            }
	        });
	        
	     // 3
		 Obj_Cust_Gui.btn_Order_New.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent e) {
	                
	                if(Obj_Cust_Gui.getCustNr().length() > 0) {																						   // Check CustomerNumber is available
	                    gui_State.create_Order(Obj_Cust_Gui);
	                    Obj_Cust_Gui.setOrderCustNr(Obj_Cust_Gui.getCustNr());
	                } else {
	                    
	                    // add MSG = No Customer No
	                }
	            }
	        });    
	     // 4 - Delete all Orders from one Customer - and Delete the Selected Customer
		 Obj_Cust_Gui.btn_Cust_Del.setOnAction(new EventHandler<ActionEvent>() {
                @Override
	            public void handle(ActionEvent e) {
	                Alert customer_Confirmation = new Alert(AlertType.CONFIRMATION);
	                	  customer_Confirmation.setTitle("Kunde wirklich löschen ?");
	                	  customer_Confirmation.setHeaderText("Kunde wirklich löschen ?\nEs werden alle Datensätze zu diesem Kunden gelöscht !");
	                	  customer_Confirmation.setContentText("Soll der Kunde-Nr.: " + Obj_Cust_Gui.getCustNr() + "\n wirklich gelöscht werden?" );
	                      Optional<ButtonType> result = customer_Confirmation.showAndWait(); 
	                
	                      if(result.get() == ButtonType.OK) {																							// if OK - Delete Customer
	                           
	                           for(int each_Order = 0; each_Order < Obj_Cust_Gui.getBestellNummernListe().size();each_Order++) {             			// First Delete all Orders from this Customer
	                               String act_Order = orderlist.get(each_Order).getOrderNo();
	                               dataBase_Result = dataBase_Request.delete_Order(Obj_Cust_Gui.getActiveDB(), Obj_Cust_Gui.getCustNr(), act_Order);
	                           }
	                           
	                           dataBase_Result = dataBase_Request.delete_Customer(Obj_Cust_Gui.getActiveDB(), Obj_Cust_Gui.getCustNr());				// Then Delete Customer
	                           
	                           System.out.println("Löschen erfolgreich ? " + dataBase_Result);
	                           gui_State.gui_State_Start(Obj_Cust_Gui);
	                       } else {
	                           // MSG - Customer not found
	                       }
	            }
	        });
	        
	        // 5 ***************** FINISHED !!!!! - Select Database  ********************************************************************************************************
		    Obj_Cust_Gui.btn_Select_Db.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent e) {
	                                          
	            	select_Database.setTitle("Bitte Datenbank auswählen !");
	                                               
	                               File selected_database = select_Database.showOpenDialog(primaryStage);					
	                                    if(selected_database != null) {
	                                                                
	                                                dataBase_Result = dataBase_Request.DatabaseConnection(selected_database.getAbsolutePath());
	                                        
	                                                if(dataBase_Result) {
	                                                	Obj_Cust_Gui.setActiveDB(selected_database.getAbsolutePath());
	                                                	Save_Database_Information last_used_database = new Save_Database_Information();
	                                                							  last_used_database.save_Database_Info(selected_database.getAbsolutePath(), logger);
	                                                	Obj_Cust_Gui.setGoodResult();
	                                                	logger.info("DB selection - Status : OK" + selected_database.getAbsolutePath());
	                                                }  else {
	                                                	Obj_Cust_Gui.setActiveDB("Verbindungsaufbau fehlgeschlagen");
	                                                	logger.error("DB selection - Arrrrgg : something goes wrong with your File" + selected_database.getAbsolutePath());
	                                                	Obj_Cust_Gui.setBadResult();
	                                                }       
	                                                         
	                                        
	                                                                               
	                                    }
	            }
	        });
	        
	        // 6 ***************** FINISHED !!!!! - Build new Database  ********************************************************************************************************
		    Obj_Cust_Gui.btn_New_Db.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent e) {
	            	select_Database.setTitle("Neue Datenbank erstellen !");
	             
	                File dataBase_Name = select_Database.showSaveDialog(primaryStage);
	                     if(dataBase_Name != null) {
	                    	 dataBase_Request.build_Database(dataBase_Name.getName() + ".db");
	                    	 Obj_Cust_Gui.setActiveDB(dataBase_Name.getAbsolutePath() + ".db");
	                         
	                         System.out.println("Eingabe : " + dataBase_Name.getName());
	                     } else {
	                         // Nichts tun
	                     }
	            }
	        });
	        // 7  ***************** FINISHED !!!!! - Save new Customer  ********************************************************************************************************
		    Obj_Cust_Gui.btn_Cust_Save.setOnAction(new EventHandler<ActionEvent>() {
	            boolean doubled_Customer = false;
	            
	            @Override
	            public void handle(ActionEvent e) {
	               obj_Customer.setCustNo(Obj_Cust_Gui.getCustNr());
	               obj_Customer.setLastname(Obj_Cust_Gui.getCustLastName());
	               obj_Customer.setFirstname(Obj_Cust_Gui.getCustName());
	               obj_Customer.setStreet(Obj_Cust_Gui.getCustStreet());
	               obj_Customer.setHouseNo(Integer.parseInt(Obj_Cust_Gui.getCustHNr()));
	               obj_Customer.setPostcode(Integer.parseInt(Obj_Cust_Gui.getCustPc()));
	               obj_Customer.setResidenz(Obj_Cust_Gui.getCustRes());
	               // doppelteKdnr = dataBaseCon.doppelteKundennr(objDaten, kdverw_Obj.getKdNr(), kdverw_Obj.getAktiveDB());
	               // Fehler bei Kundennummer abfrage           
	               
	               //if(!doppelteKdnr) {
	                   dataBase_Result = dataBase_Request.new_Customer(obj_Customer, Obj_Cust_Gui.getActiveDB());
	                   Obj_Cust_Gui.setBtnCustCancel(false);
	                   Obj_Cust_Gui.setBtnCustSave(false);
	                   gui_State.gui_State_Start(Obj_Cust_Gui);
	               //} else {
	               //    JOptionPane.showMessageDialog(null, "Datensatz konnte nicht gespeichert werden !!!\n"
	               //                                      + "Kundennummer bereits vorhanden !!!", "Fehler beim anlegen des Kunden", JOptionPane.CANCEL_OPTION);
	               //}
	            }
	                   
	        });
	        // 8 ***************** FINISHED !!!!! - Cancel - Not Saving Data  ********************************************************************************************************
		    Obj_Cust_Gui.btn_Cust_NoSave.setOnAction(new EventHandler<ActionEvent>() {   
	            @Override
	            public void handle(ActionEvent e) {
	            gui_State.gui_State_Start(Obj_Cust_Gui);
	            }
	        });
	        // 9
		    Obj_Cust_Gui.btn_Order_Save.setOnAction(new EventHandler<ActionEvent>() {					 					// Save Order Data 
	            
	            @Override
	            public void handle(ActionEvent e) {
	                                        
	                    obj_Order.setOrderNo(Obj_Cust_Gui.getOrderNr());
	                    obj_Order.setOrderDate(Obj_Cust_Gui.getOrderDate());
	                    obj_Order.setPayStart(Obj_Cust_Gui.getPayStart());
	                    obj_Order.setPayEnd(Obj_Cust_Gui.getPayEnd());
	                    obj_Order.setRateCount(Integer.parseInt(Obj_Cust_Gui.getRateCount()));
	                    obj_Order.setFirstRate(Double.parseDouble(Obj_Cust_Gui.getFirstRate()));
	                    obj_Order.setRate(Double.parseDouble(Obj_Cust_Gui.getRate()));
	                    obj_Order.setOrderSummary(Double.parseDouble(Obj_Cust_Gui.getOrderSummary()));
	                    //objBestellung.setBestellsumme(summary);
	                    obj_Order.setCustNo(Double.parseDouble(Obj_Cust_Gui.getOrderCustNr()));
	                    
	                    if(Obj_Cust_Gui.getOrderFlag() == 0) {
	                                     
	                        dataBase_Result = dataBase_Request.new_Order(obj_Order, Obj_Cust_Gui.getActiveDB());
	               
	                        Obj_Cust_Gui.setListBestellnummer(""+obj_Order.getOrderNo());
	                        System.out.println("%f"+obj_Order.getOrderNo());    
	               
	                            if(dataBase_Result) {
	                            	Obj_Cust_Gui.setBtnOrderNoSave(false);
	                            	Obj_Cust_Gui.setBtnOrderSave(false);
	                            	gui_State.cancel_Order(Obj_Cust_Gui);
	                            } else {
	                                // Buttons sichtbar lassen + Fehlermeldung
	                            }
	                        
	                    } else {
	                    	dataBase_Result = dataBase_Request.change_Order(obj_Order, Obj_Cust_Gui.getActiveDB());
	                                                
	                        if(dataBase_Result) {
	                        	Obj_Cust_Gui.setBtnOrderNoSave(false);
                            	Obj_Cust_Gui.setBtnOrderSave(false);
	                        	Obj_Cust_Gui.setOrderFlag(0);
	                        } else {
	                            // buttons sichtbar lassen + Fehlermeldung
	                        }
	                        
	                      
	                    }        
	                
	            }
	                   
	        });            
	        // 10 ***************** FINISHED !!!!! - Cancel - Not Saving Order  ********************************************************************************************************  
		    Obj_Cust_Gui.btn_Order_NoSave.setOnAction(new EventHandler<ActionEvent>() {            
	            @Override
	            public void handle(ActionEvent e) {
	                gui_State.change_Order(Obj_Cust_Gui);
	            }
	        });
	        // 11
		    Obj_Cust_Gui.lstv_Order_List.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
	           
	            @Override
	            public void changed(ObservableValue<? extends String> observable, String oldValue, String aktuelleBestNr) {
	              
	                System.out.println("Ausgewähltes Element : " + "Altes Element " + oldValue + " neues Element " + aktuelleBestNr);
	                
	                  
	                  obj_Order = dataBase_Request.getOne_Order(obj_Order,aktuelleBestNr,Obj_Cust_Gui.getCustNr(),Obj_Cust_Gui.getActiveDB());   // Database Request - returns Order Object
	            
	                  Obj_Cust_Gui.setOrderCustNr(new Double(obj_Order.getCustNo()).toString());     					// Set Values in GUI
	                  Obj_Cust_Gui.setOrderNr(obj_Order.getOrderNo());
	                  Obj_Cust_Gui.setOrderDate(obj_Order.getOrderDate());
	                  Obj_Cust_Gui.setOrderSummary(obj_Order.getOrderSummary());
	                  Obj_Cust_Gui.setFirstRate(obj_Order.getFirstRate());
	                  Obj_Cust_Gui.setRate(obj_Order.getRate());
	                  Obj_Cust_Gui.setPayStart(obj_Order.getPayStart());
	                  Obj_Cust_Gui.setPayEnd(obj_Order.getPayEnd());
	                  Obj_Cust_Gui.setRateCount(obj_Order.getRateCount());
	                           
	            }
	        });
	        //12
		    Obj_Cust_Gui.btn_Plan_Pdf.setOnAction(new EventHandler<ActionEvent>() {
	            
	            @Override
	            public void handle(ActionEvent e) {
	            
	            	orderlist = calculate.getOrder_Objects(Obj_Cust_Gui, obj_Order,dataBase_Request);
	            				calculate.setLstMonth(lst_month);
	                               
	            
	            PDF_Builder create_Pdf = new PDF_Builder();							// Create PDF Export
	            			create_Pdf.setCustomer(obj_Customer);
	            			create_Pdf.setListMonthlyRate(lst_monthly_Rate);
	            			create_Pdf.setListMonths(lst_month);
	            			create_Pdf.create_PDF_Export(logger);
	            }
	        });
	        
	       // 13 ***************** FINISHED !!!!! - Delete Order  ********************************************************************************************************  
	       Obj_Cust_Gui.btn_Order_Del.setOnAction(new EventHandler<ActionEvent>() {
	             @Override
	             public void handle(ActionEvent e) {
	                 
	                 Alert customer_Confirmation = new Alert(AlertType.CONFIRMATION);
	                 	   customer_Confirmation.setTitle("Bestellung löschen ?");
	                 	   customer_Confirmation.setHeaderText("Bestellung wirklich löschen ?");
	                 	   customer_Confirmation.setContentText("Soll die Bestellung Nr.: " + Obj_Cust_Gui.getOrderNr() + "\n wirklich gelöscht werden?" );
	                       Optional<ButtonType> result = customer_Confirmation.showAndWait();
	                       
	                       if(result.get() == ButtonType.OK) {															// Delete Order by Customer confirmation
	                           
	                           dataBase_Result = dataBase_Request.delete_Order(Obj_Cust_Gui.getActiveDB(), Obj_Cust_Gui.getCustNr(), Obj_Cust_Gui.getOrderNr());
	                           System.out.println("Löschen erfolgreich ? " + dataBase_Result);
	                           gui_State.cancel_Order(Obj_Cust_Gui);
	                       } else {
	                           // MSG - Class Impl
	                       }
	            }
	        });
	        
	       // 14 ***************** FINISHED !!!!! - Change Order  ********************************************************************************************************
	       Obj_Cust_Gui.btn_Order_Change.setOnAction(new EventHandler<ActionEvent>() {
	             @Override
	             public void handle(ActionEvent e) {
	                 
	                gui_State.change_Order(Obj_Cust_Gui); 													// GUI State => Change Order
	                Obj_Cust_Gui.setOrderFlag(1);         												   // Set Flag for Order Update
	                System.out.println("Bestell Marker : " + Obj_Cust_Gui.getOrderFlag());
	            }
	        });
	       // 15 ***************** FINISHED !!!!! - View All Customer as Table  *******************************************************************************************
	       Obj_Cust_Gui.m_Cust_View.setOnAction(new EventHandler<ActionEvent>() {
	             @Override
	             public void handle(ActionEvent e) {
	            	lst_All_Customers = dataBase_Request.getAll_Cust_Nr(Obj_Cust_Gui.getActiveDB());
	                         
	                for(int current_Cust_Nr = 0; current_Cust_Nr < lst_All_Customers.size();current_Cust_Nr++) {
	                	lst_Obj_Customer.add(dataBase_Request.getCustomer_AND_Orders(new Obj_Customer(),lst_All_Customers.get(current_Cust_Nr),Obj_Cust_Gui.getActiveDB(),logger)); // Returns customer object added to object List
	                }
	    
	                All_Customers_View customers_View = new All_Customers_View(); 			// create  Object for Customer View
	                				   customers_View.setLstCustomer(lst_Obj_Customer);     // Set List with all Customer Objects
	                             	   customers_View.create_All_Customers_Table();         // create View
	                          
	            }
	        });
	        // 16 ************************* FERTIG *********************************
	       Obj_Cust_Gui.m_Exit.setOnAction(new EventHandler<ActionEvent>() {
	             @Override
	             public void handle(ActionEvent e) {
	                    System.exit(0);
	            }
	        });
	        // 17 ***************** FINISHED !!!!! - Excel Export  ********************************************************************************************************   
	        Obj_Cust_Gui.btn_Plan_Excel.setOnAction(new EventHandler<ActionEvent>() {
	             @Override
	             public void handle(ActionEvent e) {
	                 try {
	                	
	                    String fileName = getFileName("Excel Export",".xls",primaryStage);
	                   	                    
	                    excel_Export = new Excel_Export(); 
	                    excel_Export.setObj_Customer(obj_Customer);
	                    excel_Export.setLstMonthlyRate(lst_monthly_Rate);
	                    excel_Export.setLst_Month(lst_month);
	                    excel_Export.setOutputFile(fileName);
	                    excel_Export.write_Excel_Export();
	                    
	                 } catch (IOException | WriteException ex) {
	                	 logger.error("Excel Export : " + ex.getLocalizedMessage());
	                 }
	                   
	                  
	                   
	                   
	                   
	            }
	        });
	        
	        
	        
	    }
	
	 // 18 ***************** FINISHED !!!!! - internal Method returns file Name include Path  ********************************************************************************************************     
	private String getFileName(String title, String fileExt,Stage primaryStage) {

		    String str_fileName ="";         										// Placeholder filename
	        select_Database.setTitle(title);  
	           
	        File fileName = select_Database.showSaveDialog(primaryStage);  			// Save Dialog
	            if(fileName != null) {
	                str_fileName = fileName.getAbsolutePath() + fileExt;
	                	             
	            } else {
	                str_fileName = "";
	            }
	    return str_fileName; 														// returns filename and FilePath
	}   

	 public void setLstMonth(ArrayList<String> lst_month ) { this.lst_month = lst_month; }
	

}
	 
	 
	 
	 
	 
	 
	 
	

