package com.customermanagement.statistics;
import com.customermanagement.entities.Obj_Order;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Table_fx {																															// Tableview for Order View for one Customer

	   
	    private TableView<Obj_Order> fxTableView = new TableView<Obj_Order>();
	    private ObservableList<Obj_Order> orders = FXCollections.observableArrayList();
	    
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public TableView<Obj_Order> createTable() {
	        
			System.out.println("Orderlist grösse : " + orders.size());
	        if (orders.size() > 0 ) {
	           
	           TableColumn colOrderNo   = new TableColumn("Bestellnummer");																	// create all Columns for Table
	           			   colOrderNo.setMinWidth(100);
	                       colOrderNo.setCellValueFactory(new PropertyValueFactory<Obj_Order,String>("orderNo"));	         					
                      
	           TableColumn colOrderDate  = new TableColumn("Bestelldatum");
	                       colOrderDate.setMinWidth(100);
	                       colOrderDate.setCellValueFactory(new PropertyValueFactory<Obj_Order,String>("orderDate"));
	                       
	           TableColumn colOrderSummary  = new TableColumn("Bestellsumme");
	                       colOrderSummary.setMinWidth(100);
	                       colOrderSummary.setCellValueFactory(new PropertyValueFactory<Obj_Order,String>("orderSummary"));
	                       
	           TableColumn colOrderCount  = new TableColumn("Ratenzahl");
	                       colOrderCount.setMinWidth(100);
	                       colOrderCount.setCellValueFactory(new PropertyValueFactory<Obj_Order,String>("rateCount"));
	           
	           TableColumn colPayStart = new TableColumn("Zahlungsstart");
	                       colPayStart.setMinWidth(100);
	                       colPayStart.setCellValueFactory(new PropertyValueFactory<Obj_Order,String>("payStart"));
	           
	           TableColumn colPayEnd = new TableColumn("Zahlungsende");    
	                       colPayEnd.setMinWidth(100);
	                       colPayEnd.setCellValueFactory(new PropertyValueFactory<Obj_Order,String>("payEnd"));
	                       
	           TableColumn colFirstRate = new TableColumn("Erste Rate");    
	                       colFirstRate.setMinWidth(100);
	                       colFirstRate.setCellValueFactory(new PropertyValueFactory<Obj_Order,String>("firstRate"));       
	                       
	           TableColumn colRates = new TableColumn("Folgende Raten");    
	                       colRates.setMinWidth(100);
	                       colRates.setCellValueFactory(new PropertyValueFactory<Obj_Order,String>("rate"));
	                       
	           TableColumn colToPay = new TableColumn("Noch Offen");    
	                       colToPay.setMinWidth(100);
	                       colToPay.setCellValueFactory(new PropertyValueFactory<Obj_Order,String>("stillToPay"));            

	           TableColumn colPayed = new TableColumn("Bereits gezahlt");    
	                       colPayed.setMinWidth(100);
	                       colPayed.setCellValueFactory(new PropertyValueFactory<Obj_Order,String>("alreadyPaid"));              
       
	           fxTableView.getColumns().addAll(colOrderNo,colOrderDate,colOrderSummary,colOrderCount,						// Add all visible Columns
	        		   						   colPayStart,colPayEnd,colFirstRate,colRates,colToPay,colPayed);		             
	           fxTableView.setItems(orders);
	           
	          
	       }
	    
     
	    return fxTableView; 																								 // return finished Tableview
	    }
	   
    
	    public void setOrderlist(ObservableList<Obj_Order> orders) { this.orders = orders; }												    // Setter for order Objects
	
}
