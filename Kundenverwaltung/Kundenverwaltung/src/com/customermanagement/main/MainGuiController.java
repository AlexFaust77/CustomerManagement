package com.customermanagement.main;

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.customermanagement.database.SQL_Statements;
import com.customermanagement.entities.Obj_Order;
import com.customermanagement.helpers.Logger_Init;
import com.customermanagement.listeners.Button_Listeners;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainGuiController {

	//  Button_Listeners btn_Listener = new Button_Listeners(gui_States,calculate,fx_Table_View,orderGuiControl);
	private static final Logger logger = Logger_Init.getInstance();	
	
	SQL_Statements dataBaseRequest = new SQL_Statements();	     		       // all SQL - Lite statements
	Obj_Order objOrder = new Obj_Order("",null,null,null,0,0.0,0.0,0.0,"");    // Object Order
	
	
    @FXML
    private Button btnCreateOrder;

    @FXML
    private TextField txtCustomerOutstanding;

    @FXML
    private Label lblCustomerNo;

    @FXML
    private ListView<?> lstCustomerOrders;

    @FXML
    private TextField txtCustomerHouseNo;

    @FXML
    private TextField txtCustomerID;

    @FXML
    private Label lblCustomerTown;

    @FXML
    private TextField txtCustomerOrderAmount;

    @FXML
    private TableView<?> orderTable;

    @FXML
    private Button btnDeleteCustomer;

    @FXML
    private Label lblCustomerStreet;

    @FXML
    private Label lblCurrentDatabase;

    @FXML
    private TextField txtCustomerNo;

    @FXML
    private TextField txtCustomerTown;

    @FXML
    private Button btnCustomerSearch;

    @FXML
    private Label lblCustomerID;

    @FXML
    private Button btnExportToExel;

    @FXML
    private MenuBar mainMenuBar;

    @FXML
    private TextField txtCustomerStreet;

    @FXML
    private TableColumn<?, ?> orderNo;

    @FXML
    private TextField txtCurrentDatabase;

    @FXML
    private Label lblCustomerPlc;

    @FXML
    private Label lblCustomerSummary;

    @FXML
    private Label lblOrderList;

    @FXML
    private Button btnPrintPaymentPlan;

    @FXML
    private Button btnNewDatabase;

    @FXML
    private Label lblCustomerOutstanding;

    @FXML
    private TextField txtCustomerPlc;

    @FXML
    private Label lblCustomerHouseNo;

    @FXML
    private Button btnNewCustomer;

    @FXML
    private Button btnExportToPDF;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private Label lblCustomerName;

    @FXML
    private TextField txtCustomerSummary;

    @FXML
    private LineChart<?, ?> orderLineChart;

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
	public void setTxtCustomerOutstanding(TextField txtCustomerOutstanding) { this.txtCustomerOutstanding = txtCustomerOutstanding;	}
	public void setTxtCustomerHouseNo(TextField txtCustomerHouseNo) { this.txtCustomerHouseNo = txtCustomerHouseNo;	}
	public void setTxtCustomerOrderAmount(TextField txtCustomerOrderAmount) { this.txtCustomerOrderAmount = txtCustomerOrderAmount;	}
	public void setTxtCustomerNo(TextField txtCustomerNo) {	this.txtCustomerNo = txtCustomerNo;	}
	public void setTxtCustomerTown(TextField txtCustomerTown) {	this.txtCustomerTown = txtCustomerTown;	}
	public void setTxtCustomerStreet(TextField txtCustomerStreet) {	this.txtCustomerStreet = txtCustomerStreet;	}
	public void setTxtCurrentDatabase(TextField txtCurrentDatabase) { this.txtCurrentDatabase = txtCurrentDatabase;	}
	public void setTxtCustomerPlc(TextField txtCustomerPlc) { this.txtCustomerPlc = txtCustomerPlc;	}
	public void setTxtCustomerName(TextField txtCustomerName) { this.txtCustomerName = txtCustomerName;	}
	public void setTxtCustomerSummary(TextField txtCustomerSummary) { this.txtCustomerSummary = txtCustomerSummary;	}
	public void setTxtCustomerSurname(TextField txtCustomerSurname) { this.txtCustomerSurname = txtCustomerSurname;	}
    
    // Getter
    public TextField getTxtCustomerOutstanding() { return txtCustomerOutstanding; }
	public TextField getTxtCustomerHouseNo() { return txtCustomerHouseNo; }
	public TextField getTxtCustomerID() { return txtCustomerID; }
	public TextField getTxtCustomerOrderAmount() { return txtCustomerOrderAmount; }
	public TextField getTxtCustomerNo() { return txtCustomerNo; }
	public TextField getTxtCustomerTown() { return txtCustomerTown;	}
	public TextField getTxtCustomerStreet() { return txtCustomerStreet;	}
	public TextField getTxtCurrentDatabase() { return txtCurrentDatabase; }
	public TextField getTxtCustomerPlc() { return txtCustomerPlc; }
	public TextField getTxtCustomerName() { return txtCustomerName;	}
	public TextField getTxtCustomerSummary() { return txtCustomerSummary; }
	public TextField getTxtCustomerSurname() { return txtCustomerSurname; }
    
	// Change Style

	@FXML
    void searchAction(ActionEvent event) {

    }

    @FXML
    void createOrder(ActionEvent event) {

    }

    @FXML
    void deleteCustomer(ActionEvent event) {

    }

    @FXML
    void createDataBase(ActionEvent event) {

    }

    @FXML
    void createNewCustomer(ActionEvent event) {

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
    void exportAsPDF(ActionEvent event) {

    }

    @FXML
    void exportAsExcel(ActionEvent event) {

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
		    primaryStage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      	
    }
    
    
    

}

