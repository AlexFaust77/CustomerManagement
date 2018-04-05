package com.customermanagement.main;

import com.customermanagement.helpers.GuiState;
import com.customermanagement.helpers.Save_Database_Information;
import com.customermanagement.listeners.Button_Listeners;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrderGui extends Application{

	
	public Button btn_Order_Save = new Button("Bestellung speichern");
	public Button btn_Order_Cancel = new Button("Abbruch");
	public Button btn_Order_Del = new Button("Bestellung Löschen");
	
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
	TextField txt_stillToPay = new TextField();
	TextField txt_alreadyPaid = new TextField();
	
	private int orderFlag;							// 0 = New Order - 1 = Change Order
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
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
	     Label lbl_stillToPay = new  Label("Zu zahlen :");
	     Label lbl_alreadyPaid = new Label("bereits gezahlt :");
		
	     // not editable textfields
	     txt_Pay_End.setEditable(false);
	     txt_Order_Summary.setEditable(false);
	     txt_stillToPay.setEditable(false);
		 txt_alreadyPaid.setEditable(false);
		 txt_Order_Cust_Nr.setEditable(false);
		 
	     // No Tab selection needed for this textfields
	     txt_Pay_End.setFocusTraversable(false);      					         
	     txt_Order_Summary.setFocusTraversable(false);
		
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
	       	   lbl_stillToPay.setMaxWidth(Double.MAX_VALUE);
		       lbl_alreadyPaid.setMaxWidth(Double.MAX_VALUE);
	       	   vb_Order_lbls.getChildren().addAll(lbl_Order_Cust_Nr,lbl_Order_Nr,lbl_Order_Date,lbl_Pay_Start,
	       				                          lbl_Pay_End,lbl_Rate_Count,lbl_First_Rate,lbl_Rate,lbl_Order_Summary,lbl_stillToPay,lbl_alreadyPaid);	
	       	   
	       	   
	       	   
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
	       	   txt_stillToPay.setMaxWidth(Double.MAX_VALUE);
	       	   txt_alreadyPaid.setMaxWidth(Double.MAX_VALUE);
	       	   txt_Order_Fields.getChildren().addAll(txt_Order_Cust_Nr,txt_Order_Nr,txt_Order_Date,txt_Pay_Start,txt_Pay_End,
	            								     txt_Rate_Count,txt_First_Rate,txt_Rate,txt_Order_Summary,txt_stillToPay,txt_alreadyPaid);  	   
	       	   
	       
	       VBox vb_Order_Btn = new VBox(4);                                 // Order Buttons
	 	        btn_Order_Save.setMaxWidth(Double.MAX_VALUE);
	 	        btn_Order_Cancel.setMaxWidth(Double.MAX_VALUE);
	 	        btn_Order_Del.setMaxWidth(Double.MAX_VALUE);
	 	        vb_Order_Btn.getChildren().addAll(btn_Order_Save,btn_Order_Cancel,btn_Order_Del);
	       	   
	  
	       Group guiGroup = new Group();
           Scene scene = new Scene(guiGroup,800, 450);      
              
	       BorderPane mainpane = new BorderPane();
           			  mainpane.prefHeightProperty().bind(scene.heightProperty());
           			  mainpane.prefWidthProperty().bind(scene.widthProperty());
           			  mainpane.setPadding(new Insets(50,40,50,40));
           			  
           			  
           			  
           HBox leftElements = new HBox(5.0); 
           		leftElements.getChildren().addAll(vb_Order_lbls,txt_Order_Fields,vb_Order_Btn);		  
	       
                     
           
           mainpane.setLeft(leftElements);
           mainpane.setMargin(leftElements, new Insets(0,0,20,20));               
	               
	       guiGroup.getChildren().addAll(mainpane);
	                  
	      // Button_Listeners btn_Listener = new Button_Listeners(gui_States,this,logger,primaryStage);
     
	        primaryStage.setTitle("Bestellverwaltung");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	      	  
		
	}

	
	 // Order - Getter and Setter
	 public String getOrderCustNr () { return txt_Order_Cust_Nr.getText(); }					  					
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
	 public String getStillToPay() { return txt_stillToPay.getText(); }
     public void setStillToPay(Double stillToPay) {this.txt_stillToPay.setText(stillToPay.toString()); }
     public String getAlreadyPaid() { return txt_alreadyPaid.getText();}
	 public void setAlreadyPaid(Double alreadyPaid) { this.txt_alreadyPaid.setText(alreadyPaid.toString()); }
	
	 // Overloaded Methods  - for set Double or String or Int for this Fields
	 public void setFirstRate(Double f_rate)   { this.txt_First_Rate.setText(f_rate.toString()); }                  
	 public void setRate(Double f_rate) { this.txt_Rate.setText(f_rate.toString());}
	 public void setOrderSummary(Double o_summe) { this.txt_Order_Summary.setText(o_summe.toString()); }
	 public void setRateCount(int rateCount){ this.txt_Rate_Count.setText(Integer.toString(rateCount)); }
	 
	 // Order Fields Design and activation
     public void setEditOrderCustNr(boolean on_off, String color) { this.txt_Order_Cust_Nr.setEditable(on_off);      
	 this.txt_Order_Cust_Nr.setStyle("-fx-control-inner-background: " + color + ";");}
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
	 
     // Visibility off Buttons
	 public void setBtnOrderSave(boolean on_off)             { this.btn_Order_Save.setVisible(on_off);   }
	 public void setBtnOrderNoSave(boolean on_off)           { this.btn_Order_Cancel.setVisible(on_off);     } 
	 
	 public Integer getOrderFlag() { return orderFlag; }
	 public void setOrderFlag(Integer orderFlag)    { this.orderFlag = orderFlag; }
	 
	 
	   	
}
