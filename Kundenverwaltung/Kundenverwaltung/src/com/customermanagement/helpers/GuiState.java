package com.customermanagement.helpers;
import java.text.DateFormat;

import org.apache.log4j.Logger;

import com.customermanagement.entities.Obj_Order;
import com.customermanagement.main.MainGuiController;
import com.customermanagement.main.OrderGui;
import com.customermanagement.main.OrderGuiController;

public class GuiState {
	
	    boolean on = true;
	    boolean off = false;
	    String activ = "green";
	    String passive = "white";
	    String clear = "";
	    
	public void guiStateStart(MainGuiController mainGuiController) {
	    /* 
	      mainGuiController.setEditCustNr(on, activ);  	 		// GUI Customer Textfields
	      mainGuiController.setCustNr("");
	      mainGuiController.setEditLastName(off,passive);
	      mainGuiController.setCustLastName("");
	      mainGuiController.setEditCustName(off,passive);
	      mainGuiController.setCustName("");
	      mainGuiController.setEditCustStreet(off,passive);
	      mainGuiController.setCustStreet("");
	      mainGuiController.setEditCustHNr(off,passive);
	      mainGuiController.setCustHNr("");
	      mainGuiController.setEditCustPc(off,passive);
	      mainGuiController.setCustPc("");
	      mainGuiController.setEditCustRes(off,passive);  
	      mainGuiController.setCustRes("");
	      mainGuiController.setTotal("");

	      mainGuiController.setBtnCustSave(off);					  // GUI Buttons
	      mainGuiController.setBtnCustCancel(off);  
	    */
	}

	// Activate all Fields you need for new Customer
	public void stateCreateNewCustomer(MainGuiController mainGuiController, Logger logger) {   
	        
	   mainGuiController.setEditCustNr(on, activ);
	   mainGuiController.setEditCustName(on, activ);
	   mainGuiController.setEditCustSurname(on, activ);
	   mainGuiController.setEditCustStreet(on, activ);
	   mainGuiController.setEditCustHouseNo(on, activ);
	   mainGuiController.setEditCustPlc(on, activ);
	   mainGuiController.setEditCustTown(on, activ);
      
	   mainGuiController.btnSaveCustomer.setVisible(true);
	   mainGuiController.btnCancelNewCustomer.setVisible(true);
	    	
	   mainGuiController.btnCustomerSearch.setVisible(false);
	   mainGuiController.btnDeleteCustomer.setVisible(false);
	   mainGuiController.btnNewCustomer.setVisible(false);
	   mainGuiController.btnCreateOrder.setVisible(false);
	   mainGuiController.btnNewDatabase.setVisible(false);
	   mainGuiController.btnSelectDatabase.setVisible(false);
	   mainGuiController.btnPrintPaymentPlan.setVisible(false);
	   mainGuiController.btnExportToExel.setVisible(false);
	   mainGuiController.btnExportToPDF.setVisible(false);
	   	      
	   logger.info("GUI State changed");      
	 }
	    
	 public void neuenKundenAnlegenErfolg() {
	          
	 }
	 
	 // kann nach Test des neuen Forms gelöscht werden
	 public void newOrder(OrderGui orderGui) {    // Activate all Fields you need for new Order
		  	        
		  orderGui.setBtnOrderSave(on);
		  orderGui.setBtnOrderNoSave(on);
		  
		  
	 }
	 
	 public void resetCreateNewCustomer(MainGuiController mainGuiController, Logger logger) {
		   mainGuiController.setEditCustNr(off, passive);
		   mainGuiController.setEditCustName(off, passive);
		   mainGuiController.setEditCustSurname(off, passive);
		   mainGuiController.setEditCustStreet(off, passive);
		   mainGuiController.setEditCustHouseNo(off, passive);
		   mainGuiController.setEditCustPlc(off, passive);
		   mainGuiController.setEditCustTown(off, passive);
	      
		   mainGuiController.btnSaveCustomer.setVisible(false);
		   mainGuiController.btnCancelNewCustomer.setVisible(false);
		    	
		   mainGuiController.btnCustomerSearch.setVisible(true);
		   mainGuiController.btnDeleteCustomer.setVisible(true);
		   mainGuiController.btnNewCustomer.setVisible(true);
		   mainGuiController.btnCreateOrder.setVisible(true);
		   mainGuiController.btnNewDatabase.setVisible(true);
		   mainGuiController.btnSelectDatabase.setVisible(true);
		   mainGuiController.btnPrintPaymentPlan.setVisible(true);
		   mainGuiController.btnExportToExel.setVisible(true);
		   mainGuiController.btnExportToPDF.setVisible(true);
		   
		   mainGuiController.setTxtCustomerNo(clear);
		   mainGuiController.setTxtCustomerName(clear);
		   mainGuiController.setTxtCustomerSurname(clear);
		   mainGuiController.setTxtCustomerStreet(clear);
		   mainGuiController.setTxtCustomerHouseNo(clear);
		   mainGuiController.setTxtCustomerPlc(clear);
		   mainGuiController.setTxtCustomerTown(clear);
		   
		   logger.info("GUI State resetet");   
		   
		   
	 }
	 
