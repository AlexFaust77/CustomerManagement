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

	    Button btn_Cust_Save = new Button("Datensatz speichern");   				// Gui Buttons
	    Button btn_Cust_NoSave = new Button("Abbruch");
	    Button btn_Order_Save = new Button("Bestellung speichern");
	    Button btn_Order_NoSave = new Button("Abbruch");
	    Button btn_Cust_Search = new Button("Kunde suchen");
	    Button btn_Cust_New = new Button("Neuer Kunde");
	    Button btn_Order_New = new Button("Neue Bestellung");
	    Button btn_Cust_Del = new Button("Kunde Loeschen");
	    Button btn_Select_Db = new Button("Datenbankauswahl");
	    Button btn_New_Db = new Button("Neue Datenbank");
	    Button btn_Plan_Pdf = new Button("Ratenplan als PDF");
	    Button btn_Plan_Print = new Button("Ratenplan Drucken");
	    Button btn_Order_Del = new Button("Bestellung Löschen");
	    Button btn_Order_Change = new Button("Bestellung ändern");
	    Button btn_Plan_Excel = new Button("Excel Export");
	
	    TextField txt_Selected_Db = new TextField();               					// Textfield for stats and Extras
	    TextField txt_Order_Count = new TextField();
	    TextField txt_Total = new TextField();
	    
	    TextField txt_Cust_Nr = new TextField();			     					// Textfields for Customer
	    TextField txt_Cust_LastName = new TextField();
	    TextField txt_Cust_Name = new TextField();
	    TextField txt_Cust_Street = new TextField();
	    TextField txt_Cust_HNr = new TextField();
	    TextField txt_Cust_Pc = new TextField();
	    TextField txt_Cust_Res = new TextField();
	    
	    TextField txt_Order_Cust_Nr = new TextField();	          				   // Textfields for Orders
	    TextField txt_Order_Nr = new TextField();
	    TextField txt_Order_Date = new TextField();
	    TextField txt_Pay_Start = new TextField();
	    TextField txt_Pay_End = new TextField();
	    TextField txt_Rate_Count = new TextField();
	    TextField txt_First_Rate = new TextField();
	    TextField txt_Rate = new TextField();
	    TextField txt_Order_Summary = new TextField();
	   
	    
	    ObservableList<DatenModellBest> tbl_Data_Records = FXCollections.observableArrayList();
	    ListView<String> lstv_Order_List = new ListView<String>();
	    ObservableList<String> items = FXCollections.observableArrayList();
	
	    TableView<DatenModellBest> fx_Table_View = new TableView<DatenModellBest>();
	    XYChart.Series daten;
	    ObservableList<XYChart.Series<String, Double>> obs_Lst_Data = FXCollections.observableArrayList();
	    Series<String, Double> series_Data = new Series<String, Double>();
	     
	    TabPane reg_Panel = new TabPane();
	    Tab tb_Reg = new Tab();
	    Tab tb_LineChart = new Tab();
	    
	    MenuBar mbar_Cust = new MenuBar();
	       
	    Menu m_File= new Menu("Datei");
	    MenuItem m_Cust_View = new MenuItem("Alle Kunden");
	    MenuItem m_Exit = new MenuItem("Beenden");
	    	    
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
	        menuInfo.getItems().addAll(menuProgInfo);
	        
	        mbar_Cust.getMenus().addAll(m_File,menuBearbeiten,menuInfo);
	        mbar_Cust.prefWidthProperty().bind(primaryStage.widthProperty());          // Menuebarsize scale to windowsize
	              
	        Label lbl_Cust_Nr = new Label("Kundennr :");						       // Labels for Customer 
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
	        
	        
	        Label lbl_Order_Cust_Nr = new Label("Für Kundennummer :");		          // Labels for Orders
	        Label lbl_Order_Nr = new Label("Bestellnr :");
	        Label lbl_Order_Date = new Label("Bestelldatum :");
	        Label lbl_Pay_Start = new Label("Zahlungsstart :");
	        Label lbl_Pay_End = new Label("Zahlungsende :");
	        Label lbl_Rate_Count = new Label("Ratenanzahl :");
	        Label lbl_First_Rate = new Label("Ersterate :");
	        Label lbl_Rate = new  Label("Folgerate :");
	        Label lbl_Order_Summary = new Label("Bestellsumme :");
	     	                    
	        TextField txt_Restsumme = new TextField();
	              
	        txt_Selected_Db.setEditable(false);  								     // Textfields not writeable  
	        txt_Order_Count.setEditable(false);
	        txt_Total.setEditable(false);
	        txt_Pay_End.setEditable(false);
	        txt_Order_Summary.setEditable(false);
	        
	        txt_Pay_End.setFocusTraversable(false);      					         // No Tab selection needed for this textfields
	        txt_Order_Summary.setFocusTraversable(false);
	        
	        lstv_Order_List.setItems(items);      								     // Set Items in listview
	        lstv_Order_List.setMaxHeight(260.00);

	        VBox lbl_Customer = new VBox(13);        							     // Customer Labels
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
	  
	        VBox vb_Cust_Fields= new VBox(5);                            		     // VBox for Customer and Customer Stat Fields
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
             
           HBox hb_Db_Btn = new HBox(10);                                            // Database Buttons
	         	HBox.setHgrow(btn_Select_Db, Priority.ALWAYS);
	            HBox.setHgrow(btn_New_Db, Priority.ALWAYS);
	            btn_Select_Db.setMaxWidth(Double.MAX_VALUE);
	            btn_New_Db.setMaxWidth(Double.MAX_VALUE);
	            hb_Db_Btn.getChildren().addAll(btn_Select_Db,btn_New_Db);
	       mGui_grid.add(hb_Db_Btn, col_2,16);
	                   
	        VBox vb_Cust_Btn = new VBox(5);                                         // vbox customer Buttons
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
   

	        VBox vb_lbl_Orderlist = new VBox(5);      					            // Vbox Label Orderlist
	        	 lbl_Order_List.setMaxWidth(Double.MAX_VALUE);
	        	 vb_lbl_Orderlist.getChildren().addAll(lbl_Order_List);
	        mGui_grid.add(vb_lbl_Orderlist,col_5,row_4);  
	               
	        VBox vb_Order_lst = new VBox(0);               					        // VBox Orderlist
	        	 lstv_Order_List.setMaxWidth(Double.MAX_VALUE);
	        	 vb_Order_lst.getChildren().addAll(lstv_Order_List);
	        mGui_grid.add(vb_Order_lst,col_6,row_4); 

	        VBox vb_Order_lbls = new VBox(13);              					   // VBox Order Labels
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
  
	             
	             
	        registerPanel.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

	        tabellenRegister.setText("Bestell - Historie");
	        tabellenRegister.setContent(tabellenAnsichtFX);
	        
	        xAxis.setLabel("Monate");
	        yAxis.setLabel("Rate in Euro");
	        linechart = new LineChart(xAxis,yAxis);
	        newLineChartTest.setText("Diagramm - Ratenplan");
	        newLineChartTest.setContent(linechart);
	        
	        registerPanel.getTabs().addAll(tabellenRegister,newLineChartTest);
	               
	        HBox btn_diaTab_layout = new HBox(1);
	             btn_diaTab_layout.getChildren().addAll(registerPanel);
	             HBox.setHgrow(registerPanel, Priority.ALWAYS);
	             registerPanel.setMaxWidth(Double.MAX_VALUE);
	        positionsGitter.add(btn_diaTab_layout, sp1,17,10,3); 
	                                            //   page.add(Node, colIndex, rowIndex, colSpan, rowSpan):
	                    
	        Group guiGruppe = new Group();
	              guiGruppe.getChildren().addAll(positionsGitter,kdVerwMenuBar);
	        
	        Scene scene = new Scene(guiGruppe,1500, 800);      
	              
	        GUIZustaende guiPos = new GUIZustaende();
	                     guiPos.startZustand(this);
	                      
	        btnHandler btnHandling = new btnHandler(guiPos,this,primaryStage);
	        FieldListener listenToFields = new FieldListener(guiPos,this,primaryStage);
	        
	        Feldueberwachung absichern = new Feldueberwachung(guiPos,this,primaryStage);
	        
	        dbSpeicher dbBereitsVorhanden = new dbSpeicher();
	                   dbBereitsVorhanden.pruefenObDbInfovorhanden(guiPos,this);
	                         
	        primaryStage.setTitle("Kundenverwaltung");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    	
	    }
	    
	    
	    
	
	public static void main(String[] args) {
		launch(args);

	}
	
    /*
     * TextField txt_Selected_Db = new TextField();
TextField txt_Cust_Nr = new TextField();
TextField txt_Cust_LastName = new TextField();
TextField txt_Cust_Name = new TextField();
TextField txt_Cust_Street = new TextField();
TextField txt_Cust_HNr = new TextField();
TextField txt_Cust_Pc = new TextField();
TextField txt_Cust_Res = new TextField();
     *      
     *      
     */

}
