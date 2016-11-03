package com.customermanagement.main;

import org.apache.log4j.Logger;
import com.customermanagement.entities.Obj_Order;
import com.customermanagement.helpers.*;
import com.customermanagement.listeners.Button_Listeners;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Cust_Gui extends Application {
	    // Gui Buttons
	    public Button btn_Cust_Save = new Button("Datensatz speichern");   				
	    public Button btn_Cust_NoSave = new Button("Abbruch");
	    public Button btn_Order_Save = new Button("Bestellung speichern");
	    public Button btn_Order_NoSave = new Button("Abbruch");
	    public Button btn_Cust_Search = new Button("Kunde suchen");
	    public Button btn_Cust_New = new Button("Neuer Kunde");
	    public Button btn_Order_New = new Button("Neue Bestellung");
	    public Button btn_Cust_Del = new Button("Kunde Loeschen");
	    public Button btn_Select_Db = new Button("Datenbankauswahl");
	    public Button btn_New_Db = new Button("Neue Datenbank");
	    public Button btn_Plan_Pdf = new Button("Ratenplan als PDF");
	    Button btn_Plan_Print = new Button("Ratenplan Drucken");
	    public Button btn_Order_Del = new Button("Bestellung Löschen");
	    public Button btn_Order_Change = new Button("Bestellung ändern");
	    public Button btn_Plan_Excel = new Button("Excel Export");
	    
	    private CheckBox chkHibernate = new CheckBox("Hibernate Datenbank nutzen !");
	    // Textfield for stats and Extras
	    TextField txt_Selected_Db = new TextField();               					
	    TextField txt_Order_Count = new TextField();
	    TextField txt_Total = new TextField();
	    
	    // Textfields for Customer
	    TextField txt_Cust_Nr = new TextField();			     					
	    TextField txt_Cust_LastName = new TextField();
	    TextField txt_Cust_Name = new TextField();
	    TextField txt_Cust_Street = new TextField();
	    TextField txt_Cust_HNr = new TextField();
	    TextField txt_Cust_Pc = new TextField();
	    TextField txt_Cust_Res = new TextField();
	    
	    // Textfields for Orders
	    TextField txt_Order_Cust_Nr = new TextField();	          				      
	    TextField txt_Order_Nr = new TextField();
	    TextField txt_Order_Date = new TextField();
	    TextField txt_Pay_Start = new TextField();
	    TextField txt_Pay_End = new TextField();
	    TextField txt_Rate_Count = new TextField();
	    TextField txt_First_Rate = new TextField();
	    TextField txt_Rate = new TextField();
	    TextField txt_Order_Summary = new TextField();
	    
	    // Logger   ==> Log4j - Framework
	    private static final Logger logger = Logger_Init.getInstance();			                     
	    
	    TableView<Obj_Order> fx_Table_View = new TableView<Obj_Order>();
	    ObservableList<Obj_Order> tbl_Data_Records = FXCollections.observableArrayList();
	   
	    public ListView<String> lstv_Order_List = new ListView<String>();							// vorher bestellliste
	    ObservableList<String> lst_Orderlist_Items = FXCollections.observableArrayList();			// vorher Items
		    
	    XYChart.Series daten;
	    ObservableList<XYChart.Series<String, Double>> obs_Lst_Data = FXCollections.observableArrayList();
	    Series<String, Double> series_Data = new Series<String, Double>();
	     
	    TabPane reg_Panel = new TabPane();
	    public Tab tb_Table = new Tab();
	    Tab tb_LineChart = new Tab();
	    
	    MenuBar mbar_Cust = new MenuBar();
	       
	    Menu m_File= new Menu("Datei");
	    public MenuItem m_Cust_View = new MenuItem("Alle Kunden");
	    public MenuItem m_Exit = new MenuItem("Beenden");
	    	    
	    CategoryAxis xAxis = new CategoryAxis();
	    NumberAxis yAxis = new NumberAxis();
	    LineChart<String,Number> linechart;
	    
	    private final int col_1 = 0;
	    private final int col_2 = 1;
	    private final int col_3 = 2;
	    private final int col_4 = 3;
	    private final int col_5 = 4;
	    private final int col_6 = 5;
	    private final int col_7 = 6;
	    private final int col_8 = 7;
	    private final int col_9 = 8;
	    private final int row_4 = 5;
	    private int order_Flag;
	    
	    @Override
	    public void start(Stage primaryStage) {
	    	
	        GridPane mGui_grid = new GridPane();
	        mGui_grid.setPadding(new Insets(10,10,10,10));
	        mGui_grid.setVgap(5);
	        mGui_grid.setHgap(5);
	      
	        m_File.getItems().addAll(m_Cust_View,m_Exit);
	        
	        Menu menuBearbeiten = new Menu("Bearbeiten");
	        
	        Menu menuInfo = new Menu("Info");
	        MenuItem menuProgInfo = new MenuItem("Progamm Info");
	        
	        menuInfo.getItems().addAll(menuProgInfo,m_Exit);
	        
	        mbar_Cust.getMenus().addAll(m_File,menuBearbeiten,menuInfo);
	        // Menuebarsize scale to windowsize
	        mbar_Cust.prefWidthProperty().bind(primaryStage.widthProperty());          
	        
	        // Labels for Customer 
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
	        
	        // Labels for Orders
	        Label lbl_Order_Cust_Nr = new Label("Für Kundennummer :");		          
	        Label lbl_Order_Nr = new Label("Bestellnr :");
	        Label lbl_Order_Date = new Label("Bestelldatum :");
	        Label lbl_Pay_Start = new Label("Zahlungsstart :");
	        Label lbl_Pay_End = new Label("Zahlungsende :");
	        Label lbl_Rate_Count = new Label("Ratenanzahl :");
	        Label lbl_First_Rate = new Label("Ersterate :");
	        Label lbl_Rate = new  Label("Folgerate :");
	        Label lbl_Order_Summary = new Label("Bestellsumme :");
	     	                    
	        TextField txt_Restsumme = new TextField();
	        
	        // Textfields not writeable 
	        txt_Selected_Db.setEditable(false);  								      
	        txt_Order_Count.setEditable(false);
	        txt_Total.setEditable(false);
	        txt_Pay_End.setEditable(false);
	        txt_Order_Summary.setEditable(false);
	        
	        // No Tab selection needed for this textfields
	        txt_Pay_End.setFocusTraversable(false);      					         
	        txt_Order_Summary.setFocusTraversable(false);
	        
	        // Set Items in listview
	        lstv_Order_List.setItems(lst_Orderlist_Items);      					 
	        lstv_Order_List.setMaxHeight(260.00);

	        // Customer Labels
	        VBox lbl_Customer = new VBox(13);        							    
	        	 lbl_Cust_Nr.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Cust_Lastname.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Cust_Name.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Cust_Street.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Cust_Hnr.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Cust_Pc.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Cust_Res.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Order_Count.setMaxWidth(Double.MAX_VALUE);
	        	 lbl_Cust_Total.setMaxWidth(Double.MAX_VALUE);
	             lbl_Restsumme.setMaxWidth(Double.MAX_VALUE);
	             lbl_Active_DB.setMaxWidth(Double.MAX_VALUE);
	             lbl_Customer.getChildren().addAll(lbl_Cust_Nr,lbl_Cust_Lastname,lbl_Cust_Name,lbl_Cust_Street,lbl_Cust_Hnr,lbl_Cust_Pc,
	            		 						   lbl_Cust_Res,lbl_Order_Count,lbl_Cust_Total,lbl_Restsumme,lbl_Active_DB);
	        mGui_grid.add(lbl_Customer,col_1,row_4,1,11);         			         //add ( Label Name, column,row, from Element, to Element )
	  
	        // VBox for Customer and Customer Stat Fields
	        VBox vb_Cust_Fields= new VBox(5);                            		     
	        	 txt_Cust_Nr.setMaxWidth(Double.MAX_VALUE);
	        	 txt_Cust_LastName.setMaxWidth(Double.MAX_VALUE);
	             txt_Cust_Name.setMaxWidth(Double.MAX_VALUE);
	             txt_Cust_Street.setMaxWidth(Double.MAX_VALUE);
	             txt_Cust_HNr.setMaxWidth(Double.MAX_VALUE);
	             txt_Cust_Pc.setMaxWidth(Double.MAX_VALUE);
	             txt_Cust_Res.setMaxWidth(Double.MAX_VALUE);
	             txt_Order_Count.setMaxWidth(Double.MAX_VALUE);
	             txt_Total.setMaxWidth(Double.MAX_VALUE);
	             txt_Restsumme.setMaxWidth(Double.MAX_VALUE);
	             txt_Selected_Db.setMaxWidth(Double.MAX_VALUE);
	             vb_Cust_Fields.getChildren().addAll(txt_Cust_Nr,txt_Cust_LastName,txt_Cust_Name,txt_Cust_Street,
	            		 						    txt_Cust_HNr,txt_Cust_Pc,txt_Cust_Res,txt_Order_Count,
	            		 						    txt_Total,txt_Restsumme,txt_Selected_Db);
	       mGui_grid.add(vb_Cust_Fields,col_2,row_4,1,11);                           //add ( Label Name, column,row, from Element, to Element )
           
	       // Database Buttons
           HBox hb_Db_Btn = new HBox(10);                                            
	         	HBox.setHgrow(btn_Select_Db, Priority.ALWAYS);
	            HBox.setHgrow(btn_New_Db, Priority.ALWAYS);
	            HBox.setHgrow(chkHibernate, Priority.ALWAYS);
	            btn_Select_Db.setMaxWidth(Double.MAX_VALUE);
	            btn_New_Db.setMaxWidth(Double.MAX_VALUE);
	            hb_Db_Btn.getChildren().addAll(btn_Select_Db,btn_New_Db,chkHibernate);
	       mGui_grid.add(hb_Db_Btn, col_2,16);
	       
	       // vbox customer Buttons            
	       VBox vb_Cust_Btn = new VBox(5);                                         
	            btn_Cust_Search.setMaxWidth(Double.MAX_VALUE);
	        	btn_Cust_New.setMaxWidth(Double.MAX_VALUE);
	        	btn_Cust_Save.setMaxWidth(Double.MAX_VALUE);
	        	btn_Cust_NoSave.setMaxWidth(Double.MAX_VALUE);
	        	btn_Plan_Pdf.setMaxWidth(Double.MAX_VALUE);
	        	btn_Plan_Print.setMaxWidth(Double.MAX_VALUE);
	            btn_Cust_Del.setMaxWidth(Double.MAX_VALUE);
	            btn_Plan_Excel.setMaxWidth(Double.MAX_VALUE);
	            vb_Cust_Btn.getChildren().addAll(btn_Cust_Search,btn_Cust_New,btn_Cust_Save,btn_Cust_NoSave,btn_Cust_Del,
	                 	 						 btn_Plan_Pdf,btn_Plan_Print,btn_Plan_Excel);
	       mGui_grid.add(vb_Cust_Btn,col_4,row_4,1,7); 
   
	      // Vbox Label Orderlist
	      VBox vb_lbl_Orderlist = new VBox(5);      					            
	           lbl_Order_List.setMaxWidth(Double.MAX_VALUE);
	           vb_lbl_Orderlist.getChildren().addAll(lbl_Order_List);
	      mGui_grid.add(vb_lbl_Orderlist,col_5,row_4);  
	      
	      // VBox Orderlist
	      VBox vb_Order_lst = new VBox(0);               					       
	           lstv_Order_List.setMaxWidth(Double.MAX_VALUE);
	           vb_Order_lst.getChildren().addAll(lstv_Order_List);
	      mGui_grid.add(vb_Order_lst,col_6,row_4); 

	      // VBox Order Labels
	      VBox vb_Order_lbls = new VBox(13);              					   
	       	   lbl_Order_Cust_Nr.setMaxWidth(Double.MAX_VALUE);
	       	   lbl_Order_Nr.setMaxWidth(Double.MAX_VALUE);
	       	   lbl_Order_Date.setMaxWidth(Double.MAX_VALUE);
	       	   lbl_Pay_Start.setMaxWidth(Double.MAX_VALUE);
	       	   lbl_Pay_End.setMaxWidth(Double.MAX_VALUE);
	       	   lbl_Rate_Count.setMaxWidth(Double.MAX_VALUE);
	       	   lbl_First_Rate.setMaxWidth(Double.MAX_VALUE);
	       	   lbl_Rate.setMaxWidth(Double.MAX_VALUE);
	       	   lbl_Order_Summary.setMaxWidth(Double.MAX_VALUE);
	       	   vb_Order_lbls.getChildren().addAll(lbl_Order_Cust_Nr,lbl_Order_Nr,lbl_Order_Date,lbl_Pay_Start,
	       				                          lbl_Pay_End,lbl_Rate_Count,lbl_First_Rate,lbl_Rate,lbl_Order_Summary);
	       mGui_grid.add(vb_Order_lbls,col_7,row_4,1,9);  
	        
	       VBox txt_Order_Fields = new VBox(4);              			          // VBox Order Textfields
	       		txt_Order_Cust_Nr.setMaxWidth(Double.MAX_VALUE);
	       		txt_Order_Nr.setMaxWidth(Double.MAX_VALUE);
	       		txt_Order_Date.setMaxWidth(Double.MAX_VALUE);
	       		txt_Pay_Start.setMaxWidth(Double.MAX_VALUE);
	       		txt_Pay_End.setMaxWidth(Double.MAX_VALUE);
	       		txt_Rate_Count.setMaxWidth(Double.MAX_VALUE);
	       		txt_First_Rate.setMaxWidth(Double.MAX_VALUE);
	       		txt_Rate.setMaxWidth(Double.MAX_VALUE);
	       		txt_Order_Summary.setMaxWidth(Double.MAX_VALUE);
	       		txt_Order_Fields.getChildren().addAll(txt_Order_Cust_Nr,txt_Order_Nr,txt_Order_Date,txt_Pay_Start,txt_Pay_End,
	            								     txt_Rate_Count,txt_First_Rate,txt_Rate,txt_Order_Summary);
	       mGui_grid.add(txt_Order_Fields,col_8,row_4,1,9);                 //add ( Label Name, column,row, from Element, to Element )
	       
           VBox vb_Order_Btn = new VBox(4);                                 // Order Buttons
	        	btn_Order_New.setMaxWidth(Double.MAX_VALUE);
	        	btn_Order_Change.setMaxWidth(Double.MAX_VALUE);
	        	btn_Order_Save.setMaxWidth(Double.MAX_VALUE);
	        	btn_Order_NoSave.setMaxWidth(Double.MAX_VALUE);
	        	btn_Order_Del.setMaxWidth(Double.MAX_VALUE);
	            vb_Order_Btn.getChildren().addAll(btn_Order_New,btn_Order_Change,btn_Order_Save,btn_Order_NoSave,btn_Order_Del);
	       mGui_grid.add(vb_Order_Btn,col_9,row_4,1,5); 
  
	                 
	       reg_Panel.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

	       tb_Table.setText("Bestell - Historie");
	       tb_Table.setContent(fx_Table_View);
	        
	       xAxis.setLabel("Monate");
	       yAxis.setLabel("Rate in Euro");
	       linechart = new LineChart(xAxis,yAxis);
	       tb_LineChart.setText("Diagramm - Ratenplan");
	       tb_LineChart.setContent(linechart);
	        
	       reg_Panel.getTabs().addAll(tb_Table,tb_LineChart);
	               
	       HBox btn_diaTab_layout = new HBox(1);
	            btn_diaTab_layout.getChildren().addAll(reg_Panel);
	            HBox.setHgrow(reg_Panel, Priority.ALWAYS);
	            reg_Panel.setMaxWidth(Double.MAX_VALUE);
	       mGui_grid.add(btn_diaTab_layout, col_1,17,10,3); 
	                                            //   page.add(Node, colIndex, rowIndex, colSpan, rowSpan):
	                    
	       Group guiGruppe = new Group();
	             guiGruppe.getChildren().addAll(mGui_grid,mbar_Cust);
	        
	        Scene scene = new Scene(guiGruppe,1500, 800);      
	              
	        Gui_States gui_States = new Gui_States();
	                   gui_States.gui_State_Start(this);
	                      
	        Button_Listeners btn_Listener = new Button_Listeners(gui_States,this,logger,primaryStage);
	       
	        
	        // Feldueberwachung absichern = new Feldueberwachung(gui_States,this,primaryStage);
	        
	        Save_Database_Information database_Exists = new Save_Database_Information();
	        						  database_Exists.check_Database_File(gui_States,this,logger);
	                         
	        primaryStage.setTitle("Kundenverwaltung");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    	
	    }
	    
	    
	    
	
	public static void main(String[] args) {
		launch(args);

	}
	
	 
	    
	 //   public void setDatenbankAus( String text ) { this.txt_DatenbankAus.setText(text); }
	 public String getCustNr() { return txt_Cust_Nr.getText(); }							   // Customer Getter and Setter
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
	 
	 
	 public void setTotal(String total ) { this.txt_Total.setText(total); }					    					// Stats and Extras
	 public void setOrderCount(int orderCount) { this.txt_Order_Count.setText("" + orderCount);}
	 public String getActiveDB () { return txt_Selected_Db.getText();}
	 public void setActiveDB(String db){this.txt_Selected_Db.setText(db); }
	 	 
	 public String getOrderCustNr () { return txt_Order_Cust_Nr.getText(); }					  					// Orders Getter and Setter
     public void setOrderCustNr(String orderCustNr ) { this.txt_Order_Cust_Nr.setText(orderCustNr); }
     public String getOrderNr() { return txt_Order_Nr.getText(); }
     public void setOrderNr(String orderNr)   { this.txt_Order_Nr.setText(orderNr); }
     public String getOrderDate() { return txt_Order_Date.getText(); }
     public void setOrderDate(String orderDate)    { this.txt_Order_Date.setText(orderDate); }
     public String getPayStart() { return txt_Pay_Start.getText(); }
     public void setPayStart(String payStart)   { this.txt_Pay_Start.setText(payStart); }
     public String getPayEnd() { return txt_Pay_End.getText(); }
	 public void setPayEnd(String payEnd)    { this.txt_Pay_End.setText(payEnd);}
	 public String getRateCount() { return txt_Rate_Count.getText(); }
	 public void setRateCount(String rateCount)       { this.txt_Rate_Count.setText(rateCount); }
	 public String getFirstRate() { return txt_First_Rate.getText(); }
	 public void setFirstRate(String firstRate){ this.txt_First_Rate.setText(firstRate); }  
	 public String getRate() { return txt_Rate.getText(); }
	 public void setRate(String rate){ this.txt_Rate.setText(rate);}
	 public void setOrderSummary(String orderSummary){ this. txt_Order_Summary.setText(orderSummary); }
	 public String getOrderSummary() { return  txt_Order_Summary.getText(); }
	 
	 public Boolean getChkHibernateValue() { return chkHibernate.selectedProperty().getValue(); }
	 
	 public void setFirstRate(Double f_rate)   { this.txt_First_Rate.setText(f_rate.toString()); }                  // Overloaded Methods  - for set Double or String or Int for this Fields
	 public void setRate(Double f_rate) { this.txt_Rate.setText(f_rate.toString());}
	 public void setOrderSummary(Double o_summe) { this.txt_Order_Summary.setText(o_summe.toString()); }
	 public void setRateCount(int rateCount){ this.txt_Rate_Count.setText(Integer.toString(rateCount)); }

	 
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
     
     public void setEditOrderCustNr(boolean on_off, String color) { this.txt_Order_Cust_Nr.setEditable(on_off);      // Order Fields Design and activation
     																this.txt_Order_Cust_Nr.setStyle("-fx-control-inner-background: " + color + ";");}
     
     
	 public void setBadResult() { this.txt_Selected_Db.setStyle("-fx-control-inner-background: red;"); }  			// Extra Fields Database Status etc.
	 public void setGoodResult() { this.txt_Selected_Db.setStyle("-fx-control-inner-background: green;"); }
     
     
public void setBestellNummernListe(ObservableList lst_Orderlist_Items) { this.lst_Orderlist_Items = lst_Orderlist_Items;
																	lstv_Order_List.setItems(lst_Orderlist_Items);}
public ObservableList getBestellNummernListe() { return lstv_Order_List.getItems(); } 

public void setEditOrderNr(boolean on_off,String color){ this.txt_Order_Nr.setEditable(on_off);
     													 this.txt_Order_Nr.setStyle("-fx-control-inner-background: " + color + ";");  }
public void setEditOrderDate(boolean on_off,String color) { this.txt_Order_Date.setEditable(on_off);
     														this.txt_Order_Date.setStyle("-fx-control-inner-background: " + color + ";");}
public void setEditPayStart(boolean on_off, String color) { this.txt_Pay_Start.setEditable(on_off);
     														this.txt_Pay_Start.setStyle("-fx-control-inner-background: " + color + ";");}
public void setEditRateCount(boolean on_off,String color)    { this.txt_Rate_Count.setEditable(on_off);
     														   this.txt_Rate_Count.setStyle("-fx-control-inner-background: " + color + ";");}
public void setEditFirstRate(boolean on_off,String color) { this.txt_First_Rate.setEditable(on_off);
  															 this.txt_First_Rate.setStyle("-fx-control-inner-background: " + color + ";");}
public void setEditRate(boolean on_off,String color) { this.txt_Rate.setEditable(on_off); 
  													   this.txt_Rate.setStyle("-fx-control-inner-background: " + color + ";");}
	       
	    public void setBtnCustSave(boolean on_off)              { this.btn_Cust_Save.setVisible(on_off);        }    // Button visibillity
	    public void setBtnCustCancel(boolean on_off)            { this.btn_Cust_NoSave.setVisible(on_off); }
	    public void setBtnOrderSave(boolean on_off)             { this.btn_Order_Save.setVisible(on_off);   }
	    public void setBtnOrderNoSave(boolean on_off)           { this.btn_Order_NoSave.setVisible(on_off);     } 
	    
	    
	    public Integer getOrderFlag() { return order_Flag; }
	    public void setOrderFlag(Integer order_Flag)    { this.order_Flag = order_Flag; }
	    
	   
	         
	    public void setListOrderNumbers(String ordernumbers)   { lst_Orderlist_Items.add(ordernumbers);}
	    
	    

	  //  public void setTabellenRegister(TableView tabelle) { this.tabellenRegister.setContent(tabelle);}
	  public void setFxTableItems(ObservableList<Obj_Order> tbl_Data_Records) { this.tbl_Data_Records = tbl_Data_Records; }
	  //  public void setTableView(TableView tabFX ) { this.tabellenAnsichtFX = tabFX; }
	   

	   

	    
	    public void setLineData(XYChart.Series data) { this.linechart.getData().add(data); }
	


}
