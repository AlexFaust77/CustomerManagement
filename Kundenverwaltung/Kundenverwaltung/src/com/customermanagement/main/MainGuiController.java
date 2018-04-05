package com.customermanagement.main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Date;
import org.apache.log4j.Logger;

import com.customermanagement.database.SQL_Statements;
import com.customermanagement.entities.Obj_Customer;
import com.customermanagement.entities.Obj_Order;
import com.customermanagement.helpers.Calculator;
import com.customermanagement.helpers.Clear_Data;
import com.customermanagement.helpers.GuiState;
import com.customermanagement.helpers.Logger_Init;
import com.customermanagement.helpers.Save_Database_Information;
import com.customermanagement.listeners.Button_Listeners;
import com.customermanagement.reports.Excel_Export;
import com.customermanagement.reports.PDF_Builder;
import com.customermanagement.statistics.Chart_fx;
import com.customermanagement.statistics.Table_fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jxl.write.WriteException;

public class MainGuiController {

	String loggerInfo ="com.customermanagement.main -> MainGuiController.java";

	private static final Logger logger = Logger_Init.getInstance();	
	boolean dBResult = false;
	int orderFlag = 0;															        // FXML OrderFlag Update = 1 - New Order = 0
	
	GuiState guiState = new GuiState();
    Calculator calculator = new Calculator();
    Clear_Data cleaner = new Clear_Data();
    OrderGuiController orderGuiControl = new OrderGuiController();      	            // FXML Controller for Order Form
    Stage orderGuiStage = new Stage();										            // FXML Stage for Orders
    Stage mainGuiStage;              													// FXML Stage for Customerdata
    
    SQL_Statements dataBaseRequest = new SQL_Statements();	     		                // all SQL - Lite statements
	Obj_Customer customerData =new Obj_Customer();						                // Object Customer
	Obj_Order orderData = new Obj_Order("",null,null,null,0,0.0,0.0,0.0,"");            // Object Order
	FileChooser fileChooserDB = new FileChooser();						                // Filechooser for Database
	
	
	private ArrayList<String> month = new ArrayList<String>();			                // list for Months
	private ArrayList<String> rates = new ArrayList<String>(); 							// list with all Rates
	private ObservableList<Obj_Order> allOrders = FXCollections.observableArrayList();  // list of all Order-Objects
	
	
    @FXML
	public Button btnCreateOrder;

    @FXML
    private TextField txtCustomerOutstanding;

    @FXML
    private Label lblCustomerNo;

    @FXML
    private ListView<String> lstCustomerOrders;
    
    @FXML
    private TextField txtCustomerHouseNo;

    @FXML
    private TextField txtCustomerID;

    @FXML
    private Label lblCustomerTown;

    @FXML
    private TextField txtCustomerOrderAmount;

    @FXML
    private TableView<Obj_Order> orderTable;

    @FXML
	public Button btnDeleteCustomer;

    @FXML
    private Label lblCustomerStreet;

    @FXML
    private Label lblCurrentDatabase;

    @FXML
    private TextField txtCustomerNo;

    @FXML
    private TextField txtCustomerTown;

    @FXML
	public Button btnCustomerSearch;

    @FXML
    private Label lblCustomerID;

    @FXML
	public Button btnExportToExel;

    @FXML
    private MenuBar mainMenuBar;

    @FXML
    private TextField txtCustomerStreet;

    @FXML
    private TableColumn<Obj_Order, String> colOrderNo;

    @FXML
    private TableColumn<Obj_Order, Date> colOrderDate;
    
    @FXML
    private TableColumn<Obj_Order, String> colOrderSummary;
    
    @FXML
    private TableColumn<Obj_Order, Integer> colRateCount;
           
    @FXML
    private TableColumn<Obj_Order, Date> colPayStart;
    
    @FXML
    private TableColumn<Obj_Order, Date> colPayEnd;
    
    @FXML
    private TableColumn<Obj_Order, Double> colFirstRate;
    
    @FXML
    private TableColumn<Obj_Order, String> colRate;
    
    @FXML
    private TableColumn<Obj_Order, Double> colStillToPay;
    
    @FXML
    private TableColumn<Obj_Order, Double> colAlreadyPaid;
    
    @FXML
    private TextField txtCurrentDatabase;

    @FXML
    private Label lblCustomerPlc;

    @FXML
    private Label lblCustomerSummary;

    @FXML
    private Label lblOrderList;

    @FXML
	public Button btnPrintPaymentPlan;

    @FXML
	public Button btnNewDatabase;
    
    @FXML
	public Button btnSaveCustomer;
    
