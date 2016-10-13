package com.customermanagement.statistics;
import java.util.ArrayList;

import com.customermanagement.entities.Obj_Order;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Table_fx {																															// Tableview for Order View for one Customer

	    private ArrayList<Obj_Order> orderlist = new ArrayList<Obj_Order>();
	    private TableView<Obj_Order> fxTableView = new TableView<Obj_Order>();
	    private ObservableList<Obj_Order> orders = FXCollections.observableArrayList();
	    
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public TableView<Obj_Order> erstelleFXTableView() {
	        
			System.out.println("Orderlist grösse : " + orderlist.size());
	        if (orderlist.size() > 0 ) {
	         
	           fillorderlist();																												// parse Arraylist to Observablelist
           
	           
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
	       
	           fxTableView.getColumns().addAll(colOrderNo,colOrderDate,colOrderSummary,colOrderCount,colPayStart,colPayEnd);		             // Add all Columns
	           fxTableView.setItems(orders);
	          
	       }
	    
     
	    return fxTableView; 																													 // return finished Tableview
	    }
	   
	    public void fillorderlist() {
	           for(int i = 0;i < orderlist.size();i++) {
	              	   orders.add(orderlist.get(i));
	   	       } 
	    }
	    
	    public void setOrderlist(ArrayList<Obj_Order> orderlist) { this.orderlist = orderlist; }												// Setter for order Objects
	
}
