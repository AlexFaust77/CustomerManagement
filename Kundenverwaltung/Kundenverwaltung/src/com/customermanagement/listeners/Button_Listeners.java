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
import com.customermanagement.helpers.GuiState;
import com.customermanagement.helpers.Save_Database_Information;
import com.customermanagement.inputchecks.InputChecks;
import com.customermanagement.main.Cust_Gui;
import com.customermanagement.main.MainGuiController;
import com.customermanagement.main.OrderGuiController;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jxl.write.WriteException;

public class Button_Listeners {

	 private boolean dataBase_Result;										     // DB Contact Result
	
	 Obj_Customer obj_Customer =new Obj_Customer();						         // Object Customer
	 Obj_Order objOrder = new Obj_Order("",null,null,null,0,0.0,0.0,0.0,"");     // Object Order
	 Clear_Data data_cleaner = new Clear_Data();							     // for clearing all Lists
	
	 FileChooser select_Database = new FileChooser();						     // Filechooser for Database
	 Excel_Export excel_Export;				     								 // Excel Export class
	 InputChecks checkInput = new InputChecks();
	 																			 // FXML Parameter
	 Stage orderFXMLStage = new Stage();										 // FXML Stage or Orders
	 
	 int orderFlag = 0;															 // FXML OrderFlag Update = 1 - New Order = 0
	 
	 private HibernateStatements statementsHibernate = new HibernateStatements();// for Hibernate Database Access
	 
	 private ArrayList<String> lst_month = new ArrayList<String>();			     // list for Months
	 private ArrayList<String> lst_monthly_Rate = new ArrayList<String>();       // list all monthly Rates
	 
	 
	 private ArrayList<Obj_Order> orderlist = new ArrayList<Obj_Order>();        // list for all Orders No - will be removed use Objectlist instead
	 private ObservableList<Obj_Order>lstAllOrders = FXCollections.observableArrayList();  // NEUE LISTE änderung der GUI
	 
	 private ArrayList<String>lst_All_Customers = new ArrayList<String>();       // list of Customer No
	 private ObservableList<Obj_Customer>lst_Obj_Customer = FXCollections.observableArrayList(); // list for all Customer Objects
	 
	 // Stage primaryStage will be removed  look line 222 if its neccesary
	 public Button_Listeners(GuiState gui_State,Cust_Gui Obj_Cust_Gui,Logger logger, 
			 				 Stage primaryStage, Calculator calculate, SQL_Statements dataBaseRequest, 
			 				 TableView<Obj_Order> fx_Table_View, OrderGuiController orderGuiControl) {
	
		 // Search one Customer with Customernumber
		 Obj_Cust_Gui.btn_Cust_Search.setOnAction(new EventHandler<ActionEvent>() {                                     								  
	            @Override
	            public void handle(ActionEvent e) {
	            	  obj_Customer = data_cleaner.cleanObjCustomer(obj_Customer);
	                  				 //data_cleaner.cleanLists(lst_month,orderlist,lst_Obj_Customer,lst_All_Customers,lst_monthly_Rate);
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
	                            
                      //orderlist = calculate.getOrder_Objects(Obj_Cust_Gui, obj_Order,dataBase_Request,logger);
	                  			    calculate.setLstMonth(lst_month);
	                  			    calculate.getAll_Rates(logger,lstAllOrders); // <= hier läuft was schief -- Hier weiter
	                            
	                  if(lstAllOrders.size() > 0 ) {
	                	  	   obj_Customer.setLstAllOrders(lstAllOrders);	 // Bestellobjekte werden Kunden zugewiesen
	                	  	   System.out.println("Anzahl Bestellobjekte Customer : " + obj_Customer.getLstAllOrders().size() + "\n");
	                  
	                	  	   lst_monthly_Rate = calculate.getMonthlyRate();
	                	  	   logger.info("All Rates calculated - Done");
	                  
	                	  	   // create chart for monthly Rates
	                	  	   Chart_fx rate_Chart = new Chart_fx();																									
	                	  	   		    rate_Chart.setMonthlyRate(lst_monthly_Rate);
	                	  	   		    rate_Chart.setMonthslist(lst_month);
	                	  	   		  //  rate_Chart.createChartWithToolTips();
	                	  	   		    Obj_Cust_Gui.setLineData(rate_Chart.getChartData());
	                  
	                	  	   logger.info("ratechart created - Done"); 
	                
	                	  	   //Table for Order Overview - getestet funktioniert
	                	  	                	  	   
	                	  	   TableView<Obj_Order> view_fx_Table = new TableView<Obj_Order>();
	                	  	                        view_fx_Table.setId("CurrentOrders");
	                	  	   Table_fx table = new Table_fx();
	                	  	   		//	table.setOrderlist(lstAllOrders);
	                	  	   			//view_fx_Table = table.createTable();
	                	  	   			
	                	  	   			Obj_Cust_Gui.tabCurrentOrders.setContent(view_fx_Table);
	                	  	   			
	                	  	   logger.info("order Table created - Done");
	                  } else {
	                	  
	                  }
	            }
	        });
	
			     // Delete all Orders from one Customer - and Delete the Selected Customer
		 Obj_Cust_Gui.btn_Cust_Del.setOnAction(new EventHandler<ActionEvent>() {
                @Override
	            public void handle(ActionEvent e) {
	                
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
	    	// ---	gui_State.gui_State_Start(Obj_Cust_Gui);
	    	//} else {
	        // 	JOptionPane.showMessageDialog(null, "Datensatz konnte nicht gespeichert werden !!!\n"
	        //  + "Kundennummer bereits vorhanden !!!", "Fehler beim anlegen des Kunden", JOptionPane.CANCEL_OPTION);
	        //}
	                              	            		
	        } else {
	            		
	            statementsHibernate.writeCustomer(obj_Customer,logger);  // added for Hibernate Test
	            
	        }
	     }
	});
	    
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
	      // Obj_Cust_Gui.m_Exit.setOnAction(new EventHandler<ActionEvent>() {
	      //       @Override
	      //       public void handle(ActionEvent e) {
	      //              System.exit(0);
	      //      }
	      //  });

	        
	        
	   	 ////
	        
	   	 Obj_Cust_Gui.orderList.setOnMouseClicked(new EventHandler<MouseEvent>(){
	   			
	   		    @Override
	   			public void handle(MouseEvent click) {
	   				if(click.getClickCount() == 2) {
	   					String currentOrderNo = Obj_Cust_Gui.orderList.getSelectionModel().getSelectedItem();
	   					orderFlag = 1;
	   					
	   					objOrder = dataBaseRequest.getOneOrderData(objOrder, currentOrderNo, Obj_Cust_Gui.getCustNr(), Obj_Cust_Gui.getActiveDB(), logger);
	   					logger.debug("com.customermanagement.listeners.OrderListeners - orderList" + objOrder.getOrderNo());
	   					
	   					orderFlag = 1;
		              	//orderGuiControl.setMainGui(Obj_Cust_Gui);
					    orderGuiControl.setMainStage(primaryStage);
					    orderGuiControl.setOrderFlag(orderFlag);
					    orderGuiControl.startFXMLOrderGui(orderFXMLStage,objOrder);
		               
		                //orderGuiControl.setOrderCustNo(Obj_Cust_Gui.getCustNr());
	   					
	   					
	   					
	   					//createNewOrderGui(orderGui, mainGui, guiState, logger,objOrder);
	   					// Nicht vollständig
	   					System.out.println("Selected Orderno. " + currentOrderNo);
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
	 
	 
	 
	 
	 
	 
	 
	