    @FXML
	public Button btnSelectDatabase;

    @FXML
    private Label lblCustomerOutstanding;

    @FXML
    private TextField txtCustomerPlc;

    @FXML
    private Label lblCustomerHouseNo;

    @FXML
	public Button btnNewCustomer;
    
    @FXML
	public Button btnCancelNewCustomer;

    @FXML
	public Button btnExportToPDF;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private Label lblCustomerName;

    @FXML
    private TextField txtCustomerSummary;

    //@FXML
    //private LineChart<?, ?> orderLineChart;
    
    @FXML
    private LineChart<String, Number> orderLineChart;

    @FXML
    private Label lblCustomerOrderAmount;

    @FXML
    private Label lblCustomerSurname;

    @FXML
    private MenuItem menuItemClose;

    @FXML
    private TextField txtCustomerSurname;
    
    // Visibility of Buttons
    
	// Setter
	public void setTxtCustomerOutstanding(String txtCustomerOutstanding)    { this.txtCustomerOutstanding.setText(txtCustomerOutstanding);	}
	public void setTxtCustomerHouseNo(String customerHouseNo)               { this.txtCustomerHouseNo.setText(customerHouseNo);     		}
	public void setTxtCustomerOrderAmount(int customerOrderAmount)          { this.txtCustomerOrderAmount.setText(""+ customerOrderAmount);	}
	public void setTxtCustomerNo(String txtCustomerNo)                      { this.txtCustomerNo.setText(txtCustomerNo);	        		}
	public void setTxtCustomerTown(String customerTown)                     { this.txtCustomerTown.setText(customerTown);           		}
    public void setTxtCustomerStreet(String customerStreet)                 { this.txtCustomerStreet.setText(customerStreet);       		}
	public void setTxtCurrentDatabase(String filePathDB)                    { this.txtCurrentDatabase.setText(filePathDB);          		}
	public void setTxtCustomerPlc(String customerPlc)                       { this.txtCustomerPlc.setText(customerPlc);             		}
	public void setTxtCustomerName(String customerName)                     { this.txtCustomerName.setText(customerName);           		}
	public void setTxtCustomerSummary(String customerOrderSummary)          { this.txtCustomerSummary.setText(customerOrderSummary);		}
	public void setTxtCustomerSurname(String customerSurname)               { this.txtCustomerSurname.setText(customerSurname);     		}
    public void setTxtCustomerID(String customerID)                         { this.txtCustomerID.setText(customerID);               		}
    
    public void setMainStage(Stage mainGuiStage)  { this.mainGuiStage = mainGuiStage;    } 
    
    // Getter
    public TextField getTxtCustomerOutstanding()  { return txtCustomerOutstanding;       }
	public TextField getTxtCustomerHouseNo()      { return txtCustomerHouseNo;           }
	public TextField getTxtCustomerID()           { return txtCustomerID;                }
	public TextField getTxtCustomerOrderAmount()  { return txtCustomerOrderAmount;       }
	public String getTxtCustomerNo()              { return txtCustomerNo.getText();      }
	public TextField getTxtCustomerTown()         { return txtCustomerTown;	             }
	public TextField getTxtCustomerStreet()       { return txtCustomerStreet;	         }
	public String getTxtCurrentDatabase()         { return txtCurrentDatabase.getText(); }
	public TextField getTxtCustomerPlc()          { return txtCustomerPlc;               }
	public TextField getTxtCustomerName()         { return txtCustomerName;	             }
	public TextField getTxtCustomerSummary()      { return txtCustomerSummary;           }
	public TextField getTxtCustomerSurname()      { return txtCustomerSurname;           }
    
	public TableColumn<Obj_Order,String> getColOrderSummary()  { return colOrderSummary; }
	public TableColumn<Obj_Order,String> getColRate()          { return colRate;         }
	public TableColumn<Obj_Order,String> getColOrderNo()       { return colOrderNo;      }
	public TableColumn<Obj_Order,Date> getColOrderDate()       { return colOrderDate;    }
	public TableColumn<Obj_Order,Integer> getColRateCount()    { return colRateCount;    }
	public TableColumn<Obj_Order,Date> getColPayStart()        { return colPayStart;     }
	public TableColumn<Obj_Order,Date> getColPayEnd()          { return colPayEnd;       }
	public TableColumn<Obj_Order,Double> getColFirstRate()     { return colFirstRate;    }
	public TableColumn<Obj_Order,Double> getColStillToPay()    { return colStillToPay;   }
	public TableColumn<Obj_Order,Double> getColAlreadyPaid()   { return colAlreadyPaid;  }
	
