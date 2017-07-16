package com.customermanagement.listeners;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.customermanagement.database.HibernateStatements;
import com.customermanagement.database.SQL_Statements;

import com.customermanagement.entities.Obj_Customer;
import com.customermanagement.entities.Obj_Order;
import com.customermanagement.helpers.Calculator;
import com.customermanagement.helpers.Clear_Data;
import com.customermanagement.helpers.Gui_States;
import com.customermanagement.helpers.Save_Database_Information;
import com.customermanagement.inputchecks.InputChecks;
import com.customermanagement.main.Cust_Gui;
import com.customermanagement.reports.Excel_Export;
import com.customermanagement.reports.PDF_Builder;
import com.customermanagement.statistics.All_Customers_View;
import com.customermanagement.statistics.Chart_fx;
import com.customermanagement.statistics.Table_fx;

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

	 private boolean dataBase_Result;										     // DB Contact Result
	
	 Obj_Customer obj_Customer =new Obj_Customer();						         // Object Customer
	 Obj_Order obj_Order = new Obj_Order("",null,null,null,0,0.0,0.0,0.0,"");    // Object Order
	 Clear_Data data_cleaner = new Clear_Data();							     // for clearing all Lists
	
	 FileChooser select_Database = new FileChooser();						     // Filechooser for Database
	 Excel_Export excel_Export;				     								 // Excel Export class
	 InputChecks checkInput = new InputChecks();
	 private HibernateStatements statementsHibernate = new HibernateStatements();// for Hibernate Database Access
	 
	 private ArrayList<String> lst_month = new ArrayList<String>();			     // list for Months
	 private ArrayList<String> lst_monthly_Rate = new ArrayList<String>();       // list all monthly Rates
	 
	 
	 private ArrayList<Obj_Order> orderlist = new ArrayList<Obj_Order>();        // list for all Orders No - will be removed use Objectlist instead
	 private ObservableList<Obj_Order>lstAllOrders = FXCollections.observableArrayList();  // NEUE LISTE änderung der GUI
	 
	 private ArrayList<String>lst_All_Customers = new ArrayList<String>();       // list of Customer No
	 private ObservableList<Obj_Customer>lst_Obj_Customer = FXCollections.observableArrayList(); // list for all Customer Objects
	 
	 // Stage primaryStage will be removed  look line 222 if its neccesary
	 public Button_Listeners(Gui_States gui_State,Cust_Gui Obj_Cust_Gui,Logger logger, 
			 				 Stage primaryStage, Calculator calculate, SQL_Statements dataBaseRequest, TableView<Obj_Order> fx_Table_View) {
	
		 // Search one Customer with Customernumber
		 Obj_Cust_Gui.btn_Cust_Search.setOnAction(new EventHandler<ActionEvent>() {                                     								  
	            @Override
	            public void handle(ActionEvent e) {
	            	  obj_Customer = data_cleaner.cleanObjCustomer(obj_Customer);
	                  				 data_cleaner.cleanLists(lst_month,orderlist,lst_Obj_Customer,lst_All_Customers,lst_monthly_Rate);
	                  // Database request				
	                  obj_Customer = dataBaseRequest.getCustomer(obj_Customer,Obj_Cust_Gui.getCustNr(),Obj_Cust_Gui.getActiveDB(),logger);  	
	                  logger.info("Database Request - Done");
	                  // Filling the values into the Gui Fields
	                  Obj_Cust_Gui.setCustId(obj_Customer.getId());
	                  Obj_Cust_Gui.setCustLastName(obj_Customer.getLastname());                															    
	                  Obj_Cust_Gui.setCustName(obj_Customer.getFirstname());
	                  Obj_Cust_Gui.setCustStreet(obj_Customer.getStreet());
	                  Obj_Cust_Gui.setCustHNr(Integer.toString(obj_Customer.getHouseNo()));
	                  Obj_Cust_Gui.setCustPc(Integer.toString(obj_Customer.getPostcode()));
	                  Obj_Cust_Gui.setCustRes(obj_Customer.getResidenz());
	                  // Obj_Cust_Gui.setBestellNummernListe(obj_Customer.getOrderlist());
	                  Obj_Cust_Gui.setOrderCount(obj_Customer.getOrderlist().size());
	                  Obj_Cust_Gui.setTotal(Double.toString(obj_Customer.getCustTotal()));
	                              
	                  			  calculate.fill_month_lst(Obj_Cust_Gui,dataBaseRequest,logger);
	                  lst_month = calculate.getLstMonth();
	                  logger.info("Monthlist filled - Done");
	                  
	                  
	                  
	                  
	                  lstAllOrders = dataBaseRequest.getAllOrderObjects(obj_Customer,Obj_Cust_Gui.getCustNr(),Obj_Cust_Gui.getActiveDB(),logger); 
	                  
	                  fillOrderNumberList(lstAllOrders,Obj_Cust_Gui,logger);
	                  
	                  System.out.println("Grösse der Order Liste " + lstAllOrders.size());
	                  
	                  //hier der weiter damit ich weis wo ich war
	                  
                      //orderlist = calculate.getOrder_Objects(Obj_Cust_Gui, obj_Order,dataBase_Request,logger);
	                  			    calculate.setLstMonth(lst_month);
	                  			    calculate.getAll_Rates(logger,lstAllOrders); // <= hier läuft was schief -- Hier weiter
	                  
	                  
	                  System.out.println("Vorhandene Bestellungen : " + lstAllOrders.size()+ "\n");
	                  
	                  if(lstAllOrders.size() > 0 ) {
	                	  	   obj_Customer.setLstAllOrders(lstAllOrders);	 // Bestellobjekte werden Kunden zugewiesen
	                	  	   System.out.println("Anzahl Bestellobjekte Customer : " + obj_Customer.getLstAllOrders().size() + "\n");
	                  
	                	  	   lst_monthly_Rate = calculate.getMonthlyRate();
	                	  	   logger.info("All Rates calculated - Done");
	                  
	                	  	   for(int i = 0; i < lst_monthly_Rate.size(); i++) {
	                	  		   	System.out.println("Liste der Monatesraten Rate => " + (i + 1) + " Aktueller Wert : " + lst_monthly_Rate.get(i)  +"\n" );
	                	  	   }
	                  
	                	  	   // create chart for monthly Rates
	                	  	   Chart_fx rate_Chart = new Chart_fx();																									
	                	  	   		    rate_Chart.setMonthlyRate(lst_monthly_Rate);
	                	  	   		    rate_Chart.setMonthslist(lst_month);
	                	  	   		    rate_Chart.createChartWithToolTips();
	                	  	   		    Obj_Cust_Gui.setLineData(rate_Chart.getChartData());
	                  
	                	  	   logger.info("ratechart created - Done"); 
	                
	                	  	   //Table for Order Overview - getestet funktioniert
	                	  	  
	                	  	   
	                	  	   
	                	  	   
	                	  	   
	                	  	   TableView<Obj_Order> view_fx_Table = new TableView<Obj_Order>();
	                	  	                        view_fx_Table.setId("CurrentOrders");
	                	  	   Table_fx table = new Table_fx();
	                	  	   			table.setOrderlist(lstAllOrders);
	                	  	   			view_fx_Table = table.createTable();
	                	  	   			
	                	  	   			Obj_Cust_Gui.tabCurrentOrders.setContent(view_fx_Table);
	                	  	   			
	                	  	   logger.info("order Table created - Done");
	                  } else {
	                	  
	                  }
	            }
	        });
	
		 // GUI State New Customer
		 Obj_Cust_Gui.btn_Cust_New.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent e) {  
	                   gui_State.create_Customer(Obj_Cust_Gui);
	                   logger.info("Gui State set - Done"); 
	            }
	        });

	     // Delete all Orders from one Customer - and Delete the Selected Customer
		 Obj_Cust_Gui.btn_Cust_Del.setOnAction(new EventHandler<ActionEvent>() {
                @Override
	            public void handle(ActionEvent e) {
	                Alert customer_Confirmation = new Alert(AlertType.CONFIRMATION);
	                	  customer_Confirmation.setTitle("Kunde wirklich loeschen ?");
	                	  customer_Confirmation.setHeaderText("Kunde wirklich loeschen ?\nEs werden alle Datensaetze zu diesem Kunden geloescht !");
	                	  customer_Confirmation.setContentText("Soll der Kunde-Nr.: " + Obj_Cust_Gui.getCustNr() + "\n wirklich geloescht werden?" );
	                      Optional<ButtonType> result = customer_Confirmation.showAndWait(); 
	                      // if OK - Delete Customer
	                      if(result.get() == ButtonType.OK) {
	                    	   logger.info("OK - Start to Delete Datarecord");
	                    	   // First Delete all Orders from this Customer
	                     //      for(int each_Order = 0; each_Order < Obj_Cust_Gui.getBestellNummernListe().size();each_Order++) {             			
	                     //          String act_Order = orderlist.get(each_Order).getOrderNo();
	                     //          dataBase_Result = dataBase_Request.delete_Order(Obj_Cust_Gui.getActiveDB(), Obj_Cust_Gui.getCustNr(), act_Order,logger);
	                      //     }
	                           logger.info("Orders Deleted - Done");
	                           // Then Delete Customer
	                           dataBase_Result = dataBaseRequest.delete_Customer(Obj_Cust_Gui.getActiveDB(), Obj_Cust_Gui.getCustNr(),logger);				
	                           logger.info("Customer Deleted - Done");
	                          
	                           gui_State.gui_State_Start(Obj_Cust_Gui);
	                           logger.info("GUI State - Set Start - Done");
	                       } else {
	                           // MSG - Customer not found
	                       }
	            }
	        });
	        
	        // Select Database  
		    Obj_Cust_Gui.btn_Select_Db.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent e) {
	                                          
	            	select_Database.setTitle("Bitte Datenbank auswaehlen !");
	                                               
	                               File selected_database = select_Database.showOpenDialog(primaryStage);					
	                                    if(selected_database != null) {
	                                                
	                                    	        // connect to selected Database
	                                                dataBase_Result = dataBaseRequest.DatabaseConnection(selected_database.getAbsolutePath(),logger);
	                                                
	                                                if(dataBase_Result) {  // Connection OK
	                                                	Obj_Cust_Gui.setActiveDB(selected_database.getAbsolutePath());
	                                                	Save_Database_Information last_used_database = new Save_Database_Information();
	                                                							  last_used_database.save_Database_Info(selected_database.getAbsolutePath(), logger);
	                                                	Obj_Cust_Gui.setGoodResult();
	                                                	logger.info("DB selection - Status : OK" + selected_database.getAbsolutePath());
	                                                }  else {             // connection failed
	                                                	Obj_Cust_Gui.setActiveDB("Verbindungsaufbau fehlgeschlagen");
	                                                	logger.error("DB selection - Arrrrgg : something wrong with your File" + selected_database.getAbsolutePath());
	                                                	Obj_Cust_Gui.setBadResult();
	                                                }       
	                                    }
	            }
	        });
	        
	        // Build the new SQL - Lite Database 
		    Obj_Cust_Gui.btn_New_Db.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent e) {
	            	select_Database.setTitle("Neue Datenbank erstellen !");
	            	
	                File dataBase_Name = select_Database.showSaveDialog(primaryStage);
	                     if(dataBase_Name != null) {
	                                        	 				   
	                    	 dataBaseRequest.build_Database(dataBase_Name.getName() + ".db",logger);
	                    	 Obj_Cust_Gui.setActiveDB(dataBase_Name.getAbsolutePath() + ".db");
	                         logger.info("Database builded - Information Field set");
	                         
	                     } else {
	                         // Nichts tun
	                     }
	            }
	        });
	        // Save new Customer  
		    Obj_Cust_Gui.btn_Cust_Save.setOnAction(new EventHandler<ActionEvent>() {
	            boolean doubled_Customer = false;
	            
	            
	            
	           
	            
	            @Override
	 public void handle(ActionEvent e) {
	            	
	   	System.out.println( "Checkbox property : " + Obj_Cust_Gui.getChkHibernateValue() );
	            	
	            	
	            	
	 	// Fill the customer Object with values from Gui	
	 	obj_Customer.setCustNo(Obj_Cust_Gui.getCustNr());
	 	obj_Customer.setLastname(Obj_Cust_Gui.getCustLastName());
	 	obj_Customer.setFirstname(Obj_Cust_Gui.getCustName());
	 	obj_Customer.setStreet(Obj_Cust_Gui.getCustStreet());
	 	obj_Customer.setHouseNo(Integer.parseInt(Obj_Cust_Gui.getCustHNr()));
	 	obj_Customer.setPostcode(Integer.parseInt(Obj_Cust_Gui.getCustPc()));
	 	obj_Customer.setResidenz(Obj_Cust_Gui.getCustRes());
	 	logger.info("Customer Object is filled with values");
	 	// doppelteKdnr = dataBaseCon.doppelteKundennr(objDaten, kdverw_Obj.getKdNr(), kdverw_Obj.getAktiveDB());
	 	// Failed doubled Customer has to be checked          
	           	
	       	if(!Obj_Cust_Gui.getChkHibernateValue()) {
	    		//if(!doppelteKdnr) {
	       		logger.debug("Save to JDBC Local Database : " + Obj_Cust_Gui.getChkHibernateValue());
	    		dataBase_Result = dataBaseRequest.new_Customer(obj_Customer, Obj_Cust_Gui.getActiveDB(),logger);
	    		logger.info("Object is saved to selected Database");
	    		Obj_Cust_Gui.setBtnCustCancel(false);
	    		Obj_Cust_Gui.setBtnCustSave(false);
	    		gui_State.gui_State_Start(Obj_Cust_Gui);
	    	//} else {
	        // 	JOptionPane.showMessageDialog(null, "Datensatz konnte nicht gespeichert werden !!!\n"
	        //  + "Kundennummer bereits vorhanden !!!", "Fehler beim anlegen des Kunden", JOptionPane.CANCEL_OPTION);
	        //}
	                              	            		
	        } else {
	            		
	            statementsHibernate.writeCustomer(obj_Customer,logger);  // added for Hibernate Test
	            
	        }
	     }
	});
	        // Cancel - Not Saving Customer Data  
		    Obj_Cust_Gui.btn_Cust_NoSave.setOnAction(new EventHandler<ActionEvent>() {   
	            @Override
	            public void handle(ActionEvent e) {
	            	   // Reset GUI State
	                   gui_State.gui_State_Start(Obj_Cust_Gui);
	                   logger.info("GUI State is resetet");
	            }
	        });


	        // Export as PDF File
		    Obj_Cust_Gui.btn_Plan_Pdf.setOnAction(new EventHandler<ActionEvent>() {
	            
	            @Override
	            public void handle(ActionEvent e) {
	                // calculations for PDF Export
	            	//orderlist = calculate.getOrder_Objects(Obj_Cust_Gui, obj_Order,dataBaseRequest,logger);
	            				calculate.setLstMonth(lst_month);
	                logger.info("Calculations Done for PDF Export");               
	            	// Create PDF Export
	                PDF_Builder create_Pdf = new PDF_Builder();							
	            	     		create_Pdf.setCustomer(obj_Customer);
	            		    	create_Pdf.setListMonthlyRate(lst_monthly_Rate);
	            			    create_Pdf.setListMonths(lst_month);
	            			    create_Pdf.create_PDF_Export(logger);
	            	logger.info("PDF Export Done");
	            }
	        });
	       /* 
	       // Delete Order  
	       Obj_Cust_Gui.btn_Order_Del.setOnAction(new EventHandler<ActionEvent>() {
	             @Override
	             public void handle(ActionEvent e) {
	                 // Message - Really Delete the Order ?
	                 Alert customer_Confirmation = new Alert(AlertType.CONFIRMATION);
	                 	   customer_Confirmation.setTitle("Bestellung loeschen ?");
	                 	   customer_Confirmation.setHeaderText("Bestellung wirklich loeschen ?");
	                 	   customer_Confirmation.setContentText("Soll die Bestellung Nr.: " + Obj_Cust_Gui.getOrderNr() + "\n wirklich geloescht werden?" );
	                       Optional<ButtonType> result = customer_Confirmation.showAndWait();
	                       // Delete Order if OK
	                       if(result.get() == ButtonType.OK) {															
	                           
	                           dataBase_Result = dataBase_Request.delete_Order(Obj_Cust_Gui.getActiveDB(), Obj_Cust_Gui.getCustNr(), Obj_Cust_Gui.getOrderNr(),logger);
	                           gui_State.cancel_Order(Obj_Cust_Gui);
	                           logger.info("Order deleted - GUI was reset");
	                       } else {
	                           // MSG - Class Impl
	                       }
	            }
	        });
	       */
		   /*
	       // Change Order  
	       Obj_Cust_Gui.btn_Order_Change.setOnAction(new EventHandler<ActionEvent>() {
	             @Override
	             public void handle(ActionEvent e) {
	            	// GUI State => Change Order 
	                gui_State.change_Order(Obj_Cust_Gui); 
	                // Set Flag for Order Update
	                Obj_Cust_Gui.setOrderFlag(1);         												   
	                logger.info("Order Change - Flag for Update Order is set");
	            }
	        });
	        */
	       //  View All Customer as Table
	       Obj_Cust_Gui.m_Cust_View.setOnAction(new EventHandler<ActionEvent>() {
	             @Override
	             public void handle(ActionEvent e) {
	            	// Database Request returns all Customernumbers in Database 
	            	lst_All_Customers = dataBaseRequest.getAll_Cust_Nr(Obj_Cust_Gui.getActiveDB(),logger);
	            	logger.info("All Customernumbers returned");
	            	// Returns customer object added to object List         
	                for(int current_Cust_Nr = 0; current_Cust_Nr < lst_All_Customers.size();current_Cust_Nr++) {
	                	lst_Obj_Customer.add(dataBaseRequest.getCustomer(new Obj_Customer(),lst_All_Customers.get(current_Cust_Nr),Obj_Cust_Gui.getActiveDB(),logger)); 
	                }
	                logger.info("All Customer Objects added to Object list");
	                All_Customers_View customers_View = new All_Customers_View(); 			// create  Object for Customer View
	                				   customers_View.setLstCustomer(lst_Obj_Customer);     // Set List with all Customer Objects
	                             	   customers_View.create_All_Customers_Table();         // create View
	                logger.info("Customer Table view created");          
	            }
	        });
	        // System exit
	       Obj_Cust_Gui.m_Exit.setOnAction(new EventHandler<ActionEvent>() {
	             @Override
	             public void handle(ActionEvent e) {
	                    System.exit(0);
	            }
	        });
	        // Excel Export   
	        Obj_Cust_Gui.btn_Plan_Excel.setOnAction(new EventHandler<ActionEvent>() {
	             @Override
	             public void handle(ActionEvent e) {
	                 try {
	                	// using save Dialog for Filename => getFileName
	                    String fileName = getFileName("Excel Export",".xls",primaryStage);
	                   	// create Excel Export                    
	                    excel_Export = new Excel_Export(); 
	                    excel_Export.setObj_Customer(obj_Customer);
	                    excel_Export.setLstMonthlyRate(lst_monthly_Rate);
	                    excel_Export.setLst_Month(lst_month);
	                    excel_Export.setOutputFile(fileName);
	                    excel_Export.write_Excel_Export();
	                    logger.info("Excel Export is created");
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
	        // Save Dialog   
	        File fileName = select_Database.showSaveDialog(primaryStage);  			
	            if(fileName != null) {
	                str_fileName = fileName.getAbsolutePath() + fileExt;
	                	             
	            } else {
	                str_fileName = "";
	            }
	    // returns filename and FilePath        
	    return str_fileName; 														
	}   
	
	private void fillOrderNumberList(ObservableList<Obj_Order> listOrderObjects, Cust_Gui mainGui, Logger logger) {
				
		for(Obj_Order orderObject: listOrderObjects) {
			mainGui.getOrderListItems().add(orderObject.getOrderNo());
		}
		
		logger.debug("Current Orderlistitems " + mainGui.getOrderListItems().size());
		mainGui.setOrderCount(mainGui.getOrderListItems().size());
	
	}

	 public void setLstMonth(ArrayList<String> lst_month ) { this.lst_month = lst_month; }
	

}
	 
	 
	 
	 
	 
	 
	 
	

