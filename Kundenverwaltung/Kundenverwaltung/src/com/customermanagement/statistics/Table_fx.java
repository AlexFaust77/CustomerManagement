package com.customermanagement.statistics;
import org.apache.log4j.Logger;

import com.customermanagement.entities.Obj_Order;
import com.customermanagement.main.MainGuiController;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

// Fill Tableview with Data / Orders - Orderdata
public class Table_fx {	
	
	String loggerInfo ="com.customermanagement.statistics -> Table_fx.java";
	   
		public void fillTheTable(TableView<Obj_Order> orderTable, ObservableList<Obj_Order> allOrders, MainGuiController mainGuiController, Logger logger){
	    	
	    	logger.info(loggerInfo + "Start create Tableviewdata - Checkorderdata");
			
	    	if (allOrders.size() > 0 ) {
		          
				   logger.debug(loggerInfo + "Check Amount of orders - Done - Orders counted: " + allOrders.size() + "\n");
				 
				   mainGuiController.getColOrderNo().setCellValueFactory(new PropertyValueFactory<>("orderNo"));  
				   mainGuiController.getColOrderDate().setCellValueFactory(new PropertyValueFactory<>("orderDate"));  
				   mainGuiController.getColOrderSummary().setCellValueFactory(new PropertyValueFactory<>("orderSummary"));            
		           mainGuiController.getColRateCount().setCellValueFactory(new PropertyValueFactory<>("rateCount"));  
		           mainGuiController.getColPayStart().setCellValueFactory(new PropertyValueFactory<>("payStart"));
		           mainGuiController.getColPayEnd().setCellValueFactory(new PropertyValueFactory<>("payEnd"));
		           mainGuiController.getColFirstRate().setCellValueFactory(new PropertyValueFactory<>("firstRate"));
		           mainGuiController.getColRate().setCellValueFactory(new PropertyValueFactory<>("rate"));               
		           mainGuiController.getColStillToPay().setCellValueFactory(new PropertyValueFactory<>("stillPay"));    
		           mainGuiController.getColAlreadyPaid().setCellValueFactory(new PropertyValueFactory<>("alreadyPaid"));   
		           		           	           
		           orderTable.setItems(allOrders);
		           
		           logger.debug(loggerInfo + "Data assigned - Items set - Success: " + allOrders.size() + "\n");
			
			 }
		}
}
