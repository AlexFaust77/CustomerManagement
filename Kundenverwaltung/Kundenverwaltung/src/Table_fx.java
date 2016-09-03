import java.util.ArrayList;

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
	        
	        if (orderlist.size() > 0 ) {
	         
	           fillorderlist();																													// parse Arraylist to Observablelist
	            
	           TableColumn col_Order_Nr   = new TableColumn("Bestellnummer");																	// create all Columns for Table
	           			   col_Order_Nr.setMinWidth(100);
	                       col_Order_Nr.setCellValueFactory(new PropertyValueFactory<Obj_Order,String>("bestellnummer"));
	           TableColumn col_Order_Date  = new TableColumn("Bestelldatum");
	                       col_Order_Date.setMinWidth(100);
	                       col_Order_Date.setCellValueFactory(new PropertyValueFactory<Obj_Order,String>("bestelldatum"));
	           TableColumn col_Order_Summary  = new TableColumn("Bestellsumme");
	                       col_Order_Summary.setMinWidth(100);
	                       col_Order_Summary.setCellValueFactory(new PropertyValueFactory<Obj_Order,String>("bestellsumme"));
	           TableColumn col_Order_Count  = new TableColumn("Ratenzahl");
	                       col_Order_Count.setMinWidth(100);
	                       col_Order_Count.setCellValueFactory(new PropertyValueFactory<Obj_Order,String>("ratenanzahl"));
	           TableColumn col_Pay_Start = new TableColumn("Zahlungsstart");
	                       col_Pay_Start.setMinWidth(100);
	                       col_Pay_Start.setCellValueFactory(new PropertyValueFactory<Obj_Order,String>("zahlungsstart"));
	           TableColumn col_Pay_End = new TableColumn("Zahlungsende");    
	                       col_Pay_End.setMinWidth(100);
	                       col_Pay_End.setCellValueFactory(new PropertyValueFactory<Obj_Order,String>("zahlungsende"));
	       
	           fxTableView.getColumns().addAll(col_Order_Nr,col_Order_Date,col_Order_Summary,col_Order_Count,col_Pay_Start,col_Pay_End);		  // Add all Coumns
	           fxTableView.setItems(orders);
	       }
	  
	    return fxTableView; 																													 // return finished Tableview
	    }
	   
	    public void fillorderlist() {
	           for(int i = 0;i < orderlist.size();i++) {
	              	   orders.add(orderlist.get(i));
	   	       } 
	    }
	    
	    public void setOrderlist(ArrayList<Obj_Order> orderlist) { this.orderlist = orderlist; }
	    //public ObservableList<DatenModellBest> getTabellenDaten() { return bestDatenSaetze; }
	    //public ObservableList<Bestellung> getOrderData() { return orders; }
	
	
	
}