	 public void newFXMLOrder(OrderGuiController orderGuiControl){
		 
		 orderGuiControl.setBtnDelete(off);
		 orderGuiControl.setBtnSave(on);
		 orderGuiControl.setBtnCancel(on);
		 
		 orderGuiControl.setEditOrderNo(activ);
		 orderGuiControl.setEditOrderDate(activ);
		 orderGuiControl.setEditPayStart(activ);
		 orderGuiControl.setEditFirstRate(activ);
		 orderGuiControl.setEditRate(activ);
		 orderGuiControl.setEditRateCount(activ);
		 
	 }
	 
	 public void changeFXMLOrder(OrderGuiController orderGuiControl,Obj_Order objOrder, DateFormat dateFormat) {
		 
		 orderGuiControl.setBtnDelete(on);
		 orderGuiControl.setBtnSave(on);
		 orderGuiControl.setBtnCancel(on);
		 
		 orderGuiControl.setEditOrderNo(activ);
		 orderGuiControl.setEditOrderDate(activ);
		 orderGuiControl.setEditPayStart(activ);
		 orderGuiControl.setEditFirstRate(activ);
		 orderGuiControl.setEditRate(activ);
		 orderGuiControl.setEditRateCount(activ);
		 
		 
		 this.fillOrderGui(orderGuiControl, objOrder,dateFormat);
	 
	 }
	 
     // kann nach Test des neuen Forms gelöscht werden
	 public void cancelOrder(OrderGui orderGui) {
		  orderGui.setOrderCustNr("");
		  orderGui.setEditOrderNr(off,passive);
		  orderGui.setOrderNr("");
		  orderGui.setEditOrderDate(off,passive);
		  orderGui.setOrderDate("");
		  orderGui.setEditPayStart(off,passive);
		  orderGui.setPayStart("");
		  orderGui.setPayEnd("");
		  orderGui.setEditRateCount(off,passive);
		  orderGui.setRateCount("");
		  orderGui.setEditFirstRate(off,passive);
		  orderGui.setFirstRate("");
		  orderGui.setEditRate(off,passive);
		  orderGui.setRate("");
		  orderGui.setOrderSummary("");
	      
		  orderGui.setBtnOrderNoSave(off);
		 	  
	 } 
	 
	 // kann nach Test des neuen Forms gelöscht werden
	 public void change_Order(OrderGui orderGui) {
		  orderGui.setEditOrderNr(off,passive);
		  //orderGui.setEditCustNr(off,passive);
	      
		  orderGui.setEditOrderDate(on, activ);
		  orderGui.setEditPayStart(on, activ);
		  orderGui.setEditRateCount(on, activ);
		  orderGui.setEditFirstRate(on, activ);
		  orderGui.setEditRate(on, activ);
	     
		  orderGui.setBtnOrderSave(on);
		  orderGui.setBtnOrderNoSave(on);
		  
	 }
	
	 private void fillOrderGui(OrderGuiController orderGuiControl, Obj_Order objOrder, DateFormat dateFormat) {
			
			orderGuiControl.setOrderCustNo(objOrder.getCustNo());
			orderGuiControl.setOrderNo(objOrder.getOrderNo());
			orderGuiControl.setOrderDate(dateFormat.format(objOrder.getOrderDate()));
			orderGuiControl.setPayStart(dateFormat.format(objOrder.getPayStart()));
			orderGuiControl.setPayEnd(dateFormat.format(objOrder.getPayEnd()));
			orderGuiControl.setRateCount(objOrder.getRateCount());
			orderGuiControl.setFirstRate(objOrder.getFirstRate());
			orderGuiControl.setRate(objOrder.getRate());
			orderGuiControl.setOrderSummary(objOrder.getOrderSummary());
			orderGuiControl.setStillToPay(objOrder.getStillToPay());
			orderGuiControl.setAlreadyPaid(objOrder.getAlreadyPaid());
		
	}
	 
	
}
