package com.customermanagement.main;

import org.apache.log4j.Logger;

import com.customermanagement.database.SQL_Statements;
import com.customermanagement.entities.Obj_Order;
import com.customermanagement.helpers.*;
import com.customermanagement.inputchecks.InputChecks;
import com.customermanagement.listeners.Button_Listeners;
import com.customermanagement.listeners.OrderListeners;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Cust_Gui extends Application {
	
	    // Gui Buttons
	    public Button btn_Cust_Save = new Button("Datensatz speichern");   				
	    public Button btn_Cust_NoSave = new Button("Abbruch");
	   
	    public Button btn_Cust_Search = new Button("Kunde suchen");
	    public Button btn_Cust_New = new Button("Neuer Kunde");
	    public Button btn_Order_New = new Button("Neue Bestellung");				// will be removed - new Gui Design in Progress
	    public Button btn_Cust_Del = new Button("Kunde Loeschen");
	    public Button btn_Select_Db = new Button("Datenbankauswahl");
	    public Button btn_New_Db = new Button("Neue Datenbank");
	    public Button btn_Plan_Pdf = new Button("Ratenplan als PDF");
	    Button btn_Plan_Print = new Button("Ratenplan Drucken");
	    public Button btn_Order_Change = new Button("Bestellung ändern");
	    public Button btn_Plan_Excel = new Button("Excel Export");
	   	    
	    public Button btn_New_Order = new Button("Neue Bestellung");
	    
	    private CheckBox chkHibernate = new CheckBox("Hibernate Datenbank nutzen !");
	    // Textfield for stats and Extras
	    TextField txt_Selected_Db = new TextField();               					
	    TextField txt_Order_Count = new TextField();
	    TextField txt_Total = new TextField();
	    TextField txtCustId = new TextField();
	    
	    // Textfields for Customer
	    TextField txt_Cust_Nr = new TextField();			     					
	    TextField txt_Cust_LastName = new TextField();
	    TextField txt_Cust_Name = new TextField();
	    TextField txt_Cust_Street = new TextField();
	    TextField txt_Cust_HNr = new TextField();
	    TextField txt_Cust_Pc = new TextField();
	    TextField txt_Cust_Res = new TextField();
  
	    // Logger   ==> Log4j - Framework
	    private static final Logger logger = Logger_Init.getInstance();			                     
	    
	    TableView<Obj_Order> fx_Table_View = new TableView<Obj_Order>();
	    ObservableList<Obj_Order> tbl_Data_Records = FXCollections.observableArrayList();
		    
	    XYChart.Series daten;
	    ObservableList<XYChart.Series<String, Double>> obs_Lst_Data = FXCollections.observableArrayList();
	    Series<String, Double> series_Data = new Series<String, Double>();
	     
	    public TabPane orderPanel = new TabPane();
	    TabPane chartPanel = new TabPane();
	    
	    public Tab tabCurrentOrders = new Tab();
	           Tab tabLineChart = new Tab();
	    
	    MenuBar mbar_Cust = new MenuBar();
	       
	    Menu m_File= new Menu("Datei");
	    public MenuItem m_Cust_View = new MenuItem("Alle Kunden");
	    public MenuItem m_Exit = new MenuItem("Beenden");
	    	    
	    CategoryAxis xAxis = new CategoryAxis();
	    NumberAxis yAxis = new NumberAxis();
	    LineChart<String,Number> linechart;
	    
	    public ListView<String> orderList = new ListView<>();
	    ObservableList<String>orderListItems = FXCollections.observableArrayList();
	    
	    @Override
	    public void start(Stage primaryStage) {
	    		        	        	      
	        m_File.getItems().addAll(m_Cust_View,m_Exit);
	        
	        Menu menuBearbeiten = new Menu("Bearbeiten");
	        
	        Menu menuInfo = new Menu("Info");
	        MenuItem menuProgInfo = new MenuItem("Progamm Info");
	        
	        menuInfo.getItems().addAll(menuProgInfo,m_Exit);
	        
	        mbar_Cust.getMenus().addAll(m_File,menuBearbeiten,menuInfo);
	        // Menuebarsize scale to windowsize
	        mbar_Cust.prefWidthProperty().bind(primaryStage.widthProperty());          
	        
	        // Labels for Customer 
	        Label lblCustId = new Label("Kunden ID");  
	        Label lbl_Cust_Nr = new Label("Kundennr :");						       
	        Label lbl_Cust_Lastname = new Label("Name :");
	        Label lbl_Cust_Name = new Label("Vorname :");
	        Label lbl_Cust_Street = new Label("Strasse :");
	        Label lbl_Cust_Hnr = new Label("Hausnummer :");
	        Label lbl_Cust_Pc = new Label("Postleitzahl :");
	        Label lbl_Cust_Res = new  Label("Ort :");
	        Label lbl_Order_Count = new Label("Anzahl Bestellungen :");
	        Label lbl_Cust_Total = new Label("Gesamtumsatz :");
	        Label lbl_Restsumme = new Label("Restsumme :");
	        Label lbl_Order_List = new Label("Bestellungen :");
	        Label lbl_Active_DB = new Label("Aktive Datenbank :");
	                
	        TextField txt_Restsumme = new TextField();
	        
	        // Textfields not writeable 
	        txt_Selected_Db.setEditable(false);
	        txtCustId.setEditable(false);
	        txt_Order_Count.setEditable(false);
	        txt_Total.setEditable(false);

	        // Customer Labels
	        VBox lbl_Customer = new VBox(13);
	        	 lblCustId.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Cust_Nr.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Cust_Lastname.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Cust_Name.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Cust_Street.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Cust_Hnr.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Cust_Pc.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Cust_Res.setMaxWidth(Double.MAX_VALUE);
	        lbl_Customer.getChildren().addAll(lblCustId,lbl_Cust_Nr,lbl_Cust_Lastname,lbl_Cust_Name,
	        								  lbl_Cust_Street,lbl_Cust_Hnr,lbl_Cust_Pc,lbl_Cust_Res);
	        
	        VBox lbl_Customer2 = new VBox(13);   
	        	 lbl_Order_Count.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Cust_Total.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Restsumme.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Active_DB.setMaxWidth(Double.MAX_VALUE);
	        lbl_Customer.getChildren().addAll(lbl_Order_Count,lbl_Cust_Total,lbl_Restsumme,lbl_Active_DB);	 
	        	 
	        // VBox for Customer and Customer Stat Fields  txtCustId lblCustId
	        VBox vb_Cust_Fields = new VBox(5); 
	        	 txtCustId.setMaxWidth(Double.MAX_VALUE);
	        	 txt_Cust_Nr.setMaxWidth(Double.MAX_VALUE);
	        	 txt_Cust_LastName.setMaxWidth(Double.MAX_VALUE);
	             txt_Cust_Name.setMaxWidth(Double.MAX_VALUE);
	             txt_Cust_Street.setMaxWidth(Double.MAX_VALUE);
	             txt_Cust_HNr.setMaxWidth(Double.MAX_VALUE);
	             txt_Cust_Pc.setMaxWidth(Double.MAX_VALUE);
	             txt_Cust_Res.setMaxWidth(Double.MAX_VALUE);
	        vb_Cust_Fields.getChildren().addAll(txtCustId,txt_Cust_Nr,txt_Cust_LastName,txt_Cust_Name,txt_Cust_Street,
		     		 						    txt_Cust_HNr,txt_Cust_Pc,txt_Cust_Res); 
	       
	       VBox vb_Cust_Fields2 = new VBox(5);     
	         	txt_Order_Count.setMaxWidth(Double.MAX_VALUE);
	        	txt_Total.setMaxWidth(Double.MAX_VALUE);
	        	txt_Restsumme.setMaxWidth(Double.MAX_VALUE);
	        	txt_Selected_Db.setMaxWidth(Double.MAX_VALUE);
	       vb_Cust_Fields.getChildren().addAll(txt_Order_Count,txt_Total,txt_Restsumme,txt_Selected_Db);
	        	
	       
	       HBox hbAllCustFields = new HBox(2);
	            hbAllCustFields.getChildren().addAll(lbl_Customer,vb_Cust_Fields);
	       
	       HBox vb_lblList = new HBox(2);
	       		lbl_Order_List.setMaxWidth(200);
	       		orderList.setMaxWidth(150);
	       		orderList.setMaxHeight(200);
	        	vb_lblList.getChildren().addAll(lbl_Order_List,orderList);
	       
	       HBox hbAllCustFields2 = new HBox(2); 	
	       		hbAllCustFields2.getChildren().addAll(lbl_Customer2,vb_Cust_Fields2);
	       
	       		
	       VBox hBoxContainer = new VBox();
	       		hBoxContainer.getChildren().addAll(hbAllCustFields,vb_lblList,hbAllCustFields2);
	       		

	       
	
	       // vbox customer Buttons            
	       VBox vb_Cust_Btn = new VBox(5);                                         
	            btn_Cust_Search.setMaxWidth(Double.MAX_VALUE);
	        	btn_Cust_New.setMaxWidth(Double.MAX_VALUE);
	        	btn_Cust_Save.setMaxWidth(Double.MAX_VALUE);
	        	btn_Cust_NoSave.setMaxWidth(Double.MAX_VALUE);
	        	btn_New_Order.setMaxHeight(Double.MAX_VALUE);
	        	btn_Plan_Pdf.setMaxWidth(Double.MAX_VALUE);
	        	btn_Plan_Print.setMaxWidth(Double.MAX_VALUE);
	            btn_Cust_Del.setMaxWidth(Double.MAX_VALUE);
	            btn_Plan_Excel.setMaxWidth(Double.MAX_VALUE);
	            btn_New_Db.setMaxWidth(Double.MAX_VALUE);
	            vb_Cust_Btn.getChildren().addAll(btn_Cust_Search,btn_Cust_New,btn_Cust_Save,btn_Cust_NoSave,btn_New_Order,btn_Cust_Del,
	                 	 						 btn_Plan_Pdf,btn_Plan_Print,btn_Plan_Excel,btn_New_Db);
	       
	       orderPanel.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
	       tabCurrentOrders.setText("Bestellungen");
	       tabCurrentOrders.setContent(fx_Table_View);
	       
	       chartPanel.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
	       xAxis.setLabel("Monate");
	       yAxis.setLabel("Rate in Euro");
	       linechart = new LineChart(xAxis,yAxis);
	       tabLineChart.setText("Diagramm - Ratenplan");
	       tabLineChart.setContent(linechart);
	        
	       orderPanel.getTabs().addAll(tabCurrentOrders);
	       chartPanel.getTabs().addAll(tabLineChart);
	       
	       HBox boxOrderTable = new HBox(1); // Box for Table ( Orders )
	       		boxOrderTable.getChildren().addAll(orderPanel);
	            HBox.setHgrow(orderPanel, Priority.ALWAYS);
	            			  orderPanel.setMaxWidth(Double.MAX_VALUE);
	            			  orderPanel.setMaxHeight(250);
            			  
	       HBox boxLineChart = new HBox(1);  // Box for LineChart
      			boxLineChart.getChildren().addAll(chartPanel);
           HBox.setHgrow(chartPanel, Priority.ALWAYS);
           				 chartPanel.setMaxWidth(Double.MAX_VALUE);
           				 chartPanel.setMaxHeight(250);
           
           orderList.setItems(orderListItems);				 
           				 
           Group guiGruppe = new Group();
           Scene scene = new Scene(guiGruppe,1600, 900);      
           
	       BorderPane mainpane = new BorderPane();
           			  mainpane.prefHeightProperty().bind(scene.heightProperty());
           			  mainpane.prefWidthProperty().bind(scene.widthProperty());
           			  mainpane.setPadding(new Insets(50,40,50,40));
          			  
           HBox leftElements = new HBox(5.0); 
           		//leftElements.getChildren().addAll(lbl_Customer,vb_Cust_Fields,vb_Cust_Btn);		  
           		leftElements.getChildren().addAll(hBoxContainer,vb_Cust_Btn);
          		
           VBox centerElements = new VBox();
           		centerElements.getChildren().addAll(boxOrderTable,boxLineChart);
          
           mainpane.setLeft(leftElements);
           mainpane.setCenter(centerElements);
	       mainpane.setMargin(centerElements, new Insets(0,0,20,20));               
	               
	       guiGruppe.getChildren().addAll(mainpane,mbar_Cust);
	                  
	       Gui_States gui_States = new Gui_States();
	                  gui_States.gui_State_Start(this);
	       
	                         
	       OrderGui orderGui = new OrderGui();										 // GUI for Order Management
	       Calculator calculate = new Calculator();									 // Calculate the Rates
	       InputChecks checkInput = new InputChecks();  							 // Check all Input from User
	       SQL_Statements dataBaseRequest = new SQL_Statements();			         // all SQL - Lite statements
	       
		   OrderGuiController orderGuiControl = new OrderGuiController(); 			 // FXML Controller for Order Form
		  
		   
		   
		   
		   MainGuiController mainGuiControl = new MainGuiController();			     // FXML Controller for Main Gui yet without Ojbekt - empty Constructor
		 		   
		   Stage mainGuiStage = new Stage();
		   
		  
		   
		   
		   
	       Button_Listeners btn_Listener = new Button_Listeners(gui_States,this,logger,primaryStage,calculate,dataBaseRequest,fx_Table_View,orderGuiControl);
	       OrderListeners orderListener = new OrderListeners(gui_States,this,logger, primaryStage,orderGui,checkInput,calculate,dataBaseRequest,fx_Table_View);
	        
	       // Feldueberwachung absichern = new Feldueberwachung(gui_States,this,primaryStage);
	        
	       Save_Database_Information database_Exists = new Save_Database_Information();
	        						 database_Exists.check_Database_File(gui_States,this,logger);
	    //   Old GUI                     
	    //   primaryStage.setTitle("Kundenverwaltung");
	    //   primaryStage.setScene(scene);
	    //   primaryStage.show();
	       
	       mainGuiControl.startFXMLMainGui(mainGuiStage);
	    	
	    }

	
	public static void main(String[] args) {
		launch(args);
		
		
	//	MainGuiController mainGuiControl = new MainGuiController();			     // FXML Controller for Main Gui yet without Ojbekt - empty Constructor
	//	Stage mainGuiStage = new Stage();
	//	mainGuiControl.startFXMLMainGui(mainGuiStage);

	}
		    
	 //   public void setDatenbankAus( String text ) { this.txt_DatenbankAus.setText(text); }
	 public String getCustId() { return txtCustId.getText(); }  													// Customer Getter and Setter
     public void setCustId(Integer custId) { this.txtCustId.setText(custId.toString());}
	 public String getCustNr() { return txt_Cust_Nr.getText(); }							   
	 public void setCustNr(String custNr) { this.txt_Cust_Nr.setText(custNr); }
	 public String getCustLastName() { return txt_Cust_LastName.getText(); }
	 public void setCustLastName( String custLastName ) { this.txt_Cust_LastName.setText(custLastName); }
	 public String getCustName() { return txt_Cust_Name.getText(); }
	 public void setCustName( String custName ) { this.txt_Cust_Name.setText(custName); }
	 public String getCustStreet() { return txt_Cust_Street.getText(); }
	 public void setCustStreet( String custStreet ) { this.txt_Cust_Street.setText(custStreet); }
	 public String getCustHNr() { return txt_Cust_HNr.getText(); }
	 public void setCustHNr(String custHNr) { this.txt_Cust_HNr.setText(custHNr); }
	 public String getCustPc() { return txt_Cust_Pc.getText(); }
	 public void setCustPc(String custPc) { this.txt_Cust_Pc.setText(custPc); }
	 public String getCustRes() { return txt_Cust_Res.getText(); }
	 public void setCustRes(String custRes) { this.txt_Cust_Res.setText(custRes); }
	 public ObservableList<String> getOrderListItems() { return orderListItems; }
	 public void setOrderListItems(ObservableList<String> orderListItems) { this.orderListItems = orderListItems; }
	 public void setTotal(String total ) { this.txt_Total.setText(total); }					    					// Stats and Extras
	 public void setOrderCount(int orderCount) { this.txt_Order_Count.setText("" + orderCount);}
	 public String getActiveDB () { return txt_Selected_Db.getText();}
	 public void setActiveDB(String db){this.txt_Selected_Db.setText(db); }
	 
	 public Boolean getChkHibernateValue() { return chkHibernate.selectedProperty().getValue(); }
	 	 
	 public void setEditCustNr(boolean on_off,String color)    { this.txt_Cust_Nr.setEditable(on_off); 				// Customer Field Design and activation
        													   this.txt_Cust_Nr.setStyle("-fx-control-inner-background: " + color + ";"); }
	 public void setEditLastName(boolean on_off,String color)   { this.txt_Cust_LastName.setEditable(on_off);
     															  this.txt_Cust_LastName.setStyle("-fx-control-inner-background: " + color + ";");}
	 public void setEditCustName(boolean on_off,String color) { this.txt_Cust_Name.setEditable(on_off);
     															this.txt_Cust_Name.setStyle("-fx-control-inner-background: " + color + ";");}
     public void setEditCustStreet(boolean on_off,String color) { this.txt_Cust_Street.setEditable(on_off); 
        	             										  this.txt_Cust_Street.setStyle("-fx-control-inner-background: " + color + ";");}
     public void setEditCustHNr(boolean on_off,String color)  { this.txt_Cust_HNr.setEditable(on_off); 
        												  this.txt_Cust_HNr.setStyle("-fx-control-inner-background: " + color + ";");}
     public void setEditCustPc(boolean on_off, String color)    { this.txt_Cust_Pc.setEditable(on_off);
        													 this.txt_Cust_Pc.setStyle("-fx-control-inner-background: " + color + ";");}
     public void setEditCustRes(boolean on_off,String color)     { this.txt_Cust_Res.setEditable(on_off);
     															  this.txt_Cust_Res.setStyle("-fx-control-inner-background: " + color + ";");}
     
	 public void setBadResult() { this.txt_Selected_Db.setStyle("-fx-control-inner-background: red;"); }  			// Extra Fields Database Status etc.
	 public void setGoodResult() { this.txt_Selected_Db.setStyle("-fx-control-inner-background: green;"); }
	       
	 public void setBtnCustSave(boolean on_off)              { this.btn_Cust_Save.setVisible(on_off);        }    // Button visibillity
	 public void setBtnCustCancel(boolean on_off)            { this.btn_Cust_NoSave.setVisible(on_off); }
 	  
	    //  public void setTabellenRegister(TableView tabelle) { this.tabellenRegister.setContent(tabelle);}
	 public void setFxTableItems(ObservableList<Obj_Order> tbl_Data_Records) { this.tbl_Data_Records = tbl_Data_Records; }
	 public TableView<Obj_Order> getTableView() { return this.fx_Table_View; }
	 
	 public void setTableView(TableView tableView ) { this.fx_Table_View = tableView;
	 												  this.fx_Table_View.refresh();}
    
	 public void setLineData(XYChart.Series data) { this.linechart.getData().add(data); }
}