	public Stage getMainStage()  							   { return mainGuiStage;    } 
	
	// Customer Field Design and activation
  	public void setEditCustNr(boolean on_off,String color)      { this.txtCustomerNo.setEditable(on_off); 				
	                                                              this.txtCustomerNo.setStyle("-fx-control-inner-background: " + color + ";"); }
    public void setEditCustName(boolean on_off,String color)    { this.txtCustomerName.setEditable(on_off);
		                                                          this.txtCustomerName.setStyle("-fx-control-inner-background: " + color + ";");}
    public void setEditCustSurname(boolean on_off,String color) { this.txtCustomerSurname.setEditable(on_off);
		                                                          this.txtCustomerSurname.setStyle("-fx-control-inner-background: " + color + ";");}
    public void setEditCustStreet(boolean on_off,String color)  { this.txtCustomerStreet.setEditable(on_off); 
	                                                        	  this.txtCustomerStreet.setStyle("-fx-control-inner-background: " + color + ";");}
    public void setEditCustHouseNo(boolean on_off,String color) { this.txtCustomerHouseNo.setEditable(on_off); 
                                                                  this.txtCustomerHouseNo.setStyle("-fx-control-inner-background: " + color + ";");}
    public void setEditCustPlc(boolean on_off, String color)    { this.txtCustomerPlc.setEditable(on_off);
	                                                              this.txtCustomerPlc.setStyle("-fx-control-inner-background: " + color + ";");}
    public void setEditCustTown(boolean on_off,String color)    { this.txtCustomerTown.setEditable(on_off);
	                                                         	  this.txtCustomerTown.setStyle("-fx-control-inner-background: " + color + ";");}
  
  
	
	
	
	@FXML
    void searchAction(ActionEvent event) {
		
		///// => Plz in char ändern nicht int belassen  -  Int geht nicht ende bei 65...
		cleaner.cleanObjCustomer(customerData);
		cleaner.cleanLists(month, rates, allOrders);

		// Database request				
		customerData = dataBaseRequest.getCustomer(customerData,this.getTxtCustomerNo(),this.getTxtCurrentDatabase(),logger);  	
        logger.info(loggerInfo + " dataBaseRequest - Success");
        
        // Filling the values into the Gui Fields
        this.setTxtCustomerName(customerData.getLastname());                															    
        this.setTxtCustomerSurname(customerData.getFirstname());
        this.setTxtCustomerStreet(customerData.getStreet());
        this.setTxtCustomerHouseNo(Integer.toString(customerData.getHouseNo()));
        this.setTxtCustomerPlc(Integer.toString(customerData.getPostcode()));
        this.setTxtCustomerTown(customerData.getResidenz());
        this.setTxtCustomerID(Integer.toString(customerData.getId()));
        // Obj_Cust_Gui.setBestellNummernListe(obj_Customer.getOrderlist());
        this.setTxtCustomerOrderAmount(customerData.getOrderlist().size());
        this.setTxtCustomerSummary(Double.toString(customerData.getCustTotal()));
     
		calculator.fillMonth(logger);
		month = calculator.getMonth();
		logger.info(loggerInfo + "List with Month prepared - Success");

		allOrders = dataBaseRequest.getAllOrderObjects(customerData,this.getTxtCustomerNo(),this.getTxtCurrentDatabase(),logger); 

		fillOrderNumberList(allOrders,this,logger);

		  //System.out.println("Grösse der Order Liste " + lstAllOrders.size());
	
		 /// ________ /////
		  //hier der weiter damit ich weis wo ich war

		  //orderlist = calculate.getOrder_Objects(Obj_Cust_Gui, obj_Order,dataBase_Request,logger);
		  calculator.setMonth(month);
		  calculator.getAllRates(logger,allOrders); // <= hier läuft was schief -- Hier weiter


		  //System.out.println("Vorhandene Bestellungen : " + lstAllOrders.size()+ "\n");

		  if(allOrders.size() > 0 ) {
			 customerData.setLstAllOrders(allOrders);	  // Orders assigned to Customerobject
			 // System.out.println("Anzahl Bestellobjekte Customer : " + customerData.getLstAllOrders().size() + "\n");

			  rates = calculator.getMonthlyRate();
			  logger.info("All Rates calculated - Done");

			  for(int i = 0; i < rates.size(); i++) {
			  	  System.out.println("Liste der Monatesraten Rate => " + (i + 1) + " Aktueller Wert : " + rates.get(i)  +"\n" );
			  	  System.out.println("Liste all Orders Grösse > " + allOrders.size() + "\n");
			  }

			  // create chart for monthly Rates
			  Chart_fx rate_Chart = new Chart_fx();																									
	   		  rate_Chart.setMonthlyRate(rates);
	   		  rate_Chart.setMonthslist(month);
	   		  rate_Chart.createChartWithToolTips(orderLineChart);
	   		  //orderLineChart.setData(rate_Chart.getChartData());

	   		  logger.info(loggerInfo + "Chart calculated"); 
	   		  
	   		  
	   		  Table_fx fxTable = new Table_fx();
	   		           fxTable.fillTheTable(orderTable,allOrders,this,logger);

	   		  logger.info(loggerInfo + " Tableview filled - Success");
			  
		  } else {
        	  
          }
    }

