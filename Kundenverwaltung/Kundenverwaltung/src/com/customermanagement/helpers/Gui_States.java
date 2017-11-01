package com.customermanagement.helpers;
import java.text.DateFormat;

import com.customermanagement.entities.Obj_Order;
import com.customermanagement.main.Cust_Gui;
import com.customermanagement.main.OrderGui;
import com.customermanagement.main.OrderGuiController;

public class Gui_States {
	
	    boolean on = true;
	    boolean off = false;
	    String activ = "green";
	    String passive = "white";
	    
	public void gui_State_Start(Cust_Gui obj_Cust_Gui) {
	     
	      obj_Cust_Gui.setEditCustNr(on, activ);  	 		// GUI Customer Textfields
	      obj_Cust_Gui.setCustNr("");
	      obj_Cust_Gui.setEditLastName(off,passive);
	      obj_Cust_Gui.setCustLastName("");
	      obj_Cust_Gui.setEditCustName(off,passive);
	      obj_Cust_Gui.setCustName("");
	      obj_Cust_Gui.setEditCustStreet(off,passive);
	      obj_Cust_Gui.setCustStreet("");
	      obj_Cust_Gui.setEditCustHNr(off,passive);
	      obj_Cust_Gui.setCustHNr("");
	      obj_Cust_Gui.setEditCustPc(off,passive);
	      obj_Cust_Gui.setCustPc("");
	      obj_Cust_Gui.setEditCustRes(off,passive);  
	      obj_Cust_Gui.setCustRes("");
	      obj_Cust_Gui.setTotal("");

	      obj_Cust_Gui.setBtnCustSave(off);					  // GUI Buttons
	      obj_Cust_Gui.setBtnCustCancel(off);  
	    
	}


	public void create_Customer(Cust_Gui obj_Cust_Gui) {   // Activate all Fields you need for new Customer
	        
	      obj_Cust_Gui.setEditCustNr(on, activ);
	      obj_Cust_Gui.setEditLastName(on, activ);
	      obj_Cust_Gui.setEditCustName(on, activ);
	      obj_Cust_Gui.setEditCustStreet(on, activ);
	      obj_Cust_Gui.setEditCustHNr(on, activ);
	      obj_Cust_Gui.setEditCustPc(on, activ);
	      obj_Cust_Gui.setEditCustRes(on, activ);
	       
	      obj_Cust_Gui.setBtnCustCancel(on);
	      obj_Cust_Gui.setBtnCustSave(on);
	        
	 }
	    
	 public void neuenKundenAnlegenErfolg() {
	          
	 }
	 
	 // kann nach Test des neuen Forms gelöscht werden
	 public void newOrder(OrderGui orderGui) {    // Activate all Fields you need for new Order
		  	        
		  orderGui.setBtnOrderSave(on);
		  orderGui.setBtnOrderNoSave(on);
		  
		  
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