    @FXML
    void createOrder(ActionEvent event) {
    	orderFlag = 0;
      	orderGuiControl.startFXMLOrderGui(orderGuiStage,orderData);
      	orderGuiControl.setMainGui(this);
      	orderGuiControl.setMainStage(getMainStage());
	    
      	orderGuiControl.setOrderFlag(orderFlag);
	    
        guiState.newFXMLOrder(orderGuiControl);
        orderGuiControl.setOrderCustNo(this.getTxtCustomerNo());
       
    }

    @FXML
    void deleteCustomer(ActionEvent event) {
    	Alert customer_Confirmation = new Alert(AlertType.CONFIRMATION);
  	          customer_Confirmation.setTitle("Kunde wirklich loeschen ?");
  	          customer_Confirmation.setHeaderText("Kunde wirklich loeschen ?\nEs werden alle Datensaetze zu diesem Kunden geloescht !");
  	          customer_Confirmation.setContentText("Soll der Kunde-Nr.: " + getTxtCustomerNo() + "\n wirklich geloescht werden?" );
        
  	          Optional<ButtonType> result = customer_Confirmation.showAndWait(); 
        
  	          if(result.get() == ButtonType.OK) {
  	        	  	logger.info(loggerInfo + "deleteCustomer - DeleteOrders started");
  	        	  		  // First Delete all Orders from this Customer
  	        	  	      for(int orderCounter = 0; orderCounter < this.lstCustomerOrders.getItems().size();orderCounter++) {
  	        	  	    	  logger.debug(loggerInfo + "Try to delete Order " + allOrders.get(orderCounter).getOrderNo() + "\n");
  	        	  	          dBResult = dataBaseRequest.deleteOrder(getTxtCurrentDatabase(), getTxtCustomerNo(), allOrders.get(orderCounter).getOrderNo(),logger);
  	        	  	          logger.debug(loggerInfo + "Delete Order " + dBResult + "\n");
  	        	  	     }
                    logger.info(loggerInfo + "Orders Deleted " + dBResult + "\n");
             
             dBResult = dataBaseRequest.delete_Customer(this.getTxtCurrentDatabase(), this.getTxtCustomerNo(),logger);				
             logger.info(loggerInfo + "Customer Deleted " + dBResult + "\n");
            
             /// ----- gui_State.gui_State_Start(Obj_Cust_Gui);
             
             cleaner.cleanMainGui(this);
             cleaner.cleanLists(month, rates, allOrders);
             cleaner.cleanObjCustomer(customerData);
             
             logger.debug(loggerInfo + "Data clean - Success");
         } else {
             // MSG - Customer not found
         }
    }

    @FXML
    void createDataBase(ActionEvent event) {
    	 fileChooserDB.setTitle("Neue Datenbank erstellen !");
    	
         File dataBase_Name = fileChooserDB.showSaveDialog(mainGuiStage);
             if(dataBase_Name != null) {
                                	 				   
            	 dataBaseRequest.build_Database(dataBase_Name.getName() + ".db",logger);
            	 this.setTxtCurrentDatabase(dataBase_Name.getAbsolutePath() + ".db");
                 logger.info("Database builded - Information Field set");
                 
             } else {
                 // Nichts tun
             }
    }
    
    @FXML
    void SelectDataBase(ActionEvent event) {
    	 fileChooserDB.setTitle("Bitte Datenbank auswaehlen !");
        
        File selected_database = fileChooserDB.showOpenDialog(mainGuiStage);					
             if(selected_database != null) {
                         
             	        // connect to selected Database
            	        dBResult = dataBaseRequest.DatabaseConnection(selected_database.getAbsolutePath(),logger);
                         
                         if(dBResult) {  // Connection OK
                         	this.setTxtCurrentDatabase(selected_database.getAbsolutePath());
                         	Save_Database_Information last_used_database = new Save_Database_Information();
                         							  last_used_database.save_Database_Info(selected_database.getAbsolutePath(), logger);
                         	//Obj_Cust_Gui.setGoodResult();
                         	logger.info("DB selection - Status : OK" + selected_database.getAbsolutePath());
                         }  else {             // connection failed
                         	this.setTxtCurrentDatabase("Verbindungsaufbau fehlgeschlagen");
                         	logger.error("DB selection - Arrrrgg : something wrong with your File" + selected_database.getAbsolutePath());
                         	//Obj_Cust_Gui.setBadResult();
                         }       
             }
    }

    @FXML
    void createNewCustomer(ActionEvent event) {
    	
    	guiState.stateCreateNewCustomer(this,logger);
    	logger.info("Waiting for Input now");
    }

    @FXML
    void displayCustomerOverview(ActionEvent event) {

    }

    @FXML
    void aboutApplication(ActionEvent event) {

    }

    @FXML
    void closeApplication(ActionEvent event) {

    }

    @FXML
    void printPaymentPlan(ActionEvent event) {

    }
    
    @FXML
    void saveCustomer(ActionEvent event) {

    }
    
    @FXML
    void cancelNewCustomer(ActionEvent event) {
    	 guiState.resetCreateNewCustomer(this,logger);
    }

    @FXML
    void exportAsPDF(ActionEvent event) {
        // calculations for PDF Export
    	//orderlist = calculate.getOrder_Objects(Obj_Cust_Gui, obj_Order,dataBaseRequest,logger);
    	calculator.setLstMonth(month);
        logger.info("Calculations Done for PDF Export");               
    	// Create PDF Export
        PDF_Builder create_Pdf = new PDF_Builder();							
    	     		create_Pdf.setCustomer(customerData);
    		    	create_Pdf.setListMonthlyRate(rates);
    			    create_Pdf.setListMonths(month);
    			    create_Pdf.create_PDF_Export(logger);
    	logger.info("PDF Export Done");
    }

    @FXML
    void exportAsExcel(ActionEvent event) {
    	try {
        	// using save Dialog for Filename => getFileName
            String fileName = getFileName("Excel Export",".xls");
           	// create Excel Export
            Excel_Export excelExport = new Excel_Export(); 
            excelExport.setObj_Customer(customerData);
            excelExport.setLstMonthlyRate(rates);
            excelExport.setLst_Month(month);
            excelExport.setOutputFile(fileName);
            excelExport.write_Excel_Export();
            logger.info("Excel Export is created");
         } catch (IOException | WriteException ex) {
        	logger.error("Excel Export : " + ex.getLocalizedMessage());
         }
    }
    
   private String getFileName(String title, String fileExt) {

	    String str_fileName ="";         										// Placeholder filename
        fileChooserDB.setTitle(title);  
        // Save Dialog   
        File fileName = fileChooserDB.showSaveDialog(mainGuiStage);  			
            if(fileName != null) {
                str_fileName = fileName.getAbsolutePath() + fileExt;
                	             
            } else {
                str_fileName = "";
            }
    // returns filename and FilePath        
    return str_fileName; 														
}   
    
    
    
    

	private void fillOrderNumberList(ObservableList<Obj_Order> listOrderObjects, MainGuiController mainGuiController, Logger logger) {
		
		ObservableList<String>orderListItems = FXCollections.observableArrayList();
				
		for(Obj_Order orderObject: listOrderObjects) {
			orderListItems.add(orderObject.getOrderNo());
		}
				
		logger.debug(loggerInfo + "Order list filled -> Success " + orderListItems.size());
		mainGuiController.setTxtCustomerOrderAmount(orderListItems.size());
		lstCustomerOrders.setItems((ObservableList<String>) orderListItems);
	
	}
    
 public void startFXMLMainGui(Stage primaryStage) {
    	
    	try {
    	
    		URL fileLocation = getClass().getResource("MainGui.fxml");
    	    FXMLLoader fxmlLoader = new FXMLLoader();
    	    fxmlLoader.setController(this);
    	
    	    Parent mainGuiFXML = fxmlLoader.load(fileLocation.openStream());
			
			Scene scene = new Scene(mainGuiFXML,1600, 900);  
					    		    
			primaryStage.setTitle("Kundendatenbank");
		    	    
			primaryStage.setScene(scene);
		    
		    guiState.guiStateStart(this);
		    
		    Save_Database_Information database_Exists = new Save_Database_Information();
			                          database_Exists.check_Database_File(guiState,this,logger);
		    
		    mainGuiStage = primaryStage;
		    
		    mainGuiStage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      	
    }
    
    
    

}

